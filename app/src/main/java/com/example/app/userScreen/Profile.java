package com.example.app.userScreen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


import com.example.app.R;

public class Profile extends Fragment {


    private static Profile INSTANCE = null;

    private static int ID;
    private static String username;
    private static String nume;
    private static String prenume;
    private static String password;
    private static String mail;
    private static String ziuaDeNastere;
    private static String sex;
    private static String nrtel;
    private ImageButton imageButton;

    View view;

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
        view = inflater.inflate(R.layout.activity_profile,container,false);

        ConstraintLayout aggroZone = view.findViewById(R.id.profile);

        aggroZone.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN)
                    MapActivity.getMap().getUiSettings().setScrollGesturesEnabled(true);
                return true;
            }
        });

        ID = MainScreen.getUserID();
        username = MainScreen.getUsername();
        nume = MainScreen.getNume();
        prenume = MainScreen.getPrenume();
        password = MainScreen.getPassword();
        mail = MainScreen.getMail();
        ziuaDeNastere = MainScreen.getZiuaDeNastere();
        sex = MainScreen.getSex();
        nrtel = MainScreen.getNrtel();
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

//        Toast.makeText(getContext(), nume, Toast.LENGTH_SHORT).show();
//        Toast.makeText(getContext(), prenume, Toast.LENGTH_SHORT).show();
//        Toast.makeText(getContext(), password, Toast.LENGTH_SHORT).show();
//        Toast.makeText(getContext(), mail, Toast.LENGTH_SHORT).show();
//        Toast.makeText(getContext(), ziuaDeNastere, Toast.LENGTH_SHORT).show();
//        Toast.makeText(getContext(), sex, Toast.LENGTH_SHORT).show();
//        Toast.makeText(getContext(), nrtel, Toast.LENGTH_SHORT).show();

        ConstraintLayout constraintLayout = view.findViewById(R.id.bottomSheet);
//        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(constraintLayout);


        return view;
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_profile);
//
//        EditText username = findViewById(R.id.usernameProfile);
//        username.setText(MainScreen.getUsername());
//
//    }
}
