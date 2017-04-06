package com.example.emad.movie_app.Utilities.DataBase;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by emad on 2/23/2017.
 */

public class MovieDbContentProvider extends ContentProvider {

    public MovieDbHelper movieDbHelper;
    public static final int MOVIE = 100;
    public static final int MOVIE_WITH_ID = 101;
    private static final UriMatcher sURI_MATCHER = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(MovieDbContract.AUTHORITY, MovieDbContract.PATH_MOVIE, MOVIE);
        uriMatcher.addURI(MovieDbContract.AUTHORITY, MovieDbContract.PATH_MOVIE + "/#", MOVIE_WITH_ID);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        movieDbHelper = new MovieDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase sqLiteDatabase = movieDbHelper.getReadableDatabase();
        Cursor cursor = null;
        switch (sURI_MATCHER.match(uri)) {
            case MOVIE:
                cursor = sqLiteDatabase.query(MovieDbContract.Movie.TABLE_NAME, MovieDbContract.Movie.allColumns, null, null, null, null, null);
                break;

            case MOVIE_WITH_ID:
                String id = uri.getPathSegments().get(1);

                String mSelection = MovieDbContract.Movie.COLUMN_MOVIE_ID + " = ? ";
                String[] mSelectionArgs = new String[]{id};

                cursor = sqLiteDatabase.query(MovieDbContract.Movie.TABLE_NAME, MovieDbContract.Movie.allColumns, mSelection, mSelectionArgs, null, null, null);
                break;
            default:

                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase sqLiteDatabase = movieDbHelper.getWritableDatabase();
        Uri returnUri = null;
        switch (sURI_MATCHER.match(uri)) {
            case MOVIE:

                long id = sqLiteDatabase.insert(MovieDbContract.Movie.TABLE_NAME, null, values);
                returnUri = ContentUris.withAppendedId(MovieDbContract.Movie.CONTENT_URI, id);
                break;

            default:

        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
