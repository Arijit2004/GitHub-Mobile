package com.example.arijit.github_mobile.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;

import com.example.arijit.github_mobile.R;
import com.example.arijit.github_mobile.constants.Constants;
import com.example.arijit.github_mobile.fragments.ProfileFragment;
import com.example.arijit.github_mobile.fragments.RepoFragment;
import com.example.arijit.github_mobile.fragments.SearchFragment;
import com.example.arijit.github_mobile.helper.BottomNavigationViewHelper;
import com.example.arijit.github_mobile.model.AccessToken;
import com.example.arijit.github_mobile.pref.AppPreference;
import com.example.arijit.github_mobile.rest.ApiClient;
import com.example.arijit.github_mobile.rest.ApiInterface;
import com.example.arijit.github_mobile.utils.ActivityUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

//        Uri uri = getIntent().getData();
//        if (uri != null && uri.toString().startsWith(Constants.REDIRECT_URI)) {
//            String code = uri.getQueryParameter("code");
//            Log.e("aro", "auth code success " + code);
//            AppPreference.getInstance().setAuthCode(code);
//            if (TextUtils.isEmpty(AppPreference.getInstance().getAccessToken())) {
//                ApiInterface apiService =
//                        ApiClient.getClient().create(ApiInterface.class);
//                Call<AccessToken> call = apiService.getAccessToken(Constants.CLIENT_ID, Constants.CLIENT_SECRET, code);
//                call.enqueue(new Callback<AccessToken>() {
//                    @Override
//                    public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
//                        Log.e("aro", "access token success " + response.body().getAccessToken());
//                        AppPreference.getInstance().setAccessToken(response.body().getAccessToken());
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<AccessToken> call, Throwable t) {
//                        Log.e("aro", "access token failure " + t.toString());
//                    }
//                });
//            }
//        } else {
//            Log.e("aro", "uri null");
//            Log.e("aro", "last saved token " + AppPreference.getInstance().getAccessToken());
//            if (uri != null) {
//                Log.e("aro", "uri details " + uri.toString());
//            }
//
//
//        }
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
                    //
                    ActivityUtil.replaceFragmentToActivity(mFragmentManager, new RepoFragment(), R.id.fragment_container);
//                    AppPreference.getInstance().setAccessToken(null);
//                    Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);
////                    myIntent.putExtra("", value); //Optional parameters
//                    MainActivity.this.startActivity(myIntent);
//                    finish();
                    return true;
                case R.id.logout_menu:
                    AppPreference.getInstance().setAccessToken(null);
                    AppPreference.getInstance().setAuthCode(null);

                    Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);
////                    myIntent.putExtra("", value); //Optional parameters
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
