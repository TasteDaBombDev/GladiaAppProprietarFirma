package com.example.app.userScreen.profile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.transition.TransitionManager;
import android.util.JsonToken;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app.MainActivity;
import com.example.app.R;
import com.example.app.SplashScreen;
import com.example.app.registerFirma.Register1;
import com.example.app.registerFirma.Register2;
import com.example.app.registerFirma.Register3;
import com.example.app.registerFirma.Register4;
import com.example.app.registerFirma.RegisterFirma;
import com.example.app.registerFirma.RegsiterDescriereOrganizatie;
import com.example.app.userScreen.createEvents.petreceri.EnumFragmentsPetreceri;
import com.example.app.userScreen.createEvents.vernisaje.EnumFragmentsVernisaj;
import com.example.app.userScreen.events.ListEvents;
import com.example.app.userScreen.MainScreen;
import com.example.app.userScreen.createEvents.petreceri.PetreceriPage1;
import com.example.app.userScreen.createEvents.petreceri.PetreceriPage2;
import com.example.app.userScreen.createEvents.petreceri.PetreceriPage3;
import com.example.app.userScreen.createEvents.petreceri.PetreceriPage4;
import com.example.app.userScreen.createEvents.petreceri.PetreceriPage5;
import com.example.app.userScreen.profile.dashboard.Dashboard;
import com.google.android.material.appbar.AppBarLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProfileLocation extends Fragment {

    private LinearLayout root;
    private ImageButton logout, toDashboard, toProcent, editBtn;
    private View item;
    private boolean editMode = false;
    private ImageView profilePic;
    private TextView descriere, name, adress, email, tema, decor, muzica, dresscode;
    private ConstraintLayout rootOfNext;
    private Integer[] CONSTRAINT_VIEWS = {R.id.cl1, R.id.cl2, R.id.cl3, R.id.cl4, R.id.cl5, R.id.cl6, R.id.cl7};
    private Integer[] CARD_VIEWS = {R.id.cardView2, R.id.cardView3, R.id.cardView4, R.id.cardView5, R.id.cardView6, R.id.cardView8, R.id.cardView9};
    private String[] VIEWS = {
            "Location - " + MainScreen.getAdresa(),
            "Tema - " + MainScreen.getTema(),
            "Decor - " + MainScreen.getDecor(),
            "Muzica - " + MainScreen.getMuzica(),
            "Dresscode - " + MainScreen.getDressCode(),
            "Menu - " + MainScreen.getMenu(),
            "Email - " + MainScreen.getMail()
    };
    private static ArrayList<String> firmaDetails = new ArrayList<>();

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
        item = inflater.inflate(R.layout.location_profile,container,false);
        init();
        fetchData();
        generateMenu();
        setUpleftRightEvents();
        scrollEvent();
        renderFirmDetails();
        toggleEvent();
        logoutSystem();

        return item;
    }

    private void init(){
        profilePic = item.findViewById(R.id.profilePic);
        Picasso.get().load(MainScreen.getPozaPath()).into(profilePic);


        root = item.findViewById(R.id.root);
        editBtn = item.findViewById(R.id.editBtn);
        rootOfNext = item.findViewById(R.id.rootOfNext);
        name = item.findViewById(R.id.name);
        adress = item.findViewById(R.id.adress);
        email = item.findViewById(R.id.mail);
        tema = item.findViewById(R.id.tema);
        muzica = item.findViewById(R.id.muzica);
        dresscode = item.findViewById(R.id.dresscode);
        decor = item.findViewById(R.id.decor);
        descriere = item.findViewById(R.id.descriereFirma);

        toDashboard = item.findViewById(R.id.toDashboard);
        toProcent = item.findViewById(R.id.toProcent);
    }

    private void renderFirmDetails(){
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
    }

    private void scrollEvent(){
        AppBarLayout appBarLayout = item.findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                rootOfNext.setMaxHeight(appBarLayout.getHeight() - Math.abs(verticalOffset));
                if(verticalOffset < -950)
                    editBtn.setVisibility(View.VISIBLE);
                else
                    editBtn.setVisibility(View.GONE);
//                ConstraintSet cs = new ConstraintSet();
//                cs.clone(root);
//                float l = (float) Math.abs(verticalOffset)/1032;
//                float csL = l/4;
//                if(csL > 0.11)
//                {
//                    cs.constrainPercentWidth(R.id.toDashboard, csL);
//                    cs.constrainPercentWidth(R.id.toProcent, csL);
//                } else {
//                    cs.constrainPercentWidth(R.id.toProcent, 0.11f);
//                    cs.constrainPercentWidth(R.id.toDashboard, 0.11f);
//                }
//                cs.applyTo(root);
            }
        });
    }

    private void toggleEvent(){
        final LinearLayout list = item.findViewById(R.id.listDetails);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransitionManager.beginDelayedTransition(rootOfNext);
                TransitionManager.beginDelayedTransition(list);
                if(!editMode){
                    ProfileMainFragment.setScrollable(false);
                    editMode = true;
                    toDashboard.setVisibility(View.GONE);
                    toProcent.setVisibility(View.GONE);
                    editBtn.setImageResource(R.drawable.ic_close_black_24dp);
                    for (int i = 0; i < list.getChildCount(); i++) {
                        ConstraintLayout c = item.findViewById(CONSTRAINT_VIEWS[i]);
                        ConstraintSet set = new ConstraintSet();
                        set.clone(c);
                        set.setHorizontalBias(CARD_VIEWS[i], 0.1f);
                        set.applyTo(c);
                        c.getChildAt(3).setVisibility(View.VISIBLE);
                        CheckBox cb = (CheckBox) c.getChildAt(3);
                        final int finalI = i;
                        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if(b)
                                    firmaDetails.add(VIEWS[finalI]);
                                else
                                    firmaDetails.remove(VIEWS[finalI]);
                            }
                        });
                    }
                } else {
                    Dashboard.constructProfile(getContext());
                    updateFirmData();
                    ProfileMainFragment.setScrollable(true);
                    editMode = false;
                    toDashboard.setVisibility(View.VISIBLE);
                    toProcent.setVisibility(View.VISIBLE);
                    editBtn.setImageResource(R.drawable.ic_edit_black_24dp);
                    for (int i = 0; i < list.getChildCount(); i++) {
                        ConstraintLayout c = item.findViewById(CONSTRAINT_VIEWS[i]);
                        ConstraintSet set = new ConstraintSet();
                        set.clone(c);
                        set.setHorizontalBias(CARD_VIEWS[i], 0.5f);
                        set.applyTo(c);
                        c.getChildAt(3).setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    private void logoutSystem(){

        logout = item.findViewById(R.id.logout);
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
                        EnumFragmentsVernisaj.destroy();
                        EnumFragmentsPetreceri.destroy();
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(getContext(),"Failed to logout",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void generateMenu(){
        String[] menus = MainScreen.getMenu().split(", ");
        for (int i = 0; i < menus.length; i++) {
            TextView t = new TextView(getContext());
            menus[i].replace("#", " - ");
            t.setText(menus[i]);
            root.addView(t);
        }
    }

    private void setUpleftRightEvents(){
        toDashboard = item.findViewById(R.id.toDashboard);
        toProcent = item.findViewById(R.id.toProcent);

        toDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileMainFragment.getViewPager().setCurrentItem(2);
            }
        });

        toProcent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileMainFragment.getViewPager().setCurrentItem(0);
            }
        });
    }

    public static ArrayList<String> getFirmaDetails() {
        return firmaDetails;
    }

    private void updateFirmData(){
        String url = "http://gladiaholdings.com/PHP/sendDetails.php";
        StringRequest stringRequest =  new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.w("g", response);
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
                params.put("ID", String.valueOf(MainScreen.getUserID()));
                StringBuilder s = new StringBuilder();
                final LinearLayout list = item.findViewById(R.id.listDetails);
                for (int i = 0; i < list.getChildCount(); i++) {
                    ConstraintLayout c = item.findViewById(CONSTRAINT_VIEWS[i]);
                    CheckBox cb = (CheckBox) c.getChildAt(3);
                    if(cb.isChecked())
                        s.append("1");
                    else s.append("0");
                }
                params.put("permisiuni", s.toString());
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(stringRequest);
    }

    private void fetchData(){
        String url = "http://gladiaholdings.com/PHP/fetchDetails.php";
        StringRequest stringRequest =  new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String permisiuni = jsonObject.getString("permisiuni");
                    for (int i = 0; i < permisiuni.length(); i++) {

                        Log.w("permisiuni", String.valueOf(permisiuni.charAt(i)));
                        if(permisiuni.charAt(i) == '1')
                        {
                            firmaDetails.add(VIEWS[i]);
                        }
                    }
                    Dashboard.constructProfile(getContext());
                    Log.w("a", firmaDetails.toString());
                    final LinearLayout list = item.findViewById(R.id.listDetails);
                    for (int i = 0; i < permisiuni.length(); i++) {
                        ConstraintLayout c = item.findViewById(CONSTRAINT_VIEWS[i]);
                        CheckBox cb = (CheckBox) c.getChildAt(3);
                        if(permisiuni.charAt(i) == '1')
                            cb.setChecked(true);
                    }
                } catch (JSONException e) {
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
                params.put("ID", String.valueOf(MainScreen.getUserID()));
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(stringRequest);
    }
}
