package com.pnv.matchmaking.love;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        List<Friend> image_details = getListData();
        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new CustomListAdapter(this, image_details));

        // Khi người dùng click vào các ListItem
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = listView.getItemAtPosition(position);
                Friend friend = (Friend) o;
                Toast.makeText(MainActivity.this, "Selected :" + " " + friend, Toast.LENGTH_LONG).show();
            }
        });
    }

    private  List<Friend> getListData() {
        List<Friend> list = new ArrayList<Friend>();
        Friend friend_1 = new Friend("Long Seven", "a1", "I Love You");
        Friend friend_2 = new Friend("Lionel Messi", "a2", "Will you married with me");
        Friend friend_3 = new Friend("Tony ja", "a3", "I wanna love you");
        Friend friend_4 = new Friend("SonTung_MTP","a4","I Love U");
        Friend friend_5 = new Friend("Super Spiderman","a5","I Love U");
        Friend friend_6 = new Friend("Super Man","a6","I Love U");

        list.add(friend_1);
        list.add(friend_2);
        list.add(friend_3);
        list.add(friend_4);
        list.add(friend_5);
        list.add(friend_6);

        return list;
    }

}
