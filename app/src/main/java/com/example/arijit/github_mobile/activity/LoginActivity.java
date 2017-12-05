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
            Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
            LoginActivity.this.startActivity(myIntent);
            finish();
        }

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(AppPreference.getInstance().getAccessToken())) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.GITHUB_AUTH_URL + "?client_id=" + Constants.CLIENT_ID +
                            "&scopes=repo&redirect_uri=" + Constants.REDIRECT_URI));
//                    startActivity(intent);
//                    finish();
                    Intent myIntent = new Intent(LoginActivity.this, WebViewActivity.class);
                    myIntent.putExtra("URI_STRING", Constants.GITHUB_AUTH_URL + "?client_id=" + Constants.CLIENT_ID +
                            "&scopes=repo&redirect_uri=" + Constants.REDIRECT_URI); //Optional parameters
                    LoginActivity.this.startActivity(myIntent);

                } else {
                    Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
                    LoginActivity.this.startActivity(myIntent);

                }

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        
    }

}
