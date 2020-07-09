package com.example.app.registerFirma;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

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
import com.example.app.userScreen.MainScreen;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterFirma extends AppCompatActivity {

    private ArrayList<String> texts = new ArrayList<>();
    private ConstraintLayout header;
    private Button save;
    private boolean ok = true;
    private boolean errMode = false;
    private Animation fadeIn = new AlphaAnimation(0.0f, 1.0f), fadeOut = new AlphaAnimation(1.0f, 0.0f), shake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_firma_main);
        fadeIn.setDuration(300);
        fadeIn.setFillAfter(true);
        fadeOut.setDuration(300);
        fadeOut.setFillAfter(true);
        shake = AnimationUtils.loadAnimation(RegisterFirma.this, R.anim.shake);


        save = findViewById(R.id.save);
        save.setBackgroundResource(R.drawable.circle);

        header = findViewById(R.id.header);
        TransitionManager.beginDelayedTransition(header);


        final TextView text = findViewById(R.id.textPrompt);
        texts.add("Let's set up your profile picture, name and email");
        texts.add("Now, let's set up your bussiness location");
        texts.add("Details about your firm");
        texts.add("Your menu");


        final ViewPager vp = findViewById(R.id.registerVP);
        Bundle extras = getIntent().getExtras();
        final EnumFragmentsRegister enumFragmentsRegister = new EnumFragmentsRegister(getSupportFragmentManager(), this, extras.getBoolean("locatie"));
        vp.setAdapter(enumFragmentsRegister);
        vp.setOffscreenPageLimit(4);

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position)
            {
                if(errMode){
                    text.startAnimation(fadeOut);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            text.setText(texts.get(position));
                        }
                    },300);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            text.startAnimation(fadeIn);
                        }
                    },600);
                }
                if(position == (enumFragmentsRegister.getCount() - 1) && ok) {
                    save.setVisibility(View.VISIBLE);
                    save.startAnimation(fadeIn);
                    ok = false;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if(Register1.areCompleted()){
                    if(Register2.areCompleted()){
                        String url = "http://gladiaholdings.com/PHP/updateAfacere.php";
                        StringRequest stringRequest =  new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    boolean success = jsonObject.getBoolean("success");
                                    String msg = jsonObject.getString("msg");
                                    if(success){

                                        int ID = jsonObject.getInt("userID");
                                        String Cod = jsonObject.getString("COD");
                                        String pozaPath = jsonObject.getString("poza");
                                        String nume = jsonObject.getString("nume");
                                        String numeFirma = jsonObject.getString("numeFirma");
                                        String mail = jsonObject.getString("mail");
                                        String password = jsonObject.getString("password");
                                        String type = jsonObject.getString("type");
                                        String dressCode = jsonObject.getString("dressCode");
                                        String decor = jsonObject.getString("decor");
                                        String muzica = jsonObject.getString("muzica");
                                        String tema = jsonObject.getString("tema");
                                        String adresa = jsonObject.getString("adresa");
                                        double lat = jsonObject.getDouble("lat");
                                        double lng = jsonObject.getDouble("lng");


                                        Intent intent = new Intent(RegisterFirma.this, MainScreen.class);
                                        intent.putExtra("userID", ID);
                                        intent.putExtra("pozaPath", pozaPath);
                                        intent.putExtra("nume", nume);
                                        intent.putExtra("COD", Cod);
                                        intent.putExtra("numeFirma", numeFirma);
                                        intent.putExtra("password", password);
                                        intent.putExtra("muzica", muzica);
                                        intent.putExtra("decor", decor);
                                        intent.putExtra("adresa", adresa);
                                        intent.putExtra("lat", lat);
                                        intent.putExtra("lng", lng);
                                        intent.putExtra("mail", mail);
                                        intent.putExtra("type", type);
                                        intent.putExtra("dressCode", dressCode);
                                        intent.putExtra("tema", tema);
                                        intent.putExtra("logoutPath",SplashScreen.getPath());

                                        startActivity(intent);
                                    }
                                    else{
                                        Intent intent = new Intent(RegisterFirma.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    Toast.makeText(RegisterFirma.this, msg, Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    Toast.makeText(RegisterFirma.this, "Error on receiving data from server" + response, Toast.LENGTH_LONG).show();
                                    Log.w("RESPONSE: ", response);
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(RegisterFirma.this, "Error: check your internet connection" + error, Toast.LENGTH_SHORT).show();
                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                String ID = SplashScreen.getCod();
                                params.put("ID", ID.substring(3));
                                params.put("name", Register1.getName());
                                params.put("adresa", Register2.getAddress());
                                params.put("lat", String.valueOf(Register2.getLat()));
                                params.put("lng", String.valueOf(Register2.getLng()));
                                if(enumFragmentsRegister.getCount() == 4){
                                    params.put("tema", Register3.getTema());
                                    params.put("dressCode", Register3.getDressCode());
                                    params.put("decor", Register3.getDecor());
                                    params.put("muzica", Register3.getMuzica());
                                    params.put("menu", Register4.getItems());
                                    params.put("email", Register1.getEmail());
                                } else {
                                    params.put("tema", "");
                                    params.put("dressCode", "");
                                    params.put("decor", "");
                                    params.put("muzica", "");
                                    params.put("menu", "");
                                    params.put("email", "");
                                }

                                return params;
                            }
                        };
                        RequestQueue queue = Volley.newRequestQueue(RegisterFirma.this);
                        queue.add(stringRequest);
                    }
                    else {
                        errMode = true;
                        save.startAnimation(shake);
                        save.setBackgroundTintList(ContextCompat.getColorStateList(RegisterFirma.this, R.color.error));
                        text.setText("Oh, no! You missed to set up a location. Press the cheked button to set it up");
                        vp.setCurrentItem(1);
                    }
                } else {
                    errMode = true;
                    save.startAnimation(shake);
                    save.setBackgroundTintList(ContextCompat.getColorStateList(RegisterFirma.this, R.color.error));
                    text.setText("Oh, no! You missed to write your name, email or profile pic.");
                    vp.setCurrentItem(0);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
