package com.example.app.userScreen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.app.R;
import com.example.app.userScreen.profile.petreceriFiles.EnumFragmentsPetreceri;

public class Evenimente extends Fragment {

    @SuppressLint("StaticFieldLeak")
    private static Evenimente INSTANCE = null;

    private View view;
    private boolean openPL = false;
    private ConstraintLayout rootSelector;
    private ConstraintLayout layoutRoot;
    private ViewPager petreceriForm;

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

        assert getFragmentManager() != null;
        EnumFragmentsPetreceri enumFragmentsPetreceri = new EnumFragmentsPetreceri(getFragmentManager(),getContext());
        petreceriForm.setAdapter(enumFragmentsPetreceri);



        return view;
    }

    private void circularRevealCard(View view, int startX, int startY, int startRadius){
        float finalRadius = Math.max(view.getWidth(), view.getHeight());

        Animator circularReveal = ViewAnimationUtils.createCircularReveal(view, startX, startY, startRadius, finalRadius * 1.1f);

        circularReveal.setDuration(700);

        circularReveal.start();
    }

    private void circularCloseCard(final View view, int startX, int startY, int endRadius){
        float finalRadius = Math.max(view.getWidth(), view.getHeight());

        Animator circularReveal = ViewAnimationUtils.createCircularReveal(view, startX, startY, finalRadius * 1.1f, endRadius);

        circularReveal.setDuration(700);

        circularReveal.start();
    }

    private void openingTabs(){

        for (int i = 1; i < rootSelector.getChildCount(); i++) {
            final int finalI = i;
            rootSelector.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ClickableViewAccessibility")
                @Override
                public void onClick(View v) {
                    if(!openPL){
                        for (int j = 0; j < layoutRoot.getChildCount(); j++) {
                            layoutRoot.getChildAt(j).setVisibility(View.INVISIBLE);
                        }
                        layoutRoot.getChildAt(finalI - 1).setVisibility(View.VISIBLE);

                        int startX = (int) (rootSelector.getChildAt(finalI).getX() + rootSelector.getChildAt(finalI).getWidth()/2);
                        int startY = (int) (rootSelector.getChildAt(finalI).getY() + rootSelector.getChildAt(finalI).getHeight()/2);

                        circularRevealCard(layoutRoot.getChildAt(finalI - 1), startX, startY, rootSelector.getChildAt(finalI).getWidth()/2);

                        openPL = true;
                    }
                }
            });
        }
    }

    private void closingTabs(){

        for (int i = 0; i < layoutRoot.getChildCount(); i++) {
            final int finalI = i;
            final ConstraintLayout child = (ConstraintLayout) layoutRoot.getChildAt(i);

            child.getChildAt(1).setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ClickableViewAccessibility")
                @Override
                public void onClick(View v) {
                    if(openPL)
                    {
                        int startX = (int) (rootSelector.getChildAt(finalI + 1).getX() + rootSelector.getChildAt(finalI + 1).getWidth()/2);
                        int startY = (int) (rootSelector.getChildAt(finalI + 1).getY() + rootSelector.getChildAt(finalI + 1).getHeight()/2);

                        circularCloseCard(child,startX,startY,rootSelector.getChildAt(finalI + 1).getWidth()/2);
                        openPL = false;

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                child.setVisibility(View.INVISIBLE);
                            }
                        },700);
                    }
                }
            });
        }
    }

    private void init(){
        rootSelector = view.findViewById(R.id.rootSelector);
//        petrecere = view.findViewById(R.id.petrecere);
//        concert = view.findViewById(R.id.concert);
//        deschidere = view.findViewById(R.id.deschidere);
//        festival = view.findViewById(R.id.festival);
//        vernisaj = view.findViewById(R.id.vernisaj);
//        spectacol = view.findViewById(R.id.spectacol);

        layoutRoot = view.findViewById(R.id.layoutsRoot);
//        petrecere_layout = view.findViewById(R.id.petrecere_layout);
//        concert_layout = view.findViewById(R.id.concert_layout);
//        deschidere_layout = view.findViewById(R.id.deschidere_layout);
//        festival_layout = view.findViewById(R.id.festival_layout);
//        vernisaj_layout = view.findViewById(R.id.vernisaj_layout);
//        spectacol_layout = view.findViewById(R.id.spectacol_layout);

        petreceriForm = view.findViewById(R.id.petreceriForm);
    }
}
