package com.example.mmart.memesaurio.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.mmart.memesaurio.Objects.Comment;
import com.example.mmart.memesaurio.Objects.Post;

import java.util.ArrayList;

/**
 * Created by MMART on 9/22/2017.
 */
public class DBHelper {
    private DBUtils dbHelper;
    private SQLiteDatabase database;
    private String[] POST_TABLE_COLUMNS =
            {
                    DBUtils.POST_ID,
                    DBUtils.POST_USER,
                    DBUtils.POST_TITLE,
                    DBUtils.POST_BODY
            };
    private String[] COMMENT_TABLE_COLUMNS =
            {
                    DBUtils.COMMENT_ID,
                    DBUtils.COMMENT_POST,
                    DBUtils.COMMENT_NAME,
                    DBUtils.COMMENT_EMAIL,
                    DBUtils.COMMENT_BODY
            };

    public DBHelper(Context context) {
        dbHelper = new DBUtils(context);
    }
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }
    public void close() {
        database.close();
    }

    public Comment addComment(int post, String name, String email, String body) {
        ContentValues values = new ContentValues();
        values.put(DBUtils.COMMENT_POST, post);
        values.put(DBUtils.COMMENT_NAME, name);
        values.put(DBUtils.COMMENT_EMAIL, email);
        values.put(DBUtils.COMMENT_BODY, body);
        long commentId = database.insert(DBUtils.COMMENT_TABLE, null, values);
        Cursor cursor = database.query(DBUtils.COMMENT_TABLE, COMMENT_TABLE_COLUMNS,
                DBUtils.COMMENT_ID + " = " + commentId, null, null, null, null);
        cursor.moveToFirst();
        Comment comment = parseComment(cursor);
        cursor.close();
        return comment;
    }

    public Post addPost(int user, String title, String body) {
        ContentValues values = new ContentValues();
        values.put(DBUtils.POST_USER, user);
        values.put(DBUtils.POST_TITLE, title);
        values.put(DBUtils.POST_BODY, body);
        long postId = database.insert(DBUtils.POST_TABLE, null, values);
        Cursor cursor = database.query(DBUtils.POST_TABLE, POST_TABLE_COLUMNS,
                DBUtils.POST_ID + " = " + postId, null, null, null, null);
        cursor.moveToFirst();
        Post post = parsePost(cursor);
        cursor.close();
        return post;
    }
    
    public void deletePost(int id) {
        database.delete(DBUtils.POST_TABLE, DBUtils.POST_ID+" = " + id, null);
    }

    public void deleteAllPosts() {
        database.delete(DBUtils.POST_TABLE, DBUtils.POST_ID + " > 0", null);
    }

    public void deleteComment(int id) {
        database.delete(DBUtils.COMMENT_TABLE, DBUtils.COMMENT_ID+" = " + id, null);
    }

    public void deleteAllComments() {
        database.delete(DBUtils.COMMENT_TABLE, DBUtils.COMMENT_ID + " > 0", null);
    }

    public ArrayList<Post> getAllPosts() {
        ArrayList<Post> posts = new ArrayList<Post>();
        Cursor cursor = database.query(DBUtils.POST_TABLE, POST_TABLE_COLUMNS, null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            posts.add(parsePost(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        return posts;
    }

    public ArrayList<Comment> getAllComments() {
        ArrayList<Comment> comments = new ArrayList<Comment>();
        Cursor cursor = database.query(DBUtils.COMMENT_TABLE, COMMENT_TABLE_COLUMNS, null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            comments.add(parseComment(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        return comments;
    }

    private Comment parseComment(Cursor cursor) {
        Comment comment = new Comment();
        if( cursor != null && cursor.moveToFirst() ) {
            comment.setId(cursor.getString(cursor.getColumnIndex(DBUtils.COMMENT_ID)));
            comment.setPostId(cursor.getString(cursor.getColumnIndex(DBUtils.COMMENT_POST)));
            comment.setName(cursor.getString(cursor.getColumnIndex(DBUtils.COMMENT_NAME)));
            comment.setEmail(cursor.getString(cursor.getColumnIndex(DBUtils.COMMENT_EMAIL)));
            comment.setBody(cursor.getString(cursor.getColumnIndex(DBUtils.COMMENT_BODY)));
        }
        return comment;
    }

    private Post parsePost(Cursor cursor) {
        Post post = new Post();
        if( cursor != null && cursor.moveToFirst() ) {
            post.setId(cursor.getString(cursor.getColumnIndex(DBUtils.POST_ID)));
            post.setUserId(cursor.getString(cursor.getColumnIndex(DBUtils.POST_USER)));
            post.setTitle(cursor.getString(cursor.getColumnIndex(DBUtils.POST_TITLE)));
            post.setBody(cursor.getString(cursor.getColumnIndex(DBUtils.POST_BODY)));
        }
        return post;
    }
}
