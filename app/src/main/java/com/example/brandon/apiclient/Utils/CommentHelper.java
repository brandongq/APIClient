package com.example.brandon.apiclient.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.brandon.apiclient.Models.Comment;

import java.util.ArrayList;

/**
 * Created by Brandon on 06-Oct-17.
 */

public class CommentHelper {
    private DBUtils dbHelper;
    private SQLiteDatabase database;
    private String[] COMMENT_TABLE_COLUMNS = {
            DBUtils.COMMENT_POST_ID,
            DBUtils.COMMENT_ID,
            DBUtils.COMMENT_NAME,
            DBUtils.COMMENT_EMAIL,
            DBUtils.COMMENT_BODY
    };

    public CommentHelper(Context context) {
        dbHelper = new DBUtils(context);

    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public ArrayList<Comment> getAllComments() {
        ArrayList<Comment> listComment = new ArrayList<>();
        Cursor cursor = database.query(DBUtils.COMMENT_TABLE_NAME, COMMENT_TABLE_COLUMNS, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            listComment.add(parseComment(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        return listComment;
    }

    private Comment parseComment(Cursor cursor) {
        Comment oComment = new Comment(cursor.getInt(cursor.getColumnIndex(DBUtils.COMMENT_ID)));
        oComment.setPostId(cursor.getInt(cursor.getColumnIndex(DBUtils.COMMENT_POST_ID)));
        oComment.setName(cursor.getString(cursor.getColumnIndex(DBUtils.COMMENT_NAME)));
        oComment.setEmail(cursor.getString(cursor.getColumnIndex(DBUtils.COMMENT_EMAIL)));
        oComment.setBody(cursor.getString(cursor.getColumnIndex(DBUtils.COMMENT_BODY)));
        return oComment;
    }

    public Comment addComment(int postId, int id, String name, String email, String body) {
        ContentValues values = new ContentValues();
        values.put(DBUtils.COMMENT_POST_ID, postId);
        values.put(DBUtils.COMMENT_ID, id);
        values.put(DBUtils.COMMENT_NAME, name);
        values.put(DBUtils.COMMENT_EMAIL, email);
        values.put(DBUtils.COMMENT_BODY, body);

        database.insert(DBUtils.COMMENT_TABLE_NAME, null, values);

        Cursor cursor = database.query(DBUtils.COMMENT_TABLE_NAME,
                COMMENT_TABLE_COLUMNS,
                DBUtils.COMMENT_ID + " = " + id,
                null, null, null, null);
        cursor.moveToFirst();
        Comment comment = parseComment(cursor);
        cursor.close();

        return comment;
    }

    public int deleteComment(int nCommentID) {
        return database.delete(DBUtils.COMMENT_TABLE_NAME, DBUtils.COMMENT_ID + " = " + nCommentID, null);
    }

    public boolean exists(int nCommentID) {
        Cursor cursor = database.query(DBUtils.COMMENT_TABLE_NAME,
                COMMENT_TABLE_COLUMNS,
                DBUtils.COMMENT_ID + " = " + nCommentID,
                null, null, null, null);
        int x = cursor.getCount();
        cursor.close();
        return x > 0;
    }

    public ArrayList<Comment> getCommentsByPostId (int postID) {
        ArrayList<Comment> comments = new ArrayList<>();
        Cursor cursor = database.query(DBUtils.COMMENT_TABLE_NAME,
                COMMENT_TABLE_COLUMNS,
                DBUtils.COMMENT_POST_ID + " = " + postID,
                null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            comments.add(parseComment(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        return comments;
    }
}
