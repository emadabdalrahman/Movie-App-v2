package com.example.emad.movie_app.Utilities.DataBase;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by emad on 2/22/2017.
 */

public class MovieDbContract {

    public static final String AUTHORITY = "com.example.emad.movie_app";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_MOVIE = "movie";

    private MovieDbContract() {
    }

    public static class Movie implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static String[] allColumns = {
                MovieDbContract.Movie.COLUMN_MOVIE_ID,
                MovieDbContract.Movie.COLUMN_MOVIE_ORIGINAL_TITLE,
                MovieDbContract.Movie.COLUMN_MOVIE_POSTER_PATH,
                MovieDbContract.Movie.COLUMN_MOVIE_BACK_DROP_PATH,
                MovieDbContract.Movie.COLUMN_MOVIE_OVERVIEW,
                MovieDbContract.Movie.COLUMN_MOVIE_RELEASE_DATE,
                MovieDbContract.Movie.COLUMN_MOVIE_RUNTIME,
                MovieDbContract.Movie.COLUMN_MOVIE_VOTE_AVERAGE
        };

        public static final String TABLE_NAME = "movie";
        public static final String COLUMN_MOVIE_ID = "ID";
        public static final String COLUMN_MOVIE_ORIGINAL_TITLE = "OriginalTitle";
        public static final String COLUMN_MOVIE_POSTER_PATH = "PosterPath";
        public static final String COLUMN_MOVIE_OVERVIEW = "Overview";
        public static final String COLUMN_MOVIE_RELEASE_DATE = "ReleaseData";
        public static final String COLUMN_MOVIE_BACK_DROP_PATH = "BackDropPath";
        public static final String COLUMN_MOVIE_RUNTIME = "Runtime";
        public static final String COLUMN_MOVIE_VOTE_AVERAGE = "VoteAverage";
    }
}
