package com.example.mmart.memesaurio;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mmart.memesaurio.Objects.Comment;
import com.example.mmart.memesaurio.Objects.Post;
import com.example.mmart.memesaurio.Utils.DBHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView txtId;
    Button btnSyncPosts, btnSyncComments, btnViewPosts,
            btnViewComments, btnClear, btnUpload, btnPostsComments,
            btnAsyncComments, btnAsyncPosts;
    DBHelper db;
    ArrayList<Post> posts;
    ArrayList<Comment> comments;
    String postUrl, commentUrl;
    HashMap<String, List<String>> expandableListDetail;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DBHelper(this);
        txtId = (TextView) findViewById(R.id.textId);
        btnSyncPosts = (Button) findViewById((R.id.buttonSyncP));
        btnSyncComments = (Button) findViewById((R.id.buttonSyncC));
        btnViewPosts = (Button) findViewById((R.id.buttonPosts));
        btnViewComments = (Button) findViewById((R.id.buttonComments));
        btnClear = (Button) findViewById((R.id.buttonClear));
        btnUpload = (Button) findViewById(R.id.buttonUpload);
        btnPostsComments = (Button) findViewById(R.id.buttonPostsComments);
        btnAsyncPosts = (Button) findViewById(R.id.buttonAsyncPosts);
        btnAsyncComments = (Button) findViewById(R.id.buttonAsyncComments);
        posts = new ArrayList<Post>();
        comments = new ArrayList<Comment>();
        final RequestQueue queue = Volley.newRequestQueue(this);
        String postsUrl = "http://jsonplaceholder.typicode.com/posts";
        String commentsUrl = "http://jsonplaceholder.typicode.com/comments";
        expandableListDetail = new  HashMap<String, List<String>>();
        final int id = Integer.parseInt(txtId.getText().toString());
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //http://107.170.247.123:2403/posts
        //http://107.170.247.123:2403/comments

        final JsonArrayRequest postsArrayRequest = new JsonArrayRequest(Request.Method.GET, postsUrl,
                null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        String result = "";
                        posts = new ArrayList<Post>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                //result += response.getString(i) + "\n\n";
                                posts.add(createPost(response.getString(i)));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getBaseContext(), "Error: " + error.toString(), Toast.LENGTH_LONG).show();
                    }
                });

        final JsonArrayRequest commentsArrayRequest = new JsonArrayRequest(Request.Method.GET, commentsUrl,
                null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        String result = "";
                        comments = new ArrayList<Comment>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                //result += response.getString(i) + "\n\n";
                                comments.add(createComment(response.getString(i)));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getBaseContext(), "Error: " + error.toString(), Toast.LENGTH_LONG).show();
                    }
                });

        btnSyncPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Synchronizing Posts..", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                db.open();
                db.deleteAllPosts();
                db.close();
                queue.add(postsArrayRequest);
                Snackbar.make(view, "Posts successfully synchronized.", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            }
        });

        btnSyncComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Synchronizing Comments..", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                db.open();
                db.deleteAllComments();
                db.close();
                queue.add(commentsArrayRequest);
                Snackbar.make(view, "Comments successfully synchronized.", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            }
        });

        btnViewPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.open();
                posts = db.getAllPosts();
                db.close();
                Intent intent = new Intent(getApplicationContext(), PostsActivity.class);
                intent.putExtra("Parcel", posts);
                startActivity(intent);
            }
        });

        btnPostsComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.open();
                posts = db.getAllPosts();
                comments = db.getAllComments();
                db.close();
                Intent intent = new Intent(getApplicationContext(), ExpandablePostActivity.class);
                intent.putExtra("Posts", posts);
                intent.putExtra("Comments", comments);
                startActivity(intent);
            }
        });

        btnViewComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CommentsActivity.class);
                db.open();
                intent.putExtra("Parcel", db.getAllComments());
                db.close();
                startActivity(intent);
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.open();
                db.deleteAllPosts();
                db.deleteAllComments();
                db.close();
                Snackbar.make(view, "Data has been cleared.", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Comment comment = new Comment();
                comment.setName("Miguel Martinez");
                comment.setPostId("1");
                comment.setEmail("miguel.martinez@cetys.edu.mx");
                comment.setBody("Bye");
                JSONObject json = comment.toJSON();
                JsonObjectRequest jor = uploadJSON(json, "http://107.170.247.123:2403/posts");
                queue.add(jor);
            }
        });

        btnAsyncPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new HttpAsyncTask().execute("http://jsonplaceholder.typicode.com/posts/"+id);
            }
        });

        btnAsyncComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new HttpAsyncTask().execute("http://jsonplaceholder.typicode.com/comments/"+id);
            }
        });

    }

    public Post createPost(String response) {
        Post post = new Post();
        try {
            JSONObject jsonObject = new JSONObject(response);
            int userId = jsonObject.getInt("userId");
            int id = jsonObject.getInt("id");
            String title = jsonObject.getString("title");
            String body = jsonObject.getString("body");
            db.open();
            post = db.addPost(id, userId, title, body);
            db.close();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return post;
    }

    public Comment createComment(String response) {
        Comment comment = new Comment();
        try {
            JSONObject jsonObject = new JSONObject(response);
            int postId = jsonObject.getInt("postId");
            int id = jsonObject.getInt("id");
            String name = jsonObject.getString("name");
            String email = jsonObject.getString("email");
            String body = jsonObject.getString("body");
            db.open();
            comment = db.addComment(id, postId, name, email, body);
            db.close();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return comment;
    }

    public JsonObjectRequest uploadJSON(final JSONObject jsonBody, String url) {
        final JsonObjectRequest jsonPostRequest = new JsonObjectRequest
                (Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("Successfully uploaded object.");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Error uploading: " + error.toString());
                    }
                });
        return jsonPostRequest;
    }


    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
        @Override
        protected String doInBackground(String... urls) {
            return getHTTPRequest(urls[0]);
        }
        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(getBaseContext(), "Received:\n" + result, Toast.LENGTH_LONG).show();
        }
    }

    public static String getHTTPRequest(String url) {
        URL obj = null;
        HttpURLConnection con = null;
        try {
            obj = new URL(url);
            con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return response.toString();
            } else {
                return "POST request did not work.";
            }
        } catch (IOException e) {
            return e.getMessage();
        }
    }
}
