package com.example.mmart.memesaurio.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mmart.memesaurio.Objects.Comment;
import com.example.mmart.memesaurio.R;

/**
 * Created by MMART on 10/12/2017.
 */
public class CommentAdapter extends ArrayAdapter<Comment> {

    public CommentAdapter(Context context) {
        super(context, R.layout.comment_row, R.id.textInfo);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View objectView = super.getView(position, convertView, parent);
        TextView txtInfo = (TextView) objectView.findViewById(R.id.textInfo);

        final Comment comment = this.getItem(position);
        txtInfo.setText(comment.printComment());

        return objectView;
    }
}
