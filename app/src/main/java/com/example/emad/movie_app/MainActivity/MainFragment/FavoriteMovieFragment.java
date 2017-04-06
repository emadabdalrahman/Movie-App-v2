package com.example.emad.movie_app.MainActivity.MainFragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.emad.movie_app.Utilities.DataBase.MovieDbContract;
import com.example.emad.movie_app.Utilities.Movie.Movie;
import com.example.emad.movie_app.MainActivity.RecycleViewAdepter;
import com.example.emad.movie_app.R;

import java.util.ArrayList;


/**
 * Created by emad on 2/17/2017.
 */

public class FavoriteMovieFragment extends Fragment {

    public ArrayList<Movie> mFavoriteMovies;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.favorite_movie_fragment, container, false);

        mFavoriteMovies = new ArrayList<>();
        mFavoriteMovies = getFavoriteMovies();
        if (mFavoriteMovies.size()!=0) {
            initializeRecycleView(mRootView);
            mRootView.findViewById(R.id.favorite_progressbar).setVisibility(View.INVISIBLE);
        }else {
            mRootView.findViewById(R.id.favorite_progressbar).setVisibility(View.INVISIBLE);
            mRootView.findViewById(R.id.empty).setVisibility(View.VISIBLE);
            mRootView.findViewById(R.id.description).setVisibility(View.VISIBLE);
            mRootView.findViewById(R.id.empty_movie_img).setVisibility(View.VISIBLE);
        }

        return mRootView;
    }

    public void initializeRecycleView(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.favorite_movie_recycler_view);
        recyclerView.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        RecycleViewAdepter recycleViewAdepter = new RecycleViewAdepter(mFavoriteMovies, getActivity());
        recyclerView.setAdapter(recycleViewAdepter);
    }

    public ArrayList<Movie> getFavoriteMovies() {
        ArrayList<Movie> movies = new ArrayList<>();
       Cursor cursor = getActivity().getContentResolver().query(MovieDbContract.Movie.CONTENT_URI,null,null,null,null);
        if (cursor.getCount()>0){
           while (cursor.moveToNext()){
               Movie movie = new Movie();
               movie.setID(cursor.getString(cursor.getColumnIndex(MovieDbContract.Movie.COLUMN_MOVIE_ID)));
               movie.setPosterPath(cursor.getString(cursor.getColumnIndex(MovieDbContract.Movie.COLUMN_MOVIE_POSTER_PATH)));
               movies.add(movie);
           }
        }
        return movies;
    }

}
