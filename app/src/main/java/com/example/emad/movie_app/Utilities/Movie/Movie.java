package com.example.emad.movie_app.Utilities.Movie;

import org.parceler.Parcel;

/**
 * Created by emad on 2/17/2017.
 */
@Parcel
public class Movie  {

    public String ID;
    public String PosterPath;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPosterPath() {
        return PosterPath;
    }

    public void setPosterPath(String posterPath) {
        PosterPath = posterPath;
    }
}
