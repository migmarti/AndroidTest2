package com.example.mmart.memesaurio.Objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MMART on 10/6/2017.
 */
public class Comment implements Parcelable {
    private String postId;
    private String id;
    private String name;
    private String email;
    private String body;

    public Comment(Parcel in) {
        this.postId = in.readString();
        this.id = in.readString();
        this.name = in.readString();
        this.email = in.readString();
        this.body = in.readString();
    }

    public Comment() {

    }

    public Comment(String postId, String id, String name, String email, String body) {
        this.postId = postId;
        this.id = id;
        this.name = name;
        this.email = email;
        this.body = body;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String printComment() {
        String print = "";
        print += "Post Id: " + this.postId + "\n";
        print += "Id: " + this.id + "\n";
        print += "Name: " + this.name + "\n";
        print += "Email: " + this.email + "\n";
        print += "Body: " + this.body + "\n";
        return print;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(postId);
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(body);
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}
