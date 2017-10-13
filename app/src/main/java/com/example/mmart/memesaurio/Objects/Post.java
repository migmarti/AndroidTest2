package com.example.mmart.memesaurio.Objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MMART on 10/6/2017.
 */
public class Post implements Parcelable {
    private String userId;
    private String id;
    private String title;
    private String body;

    public Post(Parcel in) {
        this.userId = in.readString();
        this.id = in.readString();
        this.title = in.readString();
        this.body = in.readString();
    }

    public Post() {

    }

    public Post(String userId, String id, String title, String body) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String printPost() {
        String print = "";
        print += "User Id: " + this.userId + "\n";
        print += "Id: " + this.id + "\n";
        print += "Title: " + this.title + "\n";
        print += "Body: " + this.body + "\n";
        return print;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(body);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        @Override
        public Post createFromParcel(Parcel in){
            return new Post(in);
        }
        @Override
        public Post[] newArray(int size){
            return new Post[size];
        }
    };
}
