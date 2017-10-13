package com.example.mmart.memesaurio.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by MMART on 9/22/2017.
 */
public class DBUtils extends SQLiteOpenHelper {
    public static final String DATABASE_NAME ="MemesaurioDB";
    public static final int DATABASE_VERSION = 2;

    public static final String POST_TABLE="Post";
    public static final String POST_BASEID = "baseId";
    public static final String POST_ID = "id";
    public static final String POST_USER = "userId";
    public static final String POST_TITLE ="title";
    public static final String POST_BODY ="body";

    public static final String COMMENT_TABLE ="Comment";
    public static final String COMMENT_BASEID = "baseId";
    public static final String COMMENT_ID = "id";
    public static final String COMMENT_POST = "postId";
    public static final String COMMENT_NAME ="name";
    public static final String COMMENT_EMAIL ="email";
    public static final String COMMENT_BODY ="body";

    public static final String CREATE_POSTS =
            "CREATE TABLE "+ POST_TABLE + "(" +
                    POST_BASEID + " INTEGER PRIMARY KEY, " +
                    POST_ID + " INTEGER, " +
                    POST_USER + " INTEGER, " +
                    POST_TITLE + " TEXT," +
                    POST_BODY + " TEXT)";

    public static final String CREATE_COMMENTS =
            "CREATE TABLE "+ COMMENT_TABLE + "(" +
                    COMMENT_BASEID + " INTEGER PRIMARY KEY, " +
                    COMMENT_ID+" INTEGER, " +
                    COMMENT_POST + " INTEGER, " +
                    COMMENT_NAME + " TEXT," +
                    COMMENT_EMAIL + " TEXT," +
                    COMMENT_BODY + " TEXT)";

    public DBUtils(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_POSTS);
        db.execSQL(CREATE_COMMENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + POST_TABLE + "");
        db.execSQL("DROP TABLE IF EXISTS " + COMMENT_TABLE + "");
        onCreate(db);
    }
}
