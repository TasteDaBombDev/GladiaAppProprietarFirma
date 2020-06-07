package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.app.termsAndConditions.TermsAndConditionsMain;
import com.example.app.userScreen.MainScreen;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    private static ProgressDialog loading;
    private static final String FILE_NAME = "data.txt";
    private EditText data, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView welcome = findViewById(R.id.welcome);
        final Animation anim = AnimationUtils.loadAnimation(this,R.anim.popin), popin = AnimationUtils.loadAnimation(this,R.anim.fade_in);
        anim.setDuration(1000);
        popin.setDuration(500);
        welcome.startAnimation(anim);

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


                        int ID = jsonObject.getInt("userID");
                        String username = jsonObject.getString("username");
                        String nume = jsonObject.getString("nume");
                        String prenume = jsonObject.getString("prenume");
                        String password = jsonObject.getString("password");
                        String mail = jsonObject.getString("mail");
                        String ziuaDeNastere = jsonObject.getString("ziuaDeNastere");
                        String sex = jsonObject.getString("sex");
                        String nrtel = jsonObject.getString("nrtel");


                        Intent intent = new Intent(MainActivity.this, MainScreen.class);
                        intent.putExtra("userID", ID);
                        intent.putExtra("username",username);
                        intent.putExtra("nume", nume);
                        intent.putExtra("prenume", prenume);
                        intent.putExtra("password", password);
                        intent.putExtra("mail", mail);
                        intent.putExtra("ziuaDeNastere", ziuaDeNastere);
                        intent.putExtra("sex", sex);
                        intent.putExtra("nrtel", nrtel);
                        intent.putExtra("nrAfaceri",jsonObject.getInt("nrAfaceri"));
                        intent.putExtra("nrEvents",jsonObject.getInt("nrEvents"));
                        intent.putExtra("friends", jsonObject.getInt("friends"));
                        intent.putExtra("pannel", 2);
                        intent.putExtra("fromRegister",false);

                        startActivity(intent);
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

        ServerRequest serverRequest = new ServerRequest("user", (data.getText().toString().trim()), "pass", (pass.getText().toString().trim()),"http://gladiaholdings.com/PHP/login.php",listener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(serverRequest);


    }

    private void createFile() {
        FileOutputStream fos = null;
        String text = (data.getText().toString().trim()) + "\n" + (pass.getText().toString().trim());
        try {
            fos = openFileOutput(FILE_NAME,MODE_PRIVATE);
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



}
