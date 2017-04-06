package com.example.emad.movie_app.MainActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.emad.movie_app.Utilities.Movie.Movie;
import com.example.emad.movie_app.MovieDetailsActivity.MovieDetailsActivity;
import com.example.emad.movie_app.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.emad.movie_app.MainActivity.MainActivity.sMainBinding;
import static com.example.emad.movie_app.MainActivity.MainActivity.sScreenSizeWidth;

/**
 * Created by emad on 2/17/2017.
 */

public class RecycleViewAdepter extends RecyclerView.Adapter<RecycleViewAdepter.ViewHolder> {

    public Context mContext;
    public ArrayList<Movie> mMovies;
    public RecycleViewItemClickListener mListener;

    public RecycleViewAdepter(ArrayList<Movie> movies, Context context) {
        mMovies = movies;
        mContext = context;
        mListener = (RecycleViewItemClickListener) context;
    }

    public interface RecycleViewItemClickListener {
        void onItemClickListener(String movieID);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.list_item_temp, parent, false);
        ViewHolder viewHolder = new ViewHolder(mView,mContext, mMovies);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Picasso.with(mContext)
                .load(mMovies.get(position).getPosterPath())
                .placeholder(R.drawable.loading)
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;


        public ViewHolder(View itemView , final Context context, final ArrayList<Movie> movies) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.list_item_temp_image_view);
            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sScreenSizeWidth > 500){
                        mListener.onItemClickListener(movies.get(getLayoutPosition()).getID());
                    }else {
                        Intent intent = new Intent(context, MovieDetailsActivity.class);
                      //  intent.putExtra("id",movies.get(getLayoutPosition()).getID());
                        SharedPreferences sharedPreferences = context.getSharedPreferences("movie_data", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("movie_id",movies.get(getLayoutPosition()).getID());
                        editor.commit();
                        context.startActivity(intent);
                    }
                }
            });

        }
    }
}

