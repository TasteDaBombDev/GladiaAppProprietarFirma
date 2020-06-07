package com.example.app.userScreen.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.app.R;
import com.squareup.picasso.Picasso;

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
