package com.zone_bussiness.app.userScreen.createEvents;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zone_bussiness.app.R;
import com.zone_bussiness.app.userScreen.createEvents.concerte.ConcertePage1;
import com.zone_bussiness.app.userScreen.createEvents.concerte.ConcertePage2;
import com.zone_bussiness.app.userScreen.createEvents.concerte.ConcertePage3;
import com.zone_bussiness.app.userScreen.createEvents.concerte.ConcertePage4;
import com.zone_bussiness.app.userScreen.createEvents.concerte.ConcertePage5;
import com.zone_bussiness.app.userScreen.createEvents.concerte.EnumFragmentsConcerte;
import com.zone_bussiness.app.userScreen.createEvents.vernisaje.EnumFragmentsVernisaj;
import com.zone_bussiness.app.userScreen.createEvents.vernisaje.VernisajPage1;
import com.zone_bussiness.app.userScreen.createEvents.vernisaje.VernisajPage2;
import com.zone_bussiness.app.userScreen.createEvents.vernisaje.VernisajPage3;
import com.zone_bussiness.app.userScreen.createEvents.vernisaje.VernisajPage4;
import com.zone_bussiness.app.userScreen.events.ListEvents;
import com.zone_bussiness.app.userScreen.MainScreen;
import com.zone_bussiness.app.userScreen.createEvents.petreceri.EnumFragmentsPetreceri;
import com.zone_bussiness.app.userScreen.createEvents.petreceri.PetreceriPage1;
import com.zone_bussiness.app.userScreen.createEvents.petreceri.PetreceriPage2;
import com.zone_bussiness.app.userScreen.createEvents.petreceri.PetreceriPage3;
import com.zone_bussiness.app.userScreen.createEvents.petreceri.PetreceriPage4;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Evenimente extends Fragment {

    private View view;
    private boolean openPL = false;
    private boolean[] hasAppeared;
    private ConstraintLayout rootSelector;
    private ConstraintLayout layoutRoot;
    private ViewPager petreceriForm, vernisajForm, concerteForm;
    private Button addP, addV, addC;
    private WormDotsIndicator dotsIndicatorPetreceri, dotsIndicatorVernisaj, dotsIndicatorConcerte;

    public Evenimente(){
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
        Arrays.fill(hasAppeared, false);

        openingTabs();
        closingTabs();
        serverListeners();

        final Animation popin = AnimationUtils.loadAnimation(getContext(),R.anim.fade_in);
        popin.setDuration(500);

        initDots();


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

        vernisajForm = view.findViewById(R.id.vernisajForm);
        dotsIndicatorVernisaj = view.findViewById(R.id.dotIndicatorV);
        addV = view.findViewById(R.id.addV);

        concerteForm = view.findViewById(R.id.concerteForm);
        dotsIndicatorConcerte = view.findViewById(R.id.dotsIndicatorConcerte);
        addC = view.findViewById(R.id.addC);
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

                String urlUpload = "http://gladiaholdings.com/PHP/firma/createEvent/addPetrecere.php";

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


                                ListEvents.refresh();

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
                                ConstraintLayout cc = (ConstraintLayout) c.getChildAt(3);
                                ImageButton btn = (ImageButton) cc.getChildAt(0);
                                btn.callOnClick();

                                progress.dismiss();
                            }
                            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            Toast.makeText(getContext(), "Error on receiving data from server" + response, Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                            Log.e("msg", response);
                            progress.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error: check your internet connection" + error, Toast.LENGTH_SHORT).show();
                        progress.dismiss();
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
                        params.put("IDorganizator",String.valueOf(MainScreen.getUserID()));


                        return params;
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(getContext());
                queue.add(stringRequest);
            }
        });

        addV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progress = new ProgressDialog(getContext());
                progress.setTitle("We are adding the vernisaj");
                progress.setCancelable(false);
                progress.setMessage("We are creating your vernisaj...");
                progress.show();

                String urlUpload = "http://gladiaholdings.com/PHP/firma/createEvent/addVernisaj.php";

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


                                ListEvents.refresh();

                                VernisajPage1.reset();
                                VernisajPage2.reset();
                                VernisajPage3.reset();

                                VernisajPage4.reset();
                                VernisajPage4.getBauturaButton().setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.colorPrimary));
                                VernisajPage4.getMancareButton().setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.colorPrimary));


                                ConstraintLayout c = (ConstraintLayout) layoutRoot.getChildAt(2);
                                ViewPager v = (ViewPager) c.getChildAt(0);

                                v.setCurrentItem(0);
                                ConstraintLayout cc = (ConstraintLayout) c.getChildAt(3);
                                ImageButton btn = (ImageButton) cc.getChildAt(0);
                                btn.callOnClick();

                                progress.dismiss();
                            }
                            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            Toast.makeText(getContext(), "Error on receiving data from server", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                            Log.e("response", response);
                            progress.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error: check your internet connection", Toast.LENGTH_SHORT).show();
                        Log.e("error", error.toString());
                        progress.dismiss();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("poza", VernisajPage1.getPoza());
                        params.put("numeEvent", VernisajPage1.getTitle());
                        params.put("data",VernisajPage1.getData());
                        params.put("oraStart",VernisajPage1.getOraStart());
                        params.put("oraEnd",VernisajPage1.getOraEnd());
                        params.put("longitudine", String.valueOf(VernisajPage1.getLongitudine()));
                        params.put("latitudine", String.valueOf(VernisajPage1.getLatitudine()));
                        params.put("adresa",VernisajPage1.getAdresa());
                        params.put("tematica", VernisajPage2.getTematica());
                        params.put("descriere", VernisajPage2.getDescriere());
                        params.put("mancare", String.valueOf(VernisajPage4.getMancare()));
                        params.put("pretMancare",VernisajPage4.getMancarePret());
                        params.put("bautura", String.valueOf(VernisajPage4.getBautura()));
                        params.put("pretBautura", VernisajPage4.getBauturaPret());
                        params.put("bilet",String.valueOf(VernisajPage4.getBilet()));
                        params.put("pretBilet", VernisajPage4.getBiletPret());
                        params.put("IDorganizator",String.valueOf(MainScreen.getUserID()));
                        params.put("detaliiArtisti", VernisajPage3.getArtistsDetalis());

                        String[] s = VernisajPage3.getArtistsPic();
                        for (int i = 0; i < s.length; i++) {
                            params.put("pic_" + i, s[i]);
                        }

                        params.put("no", String.valueOf(s.length));


                        return params;
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(getContext());
                queue.add(stringRequest);
            }
        });

        addC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progress = new ProgressDialog(getContext());
                progress.setTitle("We are adding the concert");
                progress.setCancelable(false);
                progress.setMessage("We are creating your concert...");
                progress.show();

                String urlUpload = "http://gladiaholdings.com/PHP/firma/createEvent/addConcert.php";

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


                                ListEvents.refresh();

                                ConcertePage1.reset();
                                ConcertePage3.reset();


                                ConcertePage4.reset();

                                for (int i = 0; i < ConcertePage4.getDoriane().getChildCount() ; i++) {
                                    final Button child = (Button) ConcertePage4.getDoriane().getChildAt(i);
                                    child.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.colorPrimary));
                                }

                                ConcertePage5.reset();
                                ConcertePage5.getBauturaButton().setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.colorPrimary));
                                ConcertePage5.getMancareButton().setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.colorPrimary));


                                ConstraintLayout c = (ConstraintLayout) layoutRoot.getChildAt(3);
                                ViewPager v = (ViewPager) c.getChildAt(0);

                                v.setCurrentItem(0);
                                ConstraintLayout cc = (ConstraintLayout) c.getChildAt(3);
                                ImageButton btn = (ImageButton) cc.getChildAt(0);
                                btn.callOnClick();
                            }
                            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                            progress.dismiss();
                            Log.e("res", response);
                        } catch (JSONException e) {
                            Toast.makeText(getContext(), "Error on receiving data from server", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                            Log.e("response", response);
                            progress.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error: check your internet connection", Toast.LENGTH_SHORT).show();
                        Log.e("error", error.toString());
                        progress.dismiss();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("poza", ConcertePage1.getPoza());
                        params.put("numeEvent", ConcertePage1.getTitle());
                        params.put("data",ConcertePage1.getData());
                        params.put("oraStart",ConcertePage1.getOraStart());
                        params.put("oraEnd",ConcertePage1.getOraEnd());
                        params.put("longitudine", String.valueOf(ConcertePage1.getLongitudine()));
                        params.put("latitudine", String.valueOf(ConcertePage1.getLatitudine()));
                        params.put("adresa",ConcertePage1.getAdresa());
                        params.put("descriere", ConcertePage5.getDescriere());
                        params.put("mancare", String.valueOf(ConcertePage5.getMancare()));
                        params.put("pretMancare",ConcertePage5.getMancarePret());
                        params.put("bautura", String.valueOf(ConcertePage5.getBautura()));
                        params.put("pretBautura", ConcertePage5.getBauturaPret());
                        params.put("bilet",String.valueOf(ConcertePage5.getBilet()));
                        params.put("pretBilet", ConcertePage5.getBiletPret());
                        params.put("IDorganizator",String.valueOf(MainScreen.getUserID()));
                        params.put("detaliiArtisti", ConcertePage3.getArtistsDetalis());
                        params.put("genuriMuzicale", ConcertePage4.getGenuri());
                        params.put("repertoriu", ConcertePage2.getRepertoriu());

                        String[] s = ConcertePage3.getArtistsPic();
                        for (int i = 0; i < s.length; i++) {
                            params.put("pic_" + i, s[i]);
                        }

                        params.put("no", String.valueOf(s.length));


                        return params;
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(getContext());
                queue.add(stringRequest);
            }
        });
    }

    public void initDots(){
        assert getFragmentManager() != null;
        EnumFragmentsPetreceri enumFragmentsPetreceri = new EnumFragmentsPetreceri(getFragmentManager(),getContext());
        petreceriForm.setAdapter(enumFragmentsPetreceri);
        dotsIndicatorPetreceri.setViewPager(petreceriForm);

        EnumFragmentsVernisaj enumFragmentsVernisaj = new EnumFragmentsVernisaj(getFragmentManager(), getContext());
        vernisajForm.setAdapter(enumFragmentsVernisaj);
        dotsIndicatorVernisaj.setViewPager(vernisajForm);

        EnumFragmentsConcerte enumFragmentsConcerte = new EnumFragmentsConcerte(getFragmentManager(), getContext());
        concerteForm.setAdapter(enumFragmentsConcerte);
        dotsIndicatorConcerte.setViewPager(concerteForm);
    }
}
