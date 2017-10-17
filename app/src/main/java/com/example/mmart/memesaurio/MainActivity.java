package com.example.mmart.memesaurio;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mmart.memesaurio.Objects.Comment;
import com.example.mmart.memesaurio.Objects.Post;
import com.example.mmart.memesaurio.Utils.DBHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView txtString, txtString2, txtId;
    Button btnTest, btnSyncPosts, btnSyncComments, btnViewPosts, btnViewComments, btnClear, btnUpload;
    DBHelper db;
    ArrayList<Post> posts;
    ArrayList<Comment> comments;
    String postUrl, commentUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DBHelper(this);
        txtString = (TextView) findViewById(R.id.textString);
        txtString2 = (TextView) findViewById(R.id.textString2);
        txtId = (TextView) findViewById(R.id.textId);
        btnTest = (Button) findViewById((R.id.button1));
        btnSyncPosts = (Button) findViewById((R.id.buttonSyncP));
        btnSyncComments = (Button) findViewById((R.id.buttonSyncC));
        btnViewPosts = (Button) findViewById((R.id.buttonPosts));
        btnViewComments = (Button) findViewById((R.id.buttonComments));
        btnClear = (Button) findViewById((R.id.buttonClear));
        btnUpload = (Button) findViewById(R.id.buttonUpload);
        posts = new ArrayList<Post>();
        comments = new ArrayList<Comment>();
        final RequestQueue queue = Volley.newRequestQueue(this);
        String postsUrl = "http://jsonplaceholder.typicode.com/posts";
        String commentsUrl = "http://jsonplaceholder.typicode.com/comments";

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
                        txtString.setText(error.toString());
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
                        txtString.setText(error.toString());
                    }
                });

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = Integer.parseInt(txtId.getText().toString());
                postUrl = "http://jsonplaceholder.typicode.com/posts/"+id;
                commentUrl = "http://jsonplaceholder.typicode.com/comments/"+id;
                final StringRequest postStringRequest = new StringRequest(Request.Method.GET, postUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                txtString.setText("Post:\n" + response);
                                //posts.add(createPost(response));
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        txtString.setText(error.toString());
                    }
                });

                final StringRequest commentStringRequest = new StringRequest(Request.Method.GET, commentUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                txtString2.setText("Comment:\n" + response);
                                //Comment comment = createComment(response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        txtString2.setText(error.toString());
                    }
                });
                queue.add(postStringRequest);
                queue.add(commentStringRequest);
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
                Intent intent = new Intent(getApplicationContext(), PostsActivity.class);
                db.open();
                intent.putExtra("Parcel", db.getAllPosts());
                db.close();
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
                        System.out.println("JSON: " + jsonBody.toString());
                        System.out.println("Error uploading: " + error.toString());
                    }
                });
        return jsonPostRequest;
    }
}
