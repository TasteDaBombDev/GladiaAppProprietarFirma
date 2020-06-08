package com.example.app.userScreen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.app.R;

public class Evenimente extends Fragment {

    @SuppressLint("StaticFieldLeak")
    private static Evenimente INSTANCE = null;

    private View view;
    private Button closePL;
    private boolean openPL = false;
    private ConstraintLayout petrecere,concert,deschidere,festival,vernisaj,spectacol;
    private ConstraintLayout layoutRoot,petrecere_layout,concert_layout,deschidere_layout,festival_layout,vernisaj_layout,spectacol_layout;

    public Evenimente(){
    }

    public static Evenimente getINSTANCE(){
        if (INSTANCE == null)
            INSTANCE = new Evenimente();
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
        view = inflater.inflate(R.layout.events,container,false);

        init();

        openingTabs();
        closingTabs();


        return view;
    }

    private void circularRevealCard(View view, int startX, int startY){
        float finalRadius = Math.max(view.getWidth(), view.getHeight());

        Animator circularReveal = ViewAnimationUtils.createCircularReveal(view, startX, startY, 0, finalRadius * 1.1f);

        circularReveal.setDuration(700);

        circularReveal.start();
    }

    private void circularCloseCard(final View view, int startX, int startY){
        float finalRadius = Math.max(view.getWidth(), view.getHeight());

        Animator circularReveal = ViewAnimationUtils.createCircularReveal(view, startX, startY, finalRadius * 1.1f, 0);

        circularReveal.setDuration(700);

        circularReveal.start();
    }

    private void openingTabs(){
        petrecere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!openPL){
                    for (int i = 0; i < layoutRoot.getChildCount(); i++) {
                        layoutRoot.getChildAt(i).setVisibility(View.INVISIBLE);
                    }
                    petrecere_layout.setVisibility(View.VISIBLE);

                    int startX = (int) (petrecere.getX() + petrecere.getWidth()/2);
                    int startY = (int) (petrecere.getY() + petrecere.getHeight()/2);

                    circularRevealCard(petrecere_layout,startX,startY);
                    openPL = true;
                }
            }
        });


        concert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < layoutRoot.getChildCount(); i++) {
                    layoutRoot.getChildAt(i).setVisibility(View.INVISIBLE);
                }
                concert_layout.setVisibility(View.VISIBLE);
            }
        });

        deschidere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < layoutRoot.getChildCount(); i++) {
                    layoutRoot.getChildAt(i).setVisibility(View.INVISIBLE);
                }
                deschidere_layout.setVisibility(View.VISIBLE);
            }
        });


        festival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < layoutRoot.getChildCount(); i++) {
                    layoutRoot.getChildAt(i).setVisibility(View.INVISIBLE);
                }
                festival_layout.setVisibility(View.VISIBLE);
            }
        });

        vernisaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < layoutRoot.getChildCount(); i++) {
                    layoutRoot.getChildAt(i).setVisibility(View.INVISIBLE);
                }
                vernisaj_layout.setVisibility(View.VISIBLE);
            }
        });

        spectacol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < layoutRoot.getChildCount(); i++) {
                    layoutRoot.getChildAt(i).setVisibility(View.INVISIBLE);
                }
                spectacol_layout.setVisibility(View.VISIBLE);
            }
        });
    }

    private void closingTabs(){
        closePL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int startX = (int) (petrecere.getX() + petrecere.getWidth()/2);
                int startY = (int) (petrecere.getY() + petrecere.getHeight()/2);

                circularCloseCard(petrecere_layout,startX,startY);
                openPL = false;

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        petrecere_layout.setVisibility(View.INVISIBLE);
                    }
                },700);
            }
        });
    }

    private void init(){
        petrecere = view.findViewById(R.id.petrecere);
        concert = view.findViewById(R.id.concert);
        deschidere = view.findViewById(R.id.deschidere);
        festival = view.findViewById(R.id.festival);
        vernisaj = view.findViewById(R.id.vernisaj);
        spectacol = view.findViewById(R.id.spectacol);

        layoutRoot = view.findViewById(R.id.layoutsRoot);
        petrecere_layout = view.findViewById(R.id.petrecere_layout);
        concert_layout = view.findViewById(R.id.concert_layout);
        deschidere_layout = view.findViewById(R.id.deschidere_layout);
        festival_layout = view.findViewById(R.id.festival_layout);
        vernisaj_layout = view.findViewById(R.id.vernisaj_layout);
        spectacol_layout = view.findViewById(R.id.spectacol_layout);

        closePL = view.findViewById(R.id.closePL);
    }
}
