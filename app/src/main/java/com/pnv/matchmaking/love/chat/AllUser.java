package com.pnv.matchmaking.love.chat;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pnv.matchmaking.love.R;
import com.pnv.matchmaking.love.User;

import java.util.ArrayList;
import java.util.Collections;

public class AllUser extends Activity {

    private static final String TAG = ChatDetailActivity.class.getSimpleName();
    private RecyclerView recycler_view_list_user;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;



    CustomRecyclerAdapterUser customRecyclerAdapterUser;
    ArrayList<User> arr_user = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);

        recycler_view_list_user = (RecyclerView) findViewById(R.id.recycler_view_list_user);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(AllUser.this);
        recycler_view_list_user.setLayoutManager(layoutManager);
        recycler_view_list_user.setItemAnimator(new DefaultItemAnimator());
        recycler_view_list_user.setNestedScrollingEnabled(false);
        recycler_view_list_user.setHasFixedSize(false);

        mFirebaseInstance = FirebaseDatabase.getInstance();

        mFirebaseDatabase = mFirebaseInstance.getReference("users");


        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arr_user.clear();
                for (final DataSnapshot data : dataSnapshot.getChildren()) {
                    User user = data.getValue(User.class);
                    arr_user.add(user);
                }

                Collections.reverse(arr_user);
                recycler_view_list_user.setAdapter(customRecyclerAdapterUser);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                // ...
            }
        });


        customRecyclerAdapterUser = new CustomRecyclerAdapterUser(arr_user, this);

    }

}
