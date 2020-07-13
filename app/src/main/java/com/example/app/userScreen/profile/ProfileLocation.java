package com.example.app.userScreen.profile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.app.MainActivity;
import com.example.app.R;
import com.example.app.SplashScreen;
import com.example.app.userScreen.events.ListEvents;
import com.example.app.userScreen.MainScreen;
import com.example.app.userScreen.createEvents.petreceri.PetreceriPage1;
import com.example.app.userScreen.createEvents.petreceri.PetreceriPage2;
import com.example.app.userScreen.createEvents.petreceri.PetreceriPage3;
import com.example.app.userScreen.createEvents.petreceri.PetreceriPage4;
import com.example.app.userScreen.createEvents.petreceri.PetreceriPage5;
import com.squareup.picasso.Picasso;

import java.io.File;

public class ProfileLocation extends Fragment {

    private LinearLayout root, rootButtons;
    private ImageButton logout, newI, toDashboard, toProcent;
    private View view;
    private boolean btnVisible = false;
    private ImageView profilePic;
    private TextView descriere, name, adress, email, tema, decor, muzica, dresscode, shortUN;

    public ProfileLocation(){
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.location_profile,container,false);
        root = view.findViewById(R.id.root);

        String[] menus = MainScreen.getMenu().split(", ");
        for (int i = 0; i < menus.length; i++) {
            TextView t = new TextView(getContext());
            menus[i].replace("#", " - ");
            t.setText(menus[i]);
            root.addView(t);
        }

        logout = view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                File fdelete = new File(SplashScreen.getPath());
                if (fdelete.exists()) {
                    boolean t = fdelete.delete();
                    if(t)
                    {
                        ListEvents.deleteAll();
                        PetreceriPage1.resetINSTANCE();
                        PetreceriPage2.resetINSTANCE();
                        PetreceriPage3.resetINSTANCE();
                        PetreceriPage4.resetINSTANCE();
                        PetreceriPage5.resetINSTANCE();
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(getContext(),"Failed to logout",Toast.LENGTH_LONG).show();
                }
            }
        });

        init();

        final Animation animRotateIn = new RotateAnimation(0.0f, 45.0f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        animRotateIn.setDuration(300);
        animRotateIn.setFillAfter(true);

        final Animation animRotateOut = new RotateAnimation(45.0f, 0.0f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        animRotateOut.setDuration(300);
        animRotateOut.setFillAfter(true);

        newI.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                TransitionManager.beginDelayedTransition(rootButtons);
                if(!btnVisible){
                    btnVisible = true;
                    newI.startAnimation(animRotateIn);
                    toDashboard.setVisibility(View.VISIBLE);
                    toProcent.setVisibility(View.VISIBLE);
                } else {
                    btnVisible = false;
                    toProcent.setVisibility(View.GONE);
                    toDashboard.setVisibility(View.GONE);
                    newI.startAnimation(animRotateOut);
                }
            }
        });

        toDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Dashboard.class);
                startActivity(intent);
            }
        });

        toProcent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Sales.class);
                startActivity(intent);
            }
        });



        name.setText(MainScreen.getNume());
        adress.setText(MainScreen.getAdresa());
        email.setText(MainScreen.getMail());
        tema.setText(MainScreen.getTema());
        dresscode.setText(MainScreen.getDressCode());
        muzica.setText(MainScreen.getMuzica());
        decor.setText(MainScreen.getDecor());
        StringBuilder initials = new StringBuilder();
        for (String s : MainScreen.getNume().split(" ")) {
            initials.append(s.charAt(0));
        }
        descriere.setText(MainScreen.getDescriere());

        return view;
    }

    private void init(){
        profilePic = view.findViewById(R.id.profilePic);
        Picasso.get().load(MainScreen.getPozaPath()).into(profilePic);


        name = view.findViewById(R.id.name);
        adress = view.findViewById(R.id.adress);
        email = view.findViewById(R.id.mail);
        tema = view.findViewById(R.id.tema);
        muzica = view.findViewById(R.id.muzica);
        dresscode = view.findViewById(R.id.dresscode);
        decor = view.findViewById(R.id.decor);
        descriere = view.findViewById(R.id.descriereFirma);

        newI = view.findViewById(R.id.newI);
        toDashboard = view.findViewById(R.id.toDashboard);
        toProcent = view.findViewById(R.id.toProcent);
        rootButtons = view.findViewById(R.id.rootButtons);
    }
}
