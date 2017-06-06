package com.mobile.jera.facebookapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import org.json.JSONObject;
import java.net.URL;

/**
 * Created by jera on 6/4/17.
 */

public class FriendDetails extends AppCompatActivity {
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_detail);
        context = this;
        final ImageView imageView = (ImageView) findViewById(R.id.image);
        TextView  nameTextView = (TextView) findViewById(R.id.name);
        TextView idTextView = (TextView) findViewById(R.id.number);
        final TextView genderTextView = (TextView) findViewById(R.id.gender);
        final TextView aboutTextView = (TextView) findViewById(R.id.about);
        final TextView emailTextView = (TextView) findViewById(R.id.email);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String id = intent.getStringExtra("id");
        Bundle params = new Bundle();
        params.putString("fields", "id,about,birthday,gender,email,picture.type(large)");
        new GraphRequest(AccessToken.getCurrentAccessToken(), "/"+id, params, HttpMethod.GET,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        if (response != null) {
                            try {
                                JSONObject data = response.getJSONObject();
                                Log.d("Response data:",data.toString());
                                if (data.has("picture")) {
                                    Log.d("Picture:",data.getJSONObject("picture").getJSONObject("data").getString("url"));
                                    final URL profilePicUrl = new URL (data.getJSONObject("picture").getJSONObject("data").getString("url"));
                                    Glide.with(context).load(profilePicUrl).into(imageView);
                                    if (data.has("birthday")) {
                                        emailTextView.setText(data.getJSONObject("birthday").toString());
                                    }
                                    if (data.has("gender")) {
                                        genderTextView.setText(data.getString("gender"));
                                    }
                                    if (data.has("about")) {
                                        aboutTextView.setText(data.getJSONObject("about").toString());
                                    }
                                }

                            } catch (Exception e) {
                                e.printStackTrace();

                            }
                        }
                    }
                }).executeAsync();


        nameTextView.setText(name);
        idTextView.setText(id);



    }

}
