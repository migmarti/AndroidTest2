package com.example.mmart.memesaurio;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.mmart.memesaurio.Adapters.ExpandablePostAdapter;
import com.example.mmart.memesaurio.Objects.Comment;
import com.example.mmart.memesaurio.Objects.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class ExpandablePostActivity extends AppCompatActivity {
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    TreeMap<String, List<String>> expandableListDetail;
    ArrayList<Post> posts;
    ArrayList<Comment> comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_post);

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);

        posts = this.getIntent().getParcelableArrayListExtra("Posts");
        comments = this.getIntent().getParcelableArrayListExtra("Comments");
        expandableListDetail = getData(posts, comments);

        expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
        expandableListAdapter = new ExpandablePostAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();
                return false;
            }
        });
    }

    public TreeMap<String, List<String>> getData(ArrayList<Post> posts, ArrayList<Comment> comments) {
        TreeMap<String, List<String>> data = new TreeMap<>();
        System.out.println("Posts size: " + posts.size());
        System.out.println("Comments size: " + comments.size());
        for (Post p : posts) {
            String postString = p.getTitle() + " (ID: " + p.getId() + ")";
            List<String> commentStrings = new ArrayList<String>();
            for(Comment c : comments) {
                if (c.getPostId().equals(p.getId())) {
                    String commentString = c.getName() + " said:\n" + c.getBody();
                    commentStrings.add(commentString);
                }
            }
            data.put(postString, commentStrings);
        }
        System.out.println("Data size: " + data.keySet().size());
        return data;
    }

    public void onBackPressed() {
        finish();
    }
}
