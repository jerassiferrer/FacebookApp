package com.mobile.jera.facebookapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mobile.jera.facebookapp.FriendDetails;
import com.mobile.jera.facebookapp.R;

import java.util.ArrayList;

/**
 * Created by jera on 6/5/17.
 */

public class FriendsAdapter extends ArrayAdapter<String> {
    private ArrayList<String> friends;
    private ArrayList<String> idList;


    public FriendsAdapter(Context context, ArrayList<String> friendsArray, ArrayList<String> idArray) {
        super(context, 0, friendsArray);
        friends = friendsArray;
        idList = idArray;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String friendName = getItem(position);
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
                Intent intent = new Intent(getContext(),FriendDetails.class);
                intent.putExtra("name", friends.get(friendPosition));
                intent.putExtra("id", idList.get(friendPosition));
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }
}

