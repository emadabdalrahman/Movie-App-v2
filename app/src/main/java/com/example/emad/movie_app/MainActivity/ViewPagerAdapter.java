package com.example.emad.movie_app.MainActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.emad.movie_app.MainActivity.MainFragment.FavoriteMovieFragment;
import com.example.emad.movie_app.MainActivity.MainFragment.NowPlayingMovieFragment;
import com.example.emad.movie_app.MainActivity.MainFragment.PopularMovieFragment;
import com.example.emad.movie_app.MainActivity.MainFragment.TopRatedFragment;
import com.example.emad.movie_app.MainActivity.MainFragment.UpComingMovieFragment;

/**
 * Created by emad on 2/17/2017.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public Fragment[] mFragment = {
            new PopularMovieFragment(),
            new TopRatedFragment(),
            new UpComingMovieFragment(),
            new NowPlayingMovieFragment(),
            new FavoriteMovieFragment()
    };

    public String[] mTitle = {"Popular","Top Rated","Upcoming","NowPlaying","Favorite"};

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragment[position];
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle[position];
    }

    @Override
    public int getCount() {
        return mFragment.length;
    }
}
