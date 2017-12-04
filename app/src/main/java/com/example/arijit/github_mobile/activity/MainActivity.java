package com.example.arijit.github_mobile.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.arijit.github_mobile.R;
import com.example.arijit.github_mobile.fragments.ProfileFragment;
import com.example.arijit.github_mobile.fragments.RepoFragment;
import com.example.arijit.github_mobile.fragments.SearchFragment;
import com.example.arijit.github_mobile.pref.AppPreference;
import com.example.arijit.github_mobile.utils.ActivityUtil;

public class MainActivity extends AppCompatActivity implements ProfileFragment.OnFragmentInteractionListener, SearchFragment.OnFragmentInteractionListener, RepoFragment.OnFragmentInteractionListener {

    private FragmentManager mFragmentManager;
    private static BottomNavigationView navigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        navigation = findViewById(R.id.navigation);
//        BottomNavigationViewHelper.removeShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mFragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            ActivityUtil.replaceFragmentToActivity(mFragmentManager, new ProfileFragment(), R.id.fragment_container);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.profile_menu:
                    ActivityUtil.replaceFragmentToActivity(mFragmentManager, new ProfileFragment(), R.id.fragment_container);
                    return true;
                case R.id.search_menu:
                    ActivityUtil.replaceFragmentToActivity(mFragmentManager, new SearchFragment(), R.id.fragment_container);
                    return true;
                case R.id.repo_menu:
                    ActivityUtil.replaceFragmentToActivity(mFragmentManager, new RepoFragment(), R.id.fragment_container);
                    return true;
                case R.id.logout_menu:
                    AppPreference.getInstance().setAccessToken(null);
                    AppPreference.getInstance().setAuthCode(null);
                    AppPreference.getInstance().clearUser();

                    Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);
                    MainActivity.this.startActivity(myIntent);
                   finish();
                    return true;
            }
            return false;
        }

    };

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
