package com.example.emad.movie_app.MovieDetailsActivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.emad.movie_app.Utilities.Movie.MovieReview;
import com.example.emad.movie_app.R;

import java.util.ArrayList;

/**
 * Created by emad on 2/20/2017.
 */

public class MovieReviewsRecycleViewAdepter extends RecyclerView.Adapter<MovieReviewsRecycleViewAdepter.RecycleViewHolder> {

    Context mContext;
    ArrayList<MovieReview> mMovieReviews = new ArrayList<>();

    public MovieReviewsRecycleViewAdepter(ArrayList<MovieReview> movieReviews, Context context) {
        mMovieReviews = movieReviews;
        mContext = context;
    }

    @Override
    public RecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.movie_reviews_list_item_temp, parent, false);
        RecycleViewHolder recycleViewHolder = new RecycleViewHolder(view);
        return recycleViewHolder;
    }

    @Override
    public void onBindViewHolder(RecycleViewHolder holder, int position) {
        holder.mAuthor.setText(mMovieReviews.get(position).getAuthor());
        holder.mContent.setText(mMovieReviews.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return mMovieReviews.size();
    }

    public class RecycleViewHolder extends RecyclerView.ViewHolder {

        public TextView mAuthor;
        public TextView mContent;

        public RecycleViewHolder(View itemView) {
            super(itemView);
            mContent = (TextView) itemView.findViewById(R.id.content_val);
            mAuthor = (TextView) itemView.findViewById(R.id.author_val);
        }
    }
}
