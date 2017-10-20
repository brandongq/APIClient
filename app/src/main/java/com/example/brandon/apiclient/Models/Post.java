package com.example.brandon.apiclient.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brandon on 06-Oct-17.
 */

public class Post implements Parcelable {

    //Properties
    private int userId;
    private int id;
    private String title;
    private String body;
    private ArrayList<Comment> comments;

    //Constructors
    public Post(int id) {
        this.id = id;
        comments = new ArrayList<Comment>();
    }

    //Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    // ------------------ PARCELABLE STUFF ------------------

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(userId);
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(body);
        parcel.writeTypedList(comments);
    }

    public static final Parcelable.Creator<Post> CREATOR = new Parcelable.Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    protected Post(Parcel in) {
        userId = in.readInt();
        id = in.readInt();
        title = in.readString();
        body = in.readString();
        if(comments == null){
            comments = new ArrayList<Comment>();
        }
        in.readTypedList(comments, Comment.CREATOR);
    }

    // ------------------ PARCELABLE STUFF ------------------
}
