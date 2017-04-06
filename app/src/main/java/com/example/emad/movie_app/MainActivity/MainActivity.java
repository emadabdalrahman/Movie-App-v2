package com.example.emad.movie_app.MainActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import com.example.emad.movie_app.MovieDetailsActivity.MovieDetailsFragment;
import com.example.emad.movie_app.R;
import com.example.emad.movie_app.Utilities.ScreenSize;
import com.example.emad.movie_app.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements RecycleViewAdepter.RecycleViewItemClickListener {


    public static ActivityMainBinding sMainBinding;
    public static double sScreenSizeWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sMainBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);

        ScreenSize screenSize = new ScreenSize(this);
        sScreenSizeWidth = screenSize.getWidth_dp();

        initializeToolbar();
        initializeViewPager();
        initializeTabLayout();

    }

    public void initializeToolbar() {
        sMainBinding.mainToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(sMainBinding.mainToolbar);
    }

    public void initializeViewPager() {
        ViewPagerAdapter mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        sMainBinding.mainViewPager.setAdapter(mViewPagerAdapter);
    }

    public void initializeTabLayout() {
        TabLayout mTabLayout = (TabLayout) findViewById(R.id.main_tab);
        mTabLayout.setupWithViewPager(sMainBinding.mainViewPager);
    }

    @Override
    public void onItemClickListener(String movieID) {
        MovieDetailsFragment movieDetailsFragment = new MovieDetailsFragment();
        SharedPreferences sharedPreferences = getSharedPreferences("movie_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("movie_id",movieID);
        editor.commit();

        getSupportFragmentManager().beginTransaction().replace(R.id.main_activity_frame, movieDetailsFragment).commit();
    }
}
