package com.mobile.jera.facebookapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mobile.jera.facebookapp.adapters.FriendsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;

/**
 * Created by jera on 6/4/17.
 */

public class FriendsList extends AppCompatActivity {
    ArrayList<String> idList;
    ArrayList<String> friends;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);

        Intent intent = getIntent();
        String jsondata = intent.getStringExtra("jsondata");

        JSONArray friendslist;
        friends = new ArrayList<>();
        idList = new ArrayList<>();
        try {
            friendslist = new JSONArray(jsondata);
            for (int l=0; l < friendslist.length(); l++) {
                Log.d("FriendsListJSON:",friendslist.getJSONObject(l).toString());
                friends.add(friendslist.getJSONObject(l).getString("name"));
                idList.add(friendslist.getJSONObject(l).getString("id"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayAdapter adapter = new FriendsAdapter(this,friends,idList);
                ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }


}

