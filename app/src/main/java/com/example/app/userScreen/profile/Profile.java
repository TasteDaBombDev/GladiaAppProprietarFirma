package com.example.app.userScreen.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


import com.example.app.R;
import com.example.app.userScreen.Evenimente;
import com.example.app.userScreen.MainScreen;
import com.example.app.userScreen.MapActivity;
import com.example.app.userScreen.Poze;

public class Profile extends Fragment {


    private static Profile INSTANCE = null;

    private static int ID;
    private static String username, nume, prenume, password, mail, ziuaDeNastere, sex, nrtel;
    private ImageButton imageButton, toEvents;

    private View view;

    public Profile(){
    }

    public static Profile getINSTANCE(){
        if (INSTANCE == null)
            INSTANCE = new Profile();
        return INSTANCE;
    }

    public static void resetINSTANCE(){
        INSTANCE = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile_fragment,container,false);
        init();

        ConstraintLayout aggroZone = view.findViewById(R.id.profile);
        aggroZone.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN)
                    MapActivity.getMap().getUiSettings().setScrollGesturesEnabled(true);
                return true;
            }
        });


        toEvents = view.findViewById(R.id.createPrivateEvent);
        toEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileMainClass.getVerticalViewPager().setCurrentItem(1);
            }
        });


        imageButton = view.findViewById(R.id.toEdit);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(imageButton,"toEditProfile");

                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(getActivity(),pairs);

                Intent intent = new Intent(getContext(), EditProfile.class);
                intent.putExtra("userID", ID);
                intent.putExtra("username",username);
                intent.putExtra("nume", nume);
                intent.putExtra("prenume", prenume);
                intent.putExtra("password", password);
                intent.putExtra("mail", mail);
                intent.putExtra("ziuaDeNastere", ziuaDeNastere);
                intent.putExtra("sex", sex);
                intent.putExtra("nrtel", nrtel);
                startActivity(intent, activityOptions.toBundle());
                resetINSTANCE();
                Poze.resetINSTANCE();
                MapActivity.resetINSTANCE();
                Evenimente.resetINSTANCE();
            }
        });

        return view;
    }

    private void init(){
        ID = MainScreen.getUserID();
        username = MainScreen.getUsername();
        nume = MainScreen.getNume();
        prenume = MainScreen.getPrenume();
        password = MainScreen.getPassword();
        mail = MainScreen.getMail();
        ziuaDeNastere = MainScreen.getZiuaDeNastere();
        sex = MainScreen.getSex();
        nrtel = MainScreen.getNrtel();
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.profile_fragment);
//
//        EditText username = findViewById(R.id.usernameProfile);
//        username.setText(MainScreen.getUsername());
//
//    }
}
