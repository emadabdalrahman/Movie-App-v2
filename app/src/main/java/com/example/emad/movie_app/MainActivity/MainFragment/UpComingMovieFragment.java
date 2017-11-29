package com.example.emad.movie_app.MainActivity.MainFragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.emad.movie_app.Utilities.Movie.Movie;
import com.example.emad.movie_app.MainActivity.RecycleViewAdepter;
import com.example.emad.movie_app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.emad.movie_app.Utilities.FinalData.API_KEY;
import static com.example.emad.movie_app.Utilities.FinalData.MOVIE_UP_COMING_LOADER_ID;

/**
 * Created by emad on 2/17/2017.
 */

public class UpComingMovieFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<String>>{

    public RecycleViewAdepter mRecycleViewAdepter;
    public RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.up_coming_movie_fragment, container, false);

        if (isOnline()){
            initializeRecyclerView(mRootView);
            startUpComingMovieLoader();
        }else {
            ImageView noConnection = (ImageView)mRootView.findViewById(R.id.up_coming_movie_no_connection);
            noConnection.setVisibility(View.VISIBLE);
        }

        return mRootView;
    }

    public void startUpComingMovieLoader(){
        LoaderManager loaderManager = getActivity().getSupportLoaderManager();
        Loader<ArrayList<String>> loader = loaderManager.getLoader(MOVIE_UP_COMING_LOADER_ID);
        if (loader == null){
            loaderManager.initLoader(MOVIE_UP_COMING_LOADER_ID,null,this).forceLoad();
        }else {
            loaderManager.restartLoader(MOVIE_UP_COMING_LOADER_ID,null,this).forceLoad();
        }
    }

    public void initializeRecyclerView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.up_coming_movie_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    public ArrayList<Movie> getUpComingMovies(ArrayList<String> mMoviesResponse) {
        ArrayList<Movie> mUpComingMovies = new ArrayList<>();
        for (int i = 0; i < mMoviesResponse.size(); i++) {
            try {
                JSONObject mRoot = new JSONObject(mMoviesResponse.get(i));
                JSONArray mResults = mRoot.getJSONArray("results");
                for (int j = 0; j < mResults.length(); j++) {
                    Movie movie = new Movie();
                    JSONObject mMovie = mResults.getJSONObject(j);
                    movie.setPosterPath("http://image.tmdb.org/t/p/w185" + mMovie.getString("poster_path"));
                    movie.setID(mMovie.getString("id"));
                    mUpComingMovies.add(movie);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return mUpComingMovies;
    }

    @Override
    public Loader<ArrayList<String>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<ArrayList<String>>(getContext()) {
            @Override
            public ArrayList<String> loadInBackground() {
                ArrayList<String> mMoviesResponse = new ArrayList<>();
                for (int i = 1; i < 2; i++) {
                    try {
                        OkHttpClient mClient = new OkHttpClient();
                        Request mRequest = new Request.Builder()
                                .url("https://api.themoviedb.org/3/movie/upcoming?api_key="+API_KEY+"&language=en-US&page="+i+"")
                                .build();
                        Response mResponse = mClient.newCall(mRequest).execute();
                        mMoviesResponse.add(mResponse.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return mMoviesResponse;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<String>> loader, ArrayList<String> data) {
        mRecycleViewAdepter = new RecycleViewAdepter(getUpComingMovies(data), getActivity());
        mRecyclerView.setAdapter(mRecycleViewAdepter);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<String>> loader) {

    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
