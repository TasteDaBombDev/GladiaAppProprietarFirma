package com.zone_bussiness.app;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.zone_bussiness.app.registerFirma.RegisterFirma;
import com.zone_bussiness.app.userScreen.MainScreen;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class SplashScreen extends AppCompatActivity {

    private ImageView logo;
    private TextView name;
    private static int SPLASH_TIME = 3000;
    private static final String FILE_NAME = "data.txt";
    private static String path = "";
    private static String Cod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        logo = findViewById(R.id.logo);
        name = findViewById(R.id.appName);

        FileInputStream fis = null;

        /**
         * UNCOMMENT IN CASE OF REAL EMERGENCY
         */
//        File fdelete = new File(getFilesDir() + "/" + FILE_NAME);
//        if (fdelete.exists()) {
//            boolean t = fdelete.delete();
//            if(t)
//                Toast.makeText(getApplicationContext(),"true" + getFilesDir() + "/" + FILE_NAME,Toast.LENGTH_LONG).show();
//            else
//                Toast.makeText(getApplicationContext(),"false" + getFilesDir() + "/" + FILE_NAME,Toast.LENGTH_LONG).show();
//        }
        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            final String login = br.readLine(), pass = br.readLine();
            path = br.readLine();

            Response.Listener<String> listener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");

                        if(success){
                                int f = jsonObject.getInt("firstTime");
                                int ID = jsonObject.getInt("userID");
                                Cod = jsonObject.getString("COD");
                                String pozaPath = jsonObject.getString("poza");
                                String nume = jsonObject.getString("nume");
                                String numeFirma = jsonObject.getString("numeFirma");
                                String mail = jsonObject.getString("mail");
                                String password = jsonObject.getString("password");
                                String type = jsonObject.getString("type");
                                String dressCode = jsonObject.getString("dressCode");
                                String decor = jsonObject.getString("decor");
                                String descriere = jsonObject.getString("descriere");
                                String muzica = jsonObject.getString("muzica");
                                String tema = jsonObject.getString("tema");
                                String adresa = jsonObject.getString("adresa");
                                String menu = jsonObject.getString("menu");
                                String salt = jsonObject.getString("salt");
                                double lat = jsonObject.getDouble("lat");
                                double lng = jsonObject.getDouble("lng");

                                if(f == 1){
                                    Intent intent = new Intent(SplashScreen.this, MainScreen.class);
                                    intent.putExtra("userID", ID);
                                    intent.putExtra("menu", menu);
                                    intent.putExtra("pozaPath", pozaPath);
                                    intent.putExtra("nume", nume);
                                    intent.putExtra("COD", Cod);
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
                                    intent.putExtra("logoutPath",path);

                                    startActivity(intent);
                                } else {
                                    Intent intent = new Intent(SplashScreen.this, RegisterFirma.class);
                                    String l = Cod;
                                    l = l.substring(2, 3);
                                    intent.putExtra("locatie", l.equals("L"));
                                    intent.putExtra("salt", salt);
                                    intent.putExtra("splashscreen", true);
                                    startActivity(intent);
                                }
                        } else{
                            Pair[] pairs = new Pair[1];
                            pairs[0] = new Pair<View, String>(logo,"imgTransition");

                            ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(SplashScreen.this,pairs);

                            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                            startActivity(intent, activityOptions.toBundle());
                        }

                    } catch (JSONException e) {
                        Log.w("response", response);
                        e.printStackTrace();
                    }
                }
            };

            ServerRequest serverRequest = new ServerRequest("user", login, "pass", pass,"http://gladiaholdings.com/PHP/loginFirm.php",listener);
            RequestQueue queue = Volley.newRequestQueue(SplashScreen.this);
            queue.add(serverRequest);

        } catch (FileNotFoundException e) {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {

                    Pair[] pairs = new Pair[1];
                    pairs[0] = new Pair<View, String>(logo,"imgTransition");

                    ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(SplashScreen.this,pairs);


                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent, activityOptions.toBundle());


                }
            },SPLASH_TIME);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getPath() {
        return path;
    }

    public static String getCod() {
        return Cod;
    }
}
