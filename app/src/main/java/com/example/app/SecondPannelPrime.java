package com.example.app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.github.florent37.materialtextfield.MaterialTextField;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class SecondPannelPrime extends AppCompatActivity {

    private static String usern, log;
    private EditText login, username;
    private MaterialTextField login_outline, username_outline;
    private Animation make_error;

    public static String getLogin() {
        return log;
    }

    public static String getUsername() {
        return usern;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_pannel_prime);
        make_error = AnimationUtils.loadAnimation(this, R.anim.shake);

        username = findViewById(R.id.username);
        login = findViewById(R.id.login);
        login_outline = findViewById(R.id.login_outline);
        username_outline = findViewById(R.id.username_outline);


        username_outline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!username_outline.hasFocus()) {
                    username_outline.setHasFocus(true);
                    login_outline.setHasFocus(false);
                } else username_outline.setHasFocus(false);
            }
        });


        login_outline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!login_outline.hasFocus()) {
                    username_outline.setHasFocus(false);
                    login_outline.setHasFocus(true);
                } else login_outline.setHasFocus(false);
            }
        });
    }

    @Override
    public void onBackPressed() {

    }

    public void openThirdPannel(View view) {

        usern = username.getText().toString().trim();
        log = login.getText().toString().trim();

        if (usern.length() == 0) {
            username.setError("Campul este gol");
//            username_outline.setBackgroundResource(R.color.error);
            username_outline.startAnimation(make_error);
        }

        if (log.length() == 0) {
            login.setError("Campul este gol");
//            login_outline.setBackgroundResource(R.color.error);
            login_outline.startAnimation(make_error);
            return;
        }

        if (!usern.isEmpty() && !log.isEmpty()) {

            boolean[] go = {false,false};

            Response.Listener<String> responseListener = new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");

                        if (success) {
                            Intent intent = new Intent(SecondPannelPrime.this, ThirdPannel.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        } else {
                            String message = jsonObject.getString("msg");
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "JSON_err", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                }
            };


            RequestQueue queue = Volley.newRequestQueue(SecondPannelPrime.this);

            String regex = "[0-9]+";
            if (log.matches(regex)) {
                ServerRequest registerRequest2 = new ServerRequest("0", log, usern, "http://gladiaholdings.com/PHP/checkTelefon.php", responseListener);
                queue.add(registerRequest2);
            } else {
                ServerRequest registerRequest2 = new ServerRequest("0", log, usern, "http://gladiaholdings.com/PHP/checkMail.php", responseListener);
                queue.add(registerRequest2);
            }
        }
    }
}
