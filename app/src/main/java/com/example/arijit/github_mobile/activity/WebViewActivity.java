package com.example.arijit.github_mobile.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.arijit.github_mobile.R;
import com.example.arijit.github_mobile.constants.Constants;
import com.example.arijit.github_mobile.model.AccessToken;
import com.example.arijit.github_mobile.pref.AppPreference;
import com.example.arijit.github_mobile.rest.ApiClient;
import com.example.arijit.github_mobile.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebViewActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        String s = getIntent().getStringExtra("URI_STRING");

        mWebView = (WebView) findViewById(R.id.webView1);
        clearCookies(getApplicationContext());
        mWebView.loadUrl(s);

        mWebView.clearHistory();
        mWebView.clearFormData();
        mWebView.clearCache(true);


        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                // do your handling codes here, which url is the requested url
                // probably you need to open that url rather than redirect:
                //github-android://callback?code=1c402b3e6abdac7ff519

                if (url != null && url.toString().startsWith(Constants.REDIRECT_URI)) {
//                    String code = uri.getQueryParameter("code");
                    String code = url.substring(31);
                    AppPreference.getInstance().setAuthCode(code);
                    if (TextUtils.isEmpty(AppPreference.getInstance().getAccessToken()) && !TextUtils.isEmpty(AppPreference.getInstance().getAuthCode())) {
                        ApiInterface apiService =
                                ApiClient.getClient().create(ApiInterface.class);
                        Call<AccessToken> call = apiService.getAccessToken(Constants.CLIENT_ID, Constants.CLIENT_SECRET, code);
                        call.enqueue(new Callback<AccessToken>() {
                            @Override
                            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                                if (response.body() != null && response.body().getAccessToken() != null) {
                                    AppPreference.getInstance().setAccessToken(response.body().getAccessToken());
                                    Intent myIntent = new Intent(WebViewActivity.this, MainActivity.class);

                                    WebViewActivity.this.startActivity(myIntent);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(),"Something went wrong. Please try afer sometime".toString(), Toast.LENGTH_SHORT).show();
                                }


                            }
                            @Override
                            public void onFailure(Call<AccessToken> call, Throwable t) {
                            }
                        });
                    }
                } else {
                    Log.e("aro", "last saved token " + AppPreference.getInstance().getAccessToken());


                }
//                view.loadUrl(url);
                return false; // then it is not handled by default action
            }
        });
    }

    @SuppressWarnings("deprecation")
    public static void clearCookies(Context context)
    {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
//            Log.d(C.TAG, TAG"Using clearCookies code for API >=" + String.valueOf(Build.VERSION_CODES.LOLLIPOP_MR1));
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        } else
        {
//            Log.d(C.TAG, "Using clearCookies code for API <" + String.valueOf(Build.VERSION_CODES.LOLLIPOP_MR1));
            CookieSyncManager cookieSyncMngr=CookieSyncManager.createInstance(context);
            cookieSyncMngr.startSync();
            CookieManager cookieManager=CookieManager.getInstance();
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
            cookieSyncMngr.stopSync();
            cookieSyncMngr.sync();
        }
    }

}
