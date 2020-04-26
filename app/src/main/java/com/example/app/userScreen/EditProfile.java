package com.example.app.userScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ImageButton;

import com.example.app.R;


public class EditProfile extends AppCompatActivity {

    private ImageButton imageButton;
    private ConstraintLayout constraintLayout;
    private static int userID;
    private static String username;
    private static String nume;
    private static String prenume;
    private static String password;
    private static String mail;
    private static String ziuaDeNastere;
    private static String sex;
    private static String nrtel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        Bundle extras = getIntent().getExtras();
        userID = extras.getInt("userID");
        username = extras.getString("username");
        nume = extras.getString("nume");
        prenume = extras.getString("prenume");
        password = extras.getString("password");
        mail = extras.getString("mail");
        ziuaDeNastere = extras.getString("ziuaDeNastere");
        sex = extras.getString("sex");
        nrtel = extras.getString("nrtel");

        imageButton = findViewById(R.id.toSlider);
        constraintLayout = findViewById(R.id.editProfile);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(constraintLayout,"toEditProfile");

                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(EditProfile.this,pairs);

                Intent intent = new Intent(EditProfile.this, MainScreen.class);
                intent.putExtra("userID", userID);
                intent.putExtra("username",username);
                intent.putExtra("nume", nume);
                intent.putExtra("prenume", prenume);
                intent.putExtra("password", password);
                intent.putExtra("mail", mail);
                intent.putExtra("ziuaDeNastere", ziuaDeNastere);
                intent.putExtra("sex", sex);
                intent.putExtra("nrtel", nrtel);
                intent.putExtra("pannel",1);
                startActivity(intent,activityOptions.toBundle());
            }
        });
    }
}
