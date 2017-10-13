package com.example.mmart.memesaurio.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mmart.memesaurio.Objects.Post;
import com.example.mmart.memesaurio.R;

/**
 * Created by MMART on 10/12/2017.
 */
public class PostAdapter extends ArrayAdapter<Post> {

    public PostAdapter(Context context) {
        super(context, R.layout.post_row, R.id.textInfo);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View objectView = super.getView(position, convertView, parent);
        TextView txtInfo = (TextView) objectView.findViewById(R.id.textInfo);

        final Post post = this.getItem(position);
        txtInfo.setText(post.printPost());

        return objectView;
    }
}
