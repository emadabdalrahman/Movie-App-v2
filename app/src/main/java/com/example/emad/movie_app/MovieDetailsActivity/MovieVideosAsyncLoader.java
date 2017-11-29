package com.example.emad.movie_app.MovieDetailsActivity;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.example.emad.movie_app.Utilities.FinalData;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.emad.movie_app.Utilities.FinalData.API_KEY;

/**
 * Created by emad on 2/19/2017.
 */

public class MovieVideosAsyncLoader extends AsyncTaskLoader<String> {

    public String MovieID;
    public MovieVideosAsyncLoader(Context context, String movieID) {
        super(context);
        this.MovieID = movieID;
    }

    @Override
    public String loadInBackground() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api.themoviedb.org/3/movie/"+MovieID+"/videos?api_key="+ API_KEY+"&language=en-US")
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
