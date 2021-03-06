package com.zone_bussiness.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.zone_bussiness.app.registerFirma.RegisterFirma;
import com.zone_bussiness.app.userScreen.MainScreen;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    private static ProgressDialog loading;
    private static EditText data, pass;
    private static String FILE_PATH;
    private static final String FILE_NAME = "data.txt";

    private View decorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if(visibility == 0)
                    decorView.setSystemUiVisibility(hideSystemBars());
            }
        });

        getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        TextView welcome = findViewById(R.id.welcome);
        final Animation anim = AnimationUtils.loadAnimation(this,R.anim.popin), popin = AnimationUtils.loadAnimation(this,R.anim.fade_in);
        anim.setDuration(1000);
        popin.setDuration(500);
        welcome.startAnimation(anim);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus) {
            decorView.setSystemUiVisibility(hideSystemBars());
        }
    }

    private int hideSystemBars() {
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }

    @Override
    public void onBackPressed() {

    }

    private void createDialog(){
        loading.setCancelable(false);
        loading.setTitle("Loging in");
        loading.setMessage("We are loging you in...");
        loading.show();
    }

    public void login(View view) {
        data = findViewById(R.id.cod);
        pass = findViewById(R.id.login_pass);
        loading = new ProgressDialog(MainActivity.this);
        createDialog();

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    String message = jsonObject.getString("msg");
                    if(success){

                        createFile();

                        int f = jsonObject.getInt("firstTime");
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
                        String menu = jsonObject.getString("menu");
                        String descriere = jsonObject.getString("descriere");
                        String tema = jsonObject.getString("tema");
                        String adresa = jsonObject.getString("adresa");
                        String salt = jsonObject.getString("salt");
                        double lat = jsonObject.getDouble("lat");
                        double lng = jsonObject.getDouble("lng");

                        if(f == 1){

                            Intent intent = new Intent(MainActivity.this, MainScreen.class);
                            intent.putExtra("userID", ID);
                            intent.putExtra("menu", menu);
                            intent.putExtra("pozaPath", pozaPath);
                            intent.putExtra("nume", nume);
                            intent.putExtra("COD", Cod);
                            intent.putExtra("numeFirma", numeFirma);
                            intent.putExtra("password", password);
                            intent.putExtra("muzica", muzica);
                            intent.putExtra("decor", decor);
                            intent.putExtra("descriere", descriere);
                            intent.putExtra("adresa", adresa);
                            intent.putExtra("lat", lat);
                            intent.putExtra("lng", lng);
                            intent.putExtra("mail", mail);
                            intent.putExtra("type", type);
                            intent.putExtra("dressCode", dressCode);
                            intent.putExtra("tema", tema);
                            intent.putExtra("logoutPath",FILE_PATH);

                            startActivity(intent);
                        }
                        else {
                            Intent intent = new Intent(MainActivity.this, RegisterFirma.class);
                            String l = data.getText().toString().trim();
                            l = l.substring(2, 3);
                            intent.putExtra("locatie", l.equals("L"));
                            intent.putExtra("salt", salt);
                            intent.putExtra("splashscreen", false);
                            startActivity(intent);
                        }

                        loading.dismiss();
                    } else{
                        loading.dismiss();
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        ServerRequest serverRequest = new ServerRequest("user", (data.getText().toString().trim()), "pass", (pass.getText().toString().trim()),"http://gladiaholdings.com/PHP/loginFirm.php",listener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(serverRequest);


    }

    private void createFile() {
        FileOutputStream fos = null;
        String text = ( getData() + "\n" + pass.getText().toString().trim());
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

    public static String getData() {
        return data.getText().toString().trim();
    }
}
