package com.example.emad.movie_app.Utilities.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by emad on 2/22/2017.
 */

public class MovieDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Movie.db";
    public static final String SQL_CREATE_MOVIE_TABLE =
            "CREATE TABLE " + MovieDbContract.Movie.TABLE_NAME + " (" +
                    MovieDbContract.Movie.COLUMN_MOVIE_ID + " TEXT," +
                    MovieDbContract.Movie.COLUMN_MOVIE_ORIGINAL_TITLE + " TEXT," +
                    MovieDbContract.Movie.COLUMN_MOVIE_POSTER_PATH + " TEXT," +
                    MovieDbContract.Movie.COLUMN_MOVIE_BACK_DROP_PATH + " TEXT," +
                    MovieDbContract.Movie.COLUMN_MOVIE_OVERVIEW + " TEXT," +
                    MovieDbContract.Movie.COLUMN_MOVIE_RELEASE_DATE + " TEXT," +
                    MovieDbContract.Movie.COLUMN_MOVIE_RUNTIME + " NUMERIC," +
                    MovieDbContract.Movie.COLUMN_MOVIE_VOTE_AVERAGE + " NUMERIC" + ")";

    public static final String SQL_DELETE_MOVIE_TABLE = "DROP TABLE IF EXISTS" + MovieDbContract.Movie.TABLE_NAME;


    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_MOVIE_TABLE);
        onCreate(db);
    }
}
