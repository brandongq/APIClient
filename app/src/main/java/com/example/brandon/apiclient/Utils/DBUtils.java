package com.example.brandon.apiclient.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Brandon on 06-Oct-17.
 */

public class DBUtils extends SQLiteOpenHelper{
    public static final String DB_NAME = "QuePasaPOJO.db";
    public static final int DB_VERSION = 3;

    public static final String POST_TABLE_NAME = "Post";
    public static final String POST_USER_ID = "UserId";
    public static final String POST_ID = "Id";
    public static final String POST_TITLE = "Title";
    public static final String POST_BODY = "Body";

    public static final String COMMENT_TABLE_NAME = "Comment";
    public static final String COMMENT_POST_ID = "PostId";
    public static final String COMMENT_ID = "Id";
    public static final String COMMENT_NAME = "Name";
    public static final String COMMENT_EMAIL = "Email";
    public static final String COMMENT_BODY = "Body";

    public static final String DB_CREATE_TABLE_POST = "CREATE TABLE " + POST_TABLE_NAME + " (" +
            POST_USER_ID + " INTEGER NOT NULL, " +
            POST_ID + " INTEGER NOT NULL, " +
            POST_TITLE + " TEXT NOT NULL, " +
            POST_BODY + " TEXT NOT NULL);";
    public static final String DB_CREATE_TABLE_COMMENT = "CREATE TABLE " + COMMENT_TABLE_NAME+ " (" +
            COMMENT_POST_ID + " INTEGER NOT NULL, " +
            COMMENT_ID + " INTEGER NOT NULL, " +
            COMMENT_NAME + " TEXT NOT NULL, " +
            COMMENT_EMAIL + " TEXT NOT NULL, " +
            COMMENT_BODY + " TEXT NOT NULL);";

    public DBUtils (Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DB_CREATE_TABLE_POST);
        sqLiteDatabase.execSQL(DB_CREATE_TABLE_COMMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Post");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Comment");
        onCreate(sqLiteDatabase);
    }
}
