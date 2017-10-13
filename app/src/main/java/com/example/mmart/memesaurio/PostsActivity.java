package com.example.mmart.memesaurio;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.mmart.memesaurio.Adapters.PostAdapter;
import com.example.mmart.memesaurio.Objects.Post;

import java.util.ArrayList;

public class PostsActivity extends AppCompatActivity {

    PostAdapter postAdapter;
    ListView listView;
    ArrayList<Post> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        listView = (ListView) findViewById(R.id.listView);
        postAdapter = new PostAdapter(this);
        listView.setAdapter(postAdapter);

        posts = this.getIntent().getParcelableArrayListExtra("Parcel");
        fillList(posts);
    }

    public void fillList(ArrayList<Post> posts) {
        for (Post post : posts) {
            postAdapter.add(post);
        }
        postAdapter.notifyDataSetChanged();
    }

    public void onBackPressed() {
        finish();
    }
}
