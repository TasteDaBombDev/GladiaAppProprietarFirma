package com.example.app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;


import android.widget.Toast;

public class Profile extends Fragment {


    private static Profile INSTANCE = null;

    private static String username;
    private static String nume;
    private static String prenume;
    private static String password;
    private static String mail;
    private static String ziuaDeNastere;
    private static String sex;
    private static String nrtel;

    View view;

    public Profile(){
    }

    public static Profile getINSTANCE(){
        if (INSTANCE == null)
            INSTANCE = new Profile();
        return INSTANCE;
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

        username = MainScreen.getUsername();
        nume = MainScreen.getNume();
        prenume = MainScreen.getPrenume();
        password = MainScreen.getPassword();
        mail = MainScreen.getMail();
        ziuaDeNastere = MainScreen.getZiuaDeNastere();
        sex = MainScreen.getSex();
        nrtel = MainScreen.getNrtel();

        Toast.makeText(getContext(), nume, Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(), prenume, Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(), password, Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(), mail, Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(), ziuaDeNastere, Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(), sex, Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(), nrtel, Toast.LENGTH_SHORT).show();


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
