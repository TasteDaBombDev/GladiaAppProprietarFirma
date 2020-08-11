package com.zone_bussiness.app.registerFirma;

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
import com.zone_bussiness.app.MainActivity;
import com.zone_bussiness.app.R;
import com.zone_bussiness.app.SplashScreen;
import com.zone_bussiness.app.userScreen.MainScreen;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterFirma extends AppCompatActivity {

    private ArrayList<String> texts = new ArrayList<>();
    private ConstraintLayout header;
    private Button save;
    private boolean ok = true;
    private boolean errMode = false;
    private String salt;
    private Bundle extras;
    private static String FILE_PATH;
    private static final String FILE_NAME = "data.txt";
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

        extras = getIntent().getExtras();
        salt = extras.getString("salt");

        save = findViewById(R.id.save);
        save.setBackgroundResource(R.drawable.circle);

        header = findViewById(R.id.header);
        TransitionManager.beginDelayedTransition(header);

        String ID = "";
        if(extras.getBoolean("splashscreen"))
            ID = SplashScreen.getCod();
        else
            ID = MainActivity.getData();


        final TextView text = findViewById(R.id.textPrompt);
        texts.add("Let's set up your cover photo, name and email");
        texts.add("Now, let's set up your bussiness location");
        if(ID.substring(2,3).equals("L")){
            texts.add("Details about your firm");
            texts.add("Your flavours menu");
        }
        else
            texts.add("Your organisation's description");


        final ViewPager vp = findViewById(R.id.registerVP);
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
                if(!errMode){
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

        final String finalID = ID;
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

                                        createFile();

                                        int ID = jsonObject.getInt("userID");
                                        String Cod = jsonObject.getString("COD");
                                        String pozaPath = jsonObject.getString("poza");
                                        String nume = jsonObject.getString("nume");
                                        String numeFirma = jsonObject.getString("numeFirma");
                                        String mail = jsonObject.getString("mail");
                                        String password = jsonObject.getString("password");
                                        String type = jsonObject.getString("type");
                                        String menu = jsonObject.getString("menu");
                                        String descriere = jsonObject.getString("descriere");
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
                                        intent.putExtra("menu", menu);
                                        intent.putExtra("descriere", descriere);
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
                                        Register4.clear();
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
                                params.put("ID", finalID.substring(3));
                                params.put("poza", Register1.getPoza());
                                params.put("name", Register1.getName());
                                params.put("pass", (Register1.getPass() + "--" + salt));
                                params.put("adresa", Register2.getAddress());
                                params.put("lat", String.valueOf(Register2.getLat()));
                                params.put("lng", String.valueOf(Register2.getLng()));
                                params.put("email", Register1.getEmail());
                                if(enumFragmentsRegister.getCount() == 4){
                                    params.put("tema", Register3.getTema());
                                    params.put("dressCode", Register3.getDressCode());
                                    params.put("decor", Register3.getDecor());
                                    params.put("muzica", Register3.getMuzica());
                                    params.put("menu", Register4.getItems());
                                    params.put("descriere", Register3.getDescriere());
                                } else {
                                    params.put("tema", "");
                                    params.put("dressCode", "");
                                    params.put("decor", "");
                                    params.put("muzica", "");
                                    params.put("menu", "");
                                    params.put("descriere",RegsiterDescriereOrganizatie.getDescriere());
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

    private void createFile() {
        FileOutputStream fos = null;
        String ID = "";
        if(extras.getBoolean("splashscreen"))
            ID = SplashScreen.getCod();
        else
            ID = MainActivity.getData();
        String text = ( ID + "\n" + Register1.getPass());
        try {
            fos = openFileOutput(FILE_NAME,MODE_PRIVATE);
            FILE_PATH = getFilesDir() + "/" + FILE_NAME;
            text = text + "\n" + FILE_PATH;
            fos.write(text.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {

    }
}
