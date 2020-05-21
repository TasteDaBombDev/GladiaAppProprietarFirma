package com.example.app.userScreen.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app.Login;
import com.example.app.R;
import com.example.app.ServerRequest;
import com.example.app.userScreen.MainScreen;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FriendsActivity extends AppCompatActivity {

    ListView friendsListing;

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
        Adapter adapter = new Adapter(FriendsActivity.this,Profile.getNames(),Profile.getPaths());
        friendsListing.setAdapter(adapter);
    }

    class Adapter extends ArrayAdapter<String>{

        Context context;
        String names[];
        String paths[];

        Adapter(Context c, String names[], String paths[]){
            super(c, R.layout.firend_item,R.id.friendName, names);
            this.context = c;
            this.names = names;
            this.paths = paths;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            View item = layoutInflater.inflate(R.layout.firend_item,parent,false);
            TextView name = item.findViewById(R.id.friendName);
            ImageView image = item.findViewById(R.id.friendPic);
            name.setText(names[position]);
            Picasso.get().load(paths[position]).into(image);

            return item;
        }
    }
}
