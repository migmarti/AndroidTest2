package com.example.mmart.memesaurio;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.mmart.memesaurio.Adapters.CommentAdapter;
import com.example.mmart.memesaurio.Objects.Comment;

import java.util.ArrayList;

public class CommentsActivity extends AppCompatActivity {

    CommentAdapter commentAdapter;
    ListView listView;
    ArrayList<Comment> comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        listView = (ListView) findViewById(R.id.listView);
        commentAdapter = new CommentAdapter(this);
        listView.setAdapter(commentAdapter);

        comments = this.getIntent().getParcelableArrayListExtra("Parcel");
        fillList(comments);
    }

    public void fillList(ArrayList<Comment> comments) {
        for (Comment comment : comments) {
            commentAdapter.add(comment);
        }
        commentAdapter.notifyDataSetChanged();
    }

    public void onBackPressed() {
        finish();
    }
}
