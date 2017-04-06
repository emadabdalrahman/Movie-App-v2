package com.example.emad.movie_app.Utilities.Movie;

import org.parceler.Parcel;

/**
 * Created by emad on 2/17/2017.
 */
@Parcel
public class MovieDetails extends Movie {
    public String OriginalTitle;
    public String Overview;
    public String ReleaseDate;
    public String BackdropPath;
    public int Runtime;
    public int VoteAverage;

    public String getBackdropPath() {
        return BackdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        BackdropPath = backdropPath;
    }

    public String getOriginalTitle() {
        return OriginalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        OriginalTitle = originalTitle;
    }

    public int getVoteAverage() {
        return VoteAverage;
    }

    public void setVoteAverage(int voteAverage) {
        VoteAverage = voteAverage;
    }

    public int getRuntime() {
        return Runtime;
    }

    public void setRuntime(int runtime) {
        Runtime = runtime;
    }

    public String getReleaseDate() {
        return ReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        ReleaseDate = releaseDate;
    }

    public String getOverview() {
        return Overview;
    }

    public void setOverview(String overview) {
        Overview = overview;
    }
}
