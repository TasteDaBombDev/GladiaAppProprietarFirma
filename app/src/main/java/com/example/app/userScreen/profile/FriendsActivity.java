package com.example.app.userScreen.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.app.R;

public class FriendsActivity extends AppCompatActivity {

    ListView friendsListing;
    String names[] = {"rares", "luca", "irina"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_activity);

        ImageButton back = findViewById(R.id.backToMainScreen);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        friendsListing = findViewById(R.id.listview);
    }

//    class Adapter extends ArrayAdapter<String>{
//
//        Context context;
//        String names[];
//
//        Adapter(Context c, String names[]){
//            super(c, names);
//        }
//    }
}
