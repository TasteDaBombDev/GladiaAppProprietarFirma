package com.example.app.userScreen.events;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app.R;
import com.example.app.userScreen.ListEvents;
import com.example.app.userScreen.MainScreen;
import com.example.app.userScreen.events.petreceri.EnumFragmentsPetreceri;
import com.example.app.userScreen.events.petreceri.PetreceriPage1;
import com.example.app.userScreen.events.petreceri.PetreceriPage2;
import com.example.app.userScreen.events.petreceri.PetreceriPage3;
import com.example.app.userScreen.events.petreceri.PetreceriPage4;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Evenimente extends Fragment {

    @SuppressLint("StaticFieldLeak")
    private static Evenimente INSTANCE = null;

    private View view;
    private boolean openPL = false;
    private boolean[] hasAppeared;
    private ConstraintLayout rootSelector;
    private ConstraintLayout layoutRoot;
    private ViewPager petreceriForm;
    private Button addP;
    private WormDotsIndicator dotsIndicatorPetreceri;

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
        hasAppeared  = new boolean[layoutRoot.getChildCount()];
        for (int i = 0; i < hasAppeared.length; i++) {
            hasAppeared[i] = false;
        }

        openingTabs();
        closingTabs();
        serverListeners();

        final Animation popin = AnimationUtils.loadAnimation(getContext(),R.anim.fade_in);
        popin.setDuration(500);

        assert getFragmentManager() != null;
        EnumFragmentsPetreceri enumFragmentsPetreceri = new EnumFragmentsPetreceri(getFragmentManager(),getContext());
        petreceriForm.setAdapter(enumFragmentsPetreceri);
        dotsIndicatorPetreceri.setViewPager(petreceriForm);

        for (int i = 0; i < layoutRoot.getChildCount(); i++) {
            final ConstraintLayout child = (ConstraintLayout) layoutRoot.getChildAt(i);
            final ViewPager childViewPager = (ViewPager) child.getChildAt(0);
            final ConstraintLayout childOfHeader = (ConstraintLayout) child.getChildAt(3);


//            WormDotsIndicator dotsIndicator = (WormDotsIndicator) child.getChildAt(2);
//            dotsIndicator.setViewPager(childViewPager);


            childViewPager.setOffscreenPageLimit(4);
            final int finalI = i;
            childViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    if(position != 0)
                        childOfHeader.getChildAt(0).setVisibility(View.INVISIBLE);
                    else
                        childOfHeader.getChildAt(0).setVisibility(View.VISIBLE);
                }

                @Override
                public void onPageSelected(int position) {
                    if(position == (MainScreen.getViewPagerCountPage(finalI) - 1))
                    {
                        child.getChildAt(2).setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        child.getChildAt(2).setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }


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
            final ConstraintLayout childOfHeader = (ConstraintLayout) child.getChildAt(3);

            childOfHeader.getChildAt(0).setOnClickListener(new View.OnClickListener() {
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
        layoutRoot = view.findViewById(R.id.layoutsRoot);
        petreceriForm = view.findViewById(R.id.petreceriForm);
        dotsIndicatorPetreceri = view.findViewById(R.id.dotIndicatorP);
        addP = view.findViewById(R.id.addP);
    }

    private void serverListeners(){
        addP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progress = new ProgressDialog(getContext());
                progress.setTitle("We are adding the party");
                progress.setCancelable(false);
                progress.setMessage("We are creating your party...");
                progress.show();

                String urlUpload = "http://gladiaholdings.com/PHP/addPetrecere.php";

                StringRequest stringRequest =  new StringRequest(Request.Method.POST, urlUpload, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            String msg = jsonObject.getString("message");
                            if(success){
                                String path = jsonObject.getString("path");
                                Integer id = jsonObject.getInt("id");


                                ListEvents.addNames(PetreceriPage1.getTitle());
                                ListEvents.addPaths(path);
                                ListEvents.addIds(id);
                                ListEvents.change();


                                PetreceriPage1.reset();

                                PetreceriPage2.reset();

                                PetreceriPage3.reset();
                                for (int i = 0; i < PetreceriPage3.getDoriane().getChildCount() ; i++) {
                                    final Button child = (Button) PetreceriPage3.getDoriane().getChildAt(i);
                                    child.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.colorPrimary));
                                }

                                PetreceriPage4.reset();
                                PetreceriPage4.getBauturaButton().setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.colorPrimary));
                                PetreceriPage4.getMancareButton().setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.colorPrimary));


                                ConstraintLayout c = (ConstraintLayout) layoutRoot.getChildAt(0);
                                ViewPager v = (ViewPager) c.getChildAt(0);

                                v.setCurrentItem(0);
                                ImageButton btn = (ImageButton) c.getChildAt(1);
                                btn.callOnClick();

                                progress.dismiss();
                            }
                            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            Toast.makeText(getContext(), "Error on receiving data from server" + response, Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error: check your internet connection" + error, Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("poza", PetreceriPage1.getPoza());
                        params.put("numeEvent", PetreceriPage1.getTitle());
                        params.put("data",PetreceriPage1.getData());
                        params.put("oraStart",PetreceriPage1.getOraStart());
                        params.put("oraEnd",PetreceriPage1.getOraEnd());
                        params.put("longitudine", String.valueOf(PetreceriPage1.getLongitudine()));
                        params.put("latitudine", String.valueOf(PetreceriPage1.getLatitudine()));
                        params.put("adresa",PetreceriPage1.getAdresa());
                        params.put("tematica", PetreceriPage2.getTematica());
                        params.put("pozaArtist",PetreceriPage2.getVedetaPic());
                        params.put("numeArtist",PetreceriPage2.getVedetaName());
                        params.put("genuriMuzicale", PetreceriPage3.getGenuri());
                        params.put("descriere", PetreceriPage2.getDescriere());
                        params.put("tinuta",PetreceriPage2.getTinuta());
                        params.put("mancare", String.valueOf(PetreceriPage4.getMancare()));
                        params.put("pretMancare",PetreceriPage4.getMancarePret());
                        params.put("bautura", String.valueOf(PetreceriPage4.getBautura()));
                        params.put("pretBautura",PetreceriPage4.getBauturaPret());
                        params.put("bilet",String.valueOf(PetreceriPage4.getBilet()));
                        params.put("pretBilet",PetreceriPage4.getBiletPret());
                        params.put("IDorganizator","23");

                        return params;
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(getContext());
                queue.add(stringRequest);
            }
        });
    }
}
