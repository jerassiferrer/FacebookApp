package com.mobile.jera.facebookapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.List;

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
        friends = new ArrayList<String>();
        idList = new ArrayList<String>();
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

        //ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, friends);
        ArrayAdapter adapter = new FriendsAdapter(this,friends);
                ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }

    public class FriendsAdapter extends ArrayAdapter<String> {

        public FriendsAdapter(Context context, ArrayList<String> friends) {
            super(context, 0, friends);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            String friendName = getItem(position);
            final String friendId = idList.get(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_listview, parent, false);
            }
            TextView tvName = (TextView) convertView.findViewById(R.id.label);
            tvName.setText(friendName);
            tvName.setTag(position);
            tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int friendPosition=(int) v.getTag();
                    Log.d("FriendsListJSON:Click",friends.get(friendPosition)+idList.get(friendPosition));

                }
            });

            return convertView;
        }
    }
}

