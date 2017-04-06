package com.example.emad.movie_app.MovieDetailsActivity;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.emad.movie_app.MainActivity.MainActivity;
import com.example.emad.movie_app.R;
import com.example.emad.movie_app.Utilities.DataBase.MovieDbContract;
import com.example.emad.movie_app.Utilities.Movie.MovieDetails;
import com.example.emad.movie_app.Utilities.Movie.MovieReview;
import com.example.emad.movie_app.Utilities.Movie.MovieTrailer;
import com.example.emad.movie_app.databinding.MovieDetailsFragmentBinding;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.util.TypedValue.COMPLEX_UNIT_SP;
import static com.example.emad.movie_app.Utilities.FinalData.MOVIE_DETAILS_LOADER_ID;
import static com.example.emad.movie_app.Utilities.FinalData.MOVIE_REVIEWS_LOADER_ID;
import static com.example.emad.movie_app.Utilities.FinalData.MOVIE_VIDEOS_LOADER_ID;

/**
 * Created by emad on 2/17/2017.
 */

public class MovieDetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<String>, View.OnClickListener {

    public String mMovieID ;
    public MovieDetails mMovieDetails;
    public String trailer_url;
    public MovieDetailsFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.movie_details_fragment, container, false);
        binding.favoriteButton.setOnClickListener(this);

        if (isOnline()){
            LoaderManager loaderManager = getActivity().getSupportLoaderManager();
            getMovieID();
            if (isFavorite()) {
                binding.favoriteButton.setImageResource(R.drawable.ic_favorite_white_48dp);
                binding.favoriteButton.setClickable(false);
                putMovieDetailsDataInFrame(mMovieDetails);
                startMovieVideosLoader(loaderManager);
                startMovieReviewsLoader(loaderManager);
            } else {
                startMovieDetailsLoader(loaderManager);
                startMovieVideosLoader(loaderManager);
                startMovieReviewsLoader(loaderManager);
            }
        }else {
            getMovieID();
            binding.movieDetailsNoConnection.setVisibility(View.VISIBLE);
            binding.onlineData.setVisibility(View.INVISIBLE);
            if (isFavorite()){
                binding.favoriteButton.setImageResource(R.drawable.ic_favorite_white_48dp);
                binding.favoriteButton.setClickable(false);
                putMovieDetailsDataInFrame(mMovieDetails);
            }else {
                binding.movieDetailsFrame.setVisibility(View.INVISIBLE);
                binding.movieDetailsBackgroundImg.setImageResource(R.drawable.no_internet_connection);
            }
        }


        return binding.getRoot();
    }

    public void getMovieID(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("movie_data", Context.MODE_PRIVATE);
        mMovieID = sharedPreferences.getString("movie_id",null);
//        if (MainActivity.sScreenSizeWidth > 500) {
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            mMovieID = getArguments().getString("id");
//            editor.putString("movie_id",mMovieID);
//            editor.commit();
//        } else {
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            mMovieID = getActivity().getIntent().getExtras().getString("id","empty");
//          //  editor.putString("movie_id",mMovieID);
//          //  editor.commit();
//        }
//
//        if (mMovieID == null){
//            mMovieID = sharedPreferences.getString("movie_id",null);
//        }
    }

    public void startMovieDetailsLoader(LoaderManager loaderManager) {
        Loader<String> loader = loaderManager.getLoader(MOVIE_DETAILS_LOADER_ID);
        if (loader == null) {
            loaderManager.initLoader(MOVIE_DETAILS_LOADER_ID, null, this).forceLoad();
        } else {
            loaderManager.restartLoader(MOVIE_DETAILS_LOADER_ID, null, this).startLoading();
        }
    }

    public void startMovieReviewsLoader(LoaderManager loaderManager) {
        Loader<String> loader = loaderManager.getLoader(MOVIE_REVIEWS_LOADER_ID);
        if (loader == null) {
            loaderManager.initLoader(MOVIE_REVIEWS_LOADER_ID, null, this).forceLoad();
        } else {
            loaderManager.restartLoader(MOVIE_REVIEWS_LOADER_ID, null, this).startLoading();
        }
    }

    public void startMovieVideosLoader(LoaderManager loaderManager) {
        Loader<String> loader = loaderManager.getLoader(MOVIE_VIDEOS_LOADER_ID);
        if (loader == null) {
            loaderManager.initLoader(MOVIE_VIDEOS_LOADER_ID, null, this).forceLoad();
        } else {
            loaderManager.restartLoader(MOVIE_VIDEOS_LOADER_ID, null, this).startLoading();
        }
    }

    public void putMovieDetailsDataInFrame(MovieDetails movieDetails) {
        binding.originalTitle.setText(movieDetails.getOriginalTitle());
        binding.releaseDate.setText(movieDetails.getReleaseDate());
        binding.runtime.setText("" + movieDetails.getRuntime() + "");
        binding.voteAverage.setText("" + movieDetails.getVoteAverage() + "");
        binding.overview.setText(movieDetails.getOverview());
        Picasso.with(getContext())
                .load(movieDetails.getBackdropPath())
                .placeholder(R.drawable.loading)
                .into(binding.movieDetailsBackgroundImg);
        Picasso.with(getContext())
                .load(movieDetails.getPosterPath())
                .placeholder(R.drawable.loading)
                .into(binding.moviePoster);
    }

    public void putMovieReviewsDataInFrame(ArrayList<MovieReview> movieReviews) {
        for (int i = 0; i < movieReviews.size(); i++) {

            TextView Author = new TextView(getContext());
            Author.setText("Author : " + movieReviews.get(i).getAuthor() + "\n");
            Author.setTextSize(COMPLEX_UNIT_SP, 16);
            Author.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            binding.reviews.addView(Author);

            TextView Content = new TextView(getContext());
            Content.setText("Content : " + movieReviews.get(i).getContent() + "\n");
            Content.setTextSize(COMPLEX_UNIT_SP, 16);
            Content.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            binding.reviews.addView(Content);
        }

    }

    public void putMovieTrailerDataInFrame(final ArrayList<MovieTrailer> movieTrailers) {
        for (int i = 0; i < movieTrailers.size(); i++) {
            final TextView textView = new TextView(getContext());
            textView.setText(movieTrailers.get(i).getName());
            trailer_url = "https://www.youtube.com/watch?v=" + movieTrailers.get(i).getKey();
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailer_url));
                    startActivity(intent);
                }
            });

            binding.trailer.addView(textView);
        }
    }

    public MovieDetails getMovieDetails(String data) {
        MovieDetails mMovieDetails = new MovieDetails();
        try {
            JSONObject mRoot = new JSONObject(data);
            mMovieDetails.setPosterPath("http://image.tmdb.org/t/p/w185" + mRoot.getString("poster_path"));
            mMovieDetails.setID(""+mRoot.getInt("id")+"");
            mMovieDetails.setOriginalTitle(mRoot.getString("original_title"));
            mMovieDetails.setReleaseDate(mRoot.getString("release_date"));
            mMovieDetails.setRuntime(mRoot.getInt("runtime"));
            mMovieDetails.setVoteAverage(mRoot.getInt("vote_average"));
            mMovieDetails.setOverview(mRoot.getString("overview"));
            mMovieDetails.setBackdropPath("http://image.tmdb.org/t/p/w500" + mRoot.getString("backdrop_path"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mMovieDetails;
    }

    public ArrayList<MovieReview> getMovieReviews(String data) {
        ArrayList<MovieReview> movieReviews = new ArrayList<>();
        try {
            JSONObject mRoot = new JSONObject(data);
            JSONArray mResults = mRoot.getJSONArray("results");
            for (int i = 0; i < mResults.length(); i++) {
                MovieReview movieReview = new MovieReview();
                JSONObject mReview = mResults.getJSONObject(i);
                movieReview.setAuthor(mReview.getString("author"));
                movieReview.setContent(mReview.getString("content"));
                movieReviews.add(movieReview);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movieReviews;
    }

    public ArrayList<MovieTrailer> getMovieTrailer(String data) {
        ArrayList<MovieTrailer> movieTrailers = new ArrayList<>();
        try {
            JSONObject mRoot = new JSONObject(data);
            JSONArray mResults = mRoot.getJSONArray("results");

            for (int i = 0; i < mResults.length(); i++) {
                MovieTrailer movieTrailer = new MovieTrailer();
                JSONObject mTrailer = mResults.getJSONObject(i);
                movieTrailer.setKey(mTrailer.getString("key"));
                movieTrailer.setName(mTrailer.getString("name"));
                movieTrailer.setSite(mTrailer.getString("site"));
                movieTrailer.setType(mTrailer.getString("type"));
                movieTrailers.add(movieTrailer);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movieTrailers;
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {

        if (id == MOVIE_DETAILS_LOADER_ID) {
            return new MovieDetailsAsyncLoader(getContext(), mMovieID);
        } else if (id == MOVIE_REVIEWS_LOADER_ID) {
            return new MovieReviewsAsyncLoader(getContext(), mMovieID);
        } else if (id == MOVIE_VIDEOS_LOADER_ID) {
            return new MovieVideosAsyncLoader(getContext(), mMovieID);
        } else {
            return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        if (loader.getId() == MOVIE_DETAILS_LOADER_ID) {
            mMovieDetails = new MovieDetails();
            mMovieDetails = getMovieDetails(data);
            putMovieDetailsDataInFrame(mMovieDetails);
        } else if (loader.getId() == MOVIE_REVIEWS_LOADER_ID) {
            putMovieReviewsDataInFrame(getMovieReviews(data));
        } else if (loader.getId() == MOVIE_VIDEOS_LOADER_ID) {
            putMovieTrailerDataInFrame(getMovieTrailer(data));
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

    public boolean isFavorite() {
        Uri uri = MovieDbContract.Movie.CONTENT_URI.buildUpon().appendPath("" + mMovieID + "").build();
        Cursor cursor = getActivity().getContentResolver().query(MovieDbContract.Movie.CONTENT_URI, null, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                if (cursor.getString(cursor.getColumnIndex(MovieDbContract.Movie.COLUMN_MOVIE_ID)).equals(mMovieID)) {
                    mMovieDetails = new MovieDetails();
                    mMovieDetails = getMovieDetails(cursor);
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    public MovieDetails getMovieDetails(Cursor cursor) {
        MovieDetails movieDetails = new MovieDetails();
        movieDetails.setID(cursor.getString(cursor.getColumnIndex(MovieDbContract.Movie.COLUMN_MOVIE_ID)));
        movieDetails.setOverview(cursor.getString(cursor.getColumnIndex(MovieDbContract.Movie.COLUMN_MOVIE_OVERVIEW)));
        movieDetails.setOriginalTitle(cursor.getString(cursor.getColumnIndex(MovieDbContract.Movie.COLUMN_MOVIE_ORIGINAL_TITLE)));
        movieDetails.setPosterPath(cursor.getString(cursor.getColumnIndex(MovieDbContract.Movie.COLUMN_MOVIE_POSTER_PATH)));
        movieDetails.setBackdropPath(cursor.getString(cursor.getColumnIndex(MovieDbContract.Movie.COLUMN_MOVIE_BACK_DROP_PATH)));
        movieDetails.setReleaseDate(cursor.getString(cursor.getColumnIndex(MovieDbContract.Movie.COLUMN_MOVIE_RELEASE_DATE)));
        movieDetails.setVoteAverage(cursor.getInt(cursor.getColumnIndex(MovieDbContract.Movie.COLUMN_MOVIE_VOTE_AVERAGE)));
        movieDetails.setRuntime(cursor.getInt(cursor.getColumnIndex(MovieDbContract.Movie.COLUMN_MOVIE_RUNTIME)));
        return movieDetails;
    }

    @Override
    public void onClick(View v) {

        ContentValues values = new ContentValues();

        values.put(MovieDbContract.Movie.COLUMN_MOVIE_ID, mMovieDetails.getID());
        values.put(MovieDbContract.Movie.COLUMN_MOVIE_POSTER_PATH, mMovieDetails.getPosterPath());
        values.put(MovieDbContract.Movie.COLUMN_MOVIE_BACK_DROP_PATH, mMovieDetails.getBackdropPath());
        values.put(MovieDbContract.Movie.COLUMN_MOVIE_ORIGINAL_TITLE, mMovieDetails.getOriginalTitle());
        values.put(MovieDbContract.Movie.COLUMN_MOVIE_OVERVIEW, mMovieDetails.getOverview());
        values.put(MovieDbContract.Movie.COLUMN_MOVIE_RELEASE_DATE, mMovieDetails.getReleaseDate());
        values.put(MovieDbContract.Movie.COLUMN_MOVIE_RUNTIME, mMovieDetails.getRuntime());
        values.put(MovieDbContract.Movie.COLUMN_MOVIE_VOTE_AVERAGE, mMovieDetails.getVoteAverage());

        getActivity().getContentResolver().insert(MovieDbContract.Movie.CONTENT_URI, values);

        binding.favoriteButton.setImageResource(R.drawable.ic_favorite_white_48dp);

    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
