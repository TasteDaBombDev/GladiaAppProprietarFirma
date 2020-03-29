package com.example.app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.github.florent37.materialtextfield.MaterialTextField;

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
import okhttp3.Response;

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
            String mail = SecondPannelPrime.getLogin();
            String username = SecondPannelPrime.getUsername();

            String method = "sendMail";
            BackgroundWork backgroundWork = new BackgroundWork(this);
            Toast.makeText(getApplicationContext(),"back",Toast.LENGTH_SHORT).show();
            backgroundWork.execute(method,mail,trueCode,username);
//            finish();

//            Request request = new Request.Builder().url(url).build();
//            client.newCall(request).enqueue(new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    e.printStackTrace();
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//                    if(response.isSuccessful())
//                        Toast.makeText(getApplicationContext(),"sent you a text",Toast.LENGTH_SHORT).show();
//                }
//            });
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
            Intent intent = new Intent(FourthPannel.this, Register.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
