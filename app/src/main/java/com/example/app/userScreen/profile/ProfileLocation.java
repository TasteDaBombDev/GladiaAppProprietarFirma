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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
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
import com.google.android.material.appbar.AppBarLayout;
import com.squareup.picasso.Picasso;

import java.io.File;

public class ProfileLocation extends Fragment {

    private LinearLayout root, rootButtons;
    private ImageButton logout, toDashboard, toProcent;
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


        toDashboard = view.findViewById(R.id.toDashboard);
        toProcent = view.findViewById(R.id.toProcent);

        toDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileMainFragment.getViewPager().setCurrentItem(0);
            }
        });

        toProcent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileMainFragment.getViewPager().setCurrentItem(2);
            }
        });

        AppBarLayout appBarLayout = view.findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                ConstraintLayout root = view.findViewById(R.id.rootOfNext);
                root.setMaxHeight(appBarLayout.getHeight() - Math.abs(verticalOffset));
                ConstraintSet cs = new ConstraintSet();
                cs.clone(root);
                float l = (float) Math.abs(verticalOffset)/1032;
                float csL = l/4;
                if(csL > 0.11)
                {
                    cs.constrainPercentWidth(R.id.toDashboard, csL);
                    cs.constrainPercentWidth(R.id.toProcent, csL);
                } else {
                    cs.constrainPercentWidth(R.id.toProcent, 0.11f);
                    cs.constrainPercentWidth(R.id.toDashboard, 0.11f);
                }
                cs.applyTo(root);
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

        toDashboard = view.findViewById(R.id.toDashboard);
        toProcent = view.findViewById(R.id.toProcent);
    }
}
