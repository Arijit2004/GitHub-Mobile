package com.example.arijit.github_mobile.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.arijit.github_mobile.R;
import com.example.arijit.github_mobile.constants.Constants;
import com.example.arijit.github_mobile.model.AccessToken;
import com.example.arijit.github_mobile.model.UserDetails;
import com.example.arijit.github_mobile.pref.AppPreference;
import com.example.arijit.github_mobile.rest.ApiClient;
import com.example.arijit.github_mobile.rest.ApiInterface;
import com.example.arijit.github_mobile.rest.Client;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mLoginButton = findViewById(R.id.login_button);

        if (!TextUtils.isEmpty(AppPreference.getInstance().getAccessToken())) {
            Log.e("aro", "access code success " + AppPreference.getInstance().getAccessToken());
            Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
//                        myIntent.putExtra("key", value); //Optional parameters
            LoginActivity.this.startActivity(myIntent);
            finish();
        }

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(AppPreference.getInstance().getAccessToken())) {
                    Log.e("aro", "onCreate access token null");
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.GITHUB_AUTH_URL + "?client_id=" + Constants.CLIENT_ID +
                            "&scopes=repo&redirect_uri=" + Constants.REDIRECT_URI));
                    startActivity(intent);
                    finish();
                } else {
                    Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
//                        myIntent.putExtra("key", value); //Optional parameters
                    LoginActivity.this.startActivity(myIntent);
                }

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        Uri uri = getIntent().getData();
        if (uri != null && uri.toString().startsWith(Constants.REDIRECT_URI)) {
            String code = uri.getQueryParameter("code");
            Log.e("aro", "auth code success on resume " + code);
            AppPreference.getInstance().setAuthCode(code);
            if (TextUtils.isEmpty(AppPreference.getInstance().getAccessToken()) && !TextUtils.isEmpty(AppPreference.getInstance().getAuthCode())) {
                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);
                Call<AccessToken> call = apiService.getAccessToken(Constants.CLIENT_ID, Constants.CLIENT_SECRET, code);
                call.enqueue(new Callback<AccessToken>() {
                    @Override
                    public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                        if (response.body() != null && response.body().getAccessToken() != null) {
                            Log.e("aro", "access token success " + response.body().getAccessToken());
                            AppPreference.getInstance().setAccessToken(response.body().getAccessToken());
//                           makeProfileDataRequest();
                            Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
//                        myIntent.putExtra("key", value); //Optional parameters
                            LoginActivity.this.startActivity(myIntent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(),"Something went wrong. Please try afer sometime".toString(), Toast.LENGTH_SHORT).show();
                        }


                    }
                    @Override
                    public void onFailure(Call<AccessToken> call, Throwable t) {
                        Log.e("aro", "access token failure " + t.toString());
                    }
                });
            }
        } else {
            Log.e("aro", "uri null");
            Log.e("aro", "last saved token " + AppPreference.getInstance().getAccessToken());
            if (uri != null) {
                Log.e("aro", "uri details " + uri.toString());
            }


        }

//        makeProfileDataRequest();
    }

    private void makeProfileDataRequest() {

        if (!TextUtils.isEmpty(AppPreference.getInstance().getAccessToken()) ) {
            ApiInterface apiService =
                    Client.getClient().create(ApiInterface.class);
            Call<UserDetails> call = apiService.getUserDetails(AppPreference.getInstance().getAccessToken());
            call.enqueue(new Callback<UserDetails>() {
                @Override
                public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {

                    if (response != null ) {
                        AppPreference.getInstance().setUser(response.body());
                    }
                    Log.e("aro", "user details success " + response.body().getLogin());
                    Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
//                        myIntent.putExtra("key", value); //Optional parameters
                    LoginActivity.this.startActivity(myIntent);
                    finish();


                }

                @Override
                public void onFailure(Call<UserDetails> call, Throwable t) {
                    Log.e("aro", "get user details failed " + t.toString());
                }
            });
        }
    }

}
