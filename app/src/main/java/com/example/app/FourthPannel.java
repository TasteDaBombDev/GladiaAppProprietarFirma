package com.example.app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.github.florent37.materialtextfield.MaterialTextField;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import static java.lang.Character.isDigit;

public class FourthPannel extends AppCompatActivity {

    private EditText cod;
    private String trueCode;
    private String method;
    private OkHttpClient client;
    private Animation make_error;
    private MaterialTextField code_outline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth_pannel);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        make_error = AnimationUtils.loadAnimation(this,R.anim.shake);
        code_outline = findViewById(R.id.code_outline);

        cod = findViewById(R.id.cod);
        trueCode = generateCode();
        sendCode();
        client = new OkHttpClient();

        code_outline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!code_outline.hasFocus()) code_outline.setHasFocus(true);
                else code_outline.setHasFocus(false);
            }
        });
    }

    private String generateCode(){
        Random r = new Random( System.currentTimeMillis() );
        int a = 10000 + r.nextInt(20000);
        return String.valueOf(a);
    }

    private void sendCode(){
        method = SecondPannelPrime.getLogin();
        String regex = "[0-9]+";
        if(method.matches(regex)){
            //is a phone number
            Toast.makeText(getApplicationContext(),"you introduced a phone number " + trueCode,Toast.LENGTH_LONG).show();
        }
        else{
            //is a mail

//            Response.Listener<String> listener = new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    try {
//                        JSONObject jsonObject = new JSONObject(response);
//                        boolean success = jsonObject.getBoolean("success");
//
//                        if(success){
//                            Toast.makeText(getApplicationContext(),"yay",Toast.LENGTH_SHORT).show();
//                        } else{
//                            Toast.makeText(getApplicationContext(), "no", Toast.LENGTH_SHORT).show();
//                        }
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            };
//
//
//            ServerRequest serverRequest = new ServerRequest(SecondPannelPrime.getLogin(),SecondPannelPrime.getUsername(),trueCode,"http://gladiaholdings.com/PHP/login.php",listener);
//            RequestQueue queue = Volley.newRequestQueue(FourthPannel.this);
//            queue.add(serverRequest);

            Toast.makeText(getApplicationContext(),"you introduced a mail" + trueCode,Toast.LENGTH_LONG).show();
        }
    }

    private float x1,x2,y1,y2;

    public boolean onTouchEvent(MotionEvent touchEvent){
        switch (touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:{
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
            } break;
            case MotionEvent.ACTION_UP:{
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if (x1 > x2)
                    registerUser();

            } break;
        }
        return false;
    }

    private boolean check(){
        String code = cod.getText().toString();
        return code.equals(trueCode);
    }

    public void registerUser() {
        if(check()){
//            Intent intent = new Intent(FourthPannel.this, Register.class);
//            startActivity(intent);
//            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
        else
        {
            code_outline.startAnimation(make_error);
            cod.setError("Nu ai introdus codul corect!");
        }
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
