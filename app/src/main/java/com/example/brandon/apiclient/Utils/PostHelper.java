package com.example.brandon.apiclient.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.brandon.apiclient.Models.Post;

import java.util.ArrayList;

/**
 * Created by Brandon on 06-Oct-17.
 */

public class PostHelper {
    private DBUtils dbHelper;
    private SQLiteDatabase database;
    private String[] POST_TABLE_COLUMNS = {
            DBUtils.POST_USER_ID,
            DBUtils.POST_ID,
            DBUtils.POST_TITLE,
            DBUtils.POST_BODY
    };

    public PostHelper(Context context) {
        dbHelper = new DBUtils(context);

    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public ArrayList<Post> getAllPosts() {
        ArrayList<Post> listPost = new ArrayList<>();
        Cursor cursor = database.query(DBUtils.POST_TABLE_NAME, POST_TABLE_COLUMNS, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            listPost.add(parsePost(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        return listPost;
    }

    private Post parsePost(Cursor cursor) {
        Post oPost = new Post(cursor.getInt(cursor.getColumnIndex(DBUtils.POST_ID)));
        oPost.setUserId(cursor.getInt(cursor.getColumnIndex(DBUtils.POST_USER_ID)));
        oPost.setTitle(cursor.getString(cursor.getColumnIndex(DBUtils.POST_TITLE)));
        oPost.setBody(cursor.getString(cursor.getColumnIndex(DBUtils.POST_BODY)));
        return oPost;
    }

    public Post addPost(int userId, int id, String title, String body) {
        ContentValues values = new ContentValues();
        values.put(DBUtils.POST_USER_ID, userId);
        values.put(DBUtils.POST_ID, id);
        values.put(DBUtils.POST_TITLE, title);
        values.put(DBUtils.POST_BODY, body);

        database.insert(DBUtils.POST_TABLE_NAME, null, values);

        Cursor cursor = database.query(DBUtils.POST_TABLE_NAME,
                POST_TABLE_COLUMNS,
                DBUtils.POST_ID + " = " + id,
                null, null, null, null);
        cursor.moveToFirst();
        Post post = parsePost(cursor);
        cursor.close();

        return post;
    }

    public int deletePost(int nPostID) {
        return database.delete(DBUtils.POST_TABLE_NAME, DBUtils.POST_ID + " = " + nPostID, null);
    }

    public boolean exists(int nPostID) {
        Cursor cursor = database.query(DBUtils.POST_TABLE_NAME,
                POST_TABLE_COLUMNS,
                DBUtils.POST_ID + " = " + nPostID,
                null, null, null, null);
        int x = cursor.getCount();
        cursor.close();
        return x > 0;
    }
}
