package com.mobile.jera.facebookapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import org.json.JSONArray;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    LoginButton loginButton;
    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_main);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if(accessToken!=null){
            new GraphRequest(
                    accessToken,
                    "/me/friends",
                    null,
                    HttpMethod.GET,
                    new GraphRequest.Callback() {
                        public void onCompleted(GraphResponse response) {
                            Intent intent = new Intent(MainActivity.this,FriendsList.class);
                            try {
                                JSONArray rawName = response.getJSONObject().getJSONArray("data");
                                intent.putExtra("jsondata", rawName.toString());
                                startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            ).executeAsync();
        } else {
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult login_result) {
                     new GraphRequest(
                            login_result.getAccessToken(),
                            "/me/friends",
                            null,
                            HttpMethod.GET,
                            new GraphRequest.Callback() {
                                public void onCompleted(GraphResponse response) {
                                    Intent intent = new Intent(MainActivity.this, FriendsList.class);
                                    try {
                                        JSONArray rawName = response.getJSONObject().getJSONArray("data");
                                        intent.putExtra("jsondata", rawName.toString());
                                        startActivity(intent);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                    ).executeAsync();

                }

                @Override
                public void onCancel() {
                }

                @Override
                public void onError(FacebookException exception) {
                }
            });
        }



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }

}
