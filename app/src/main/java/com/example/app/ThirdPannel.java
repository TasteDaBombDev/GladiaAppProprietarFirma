package com.example.app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

import com.github.florent37.materialtextfield.MaterialTextField;

public class ThirdPannel extends AppCompatActivity {

    private EditText pass,verifpass;
    private static String password;
    private MaterialTextField pass_outline,verif_pass_outline;
    private Animation make_error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_pannel);
        pass = (EditText)findViewById(R.id.pass);
        verifpass = (EditText)findViewById(R.id.verif_pass);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        make_error = AnimationUtils.loadAnimation(this,R.anim.shake);

        pass_outline = findViewById(R.id.pass_outline);
        verif_pass_outline = findViewById(R.id.verif_pass_outline);

        pass_outline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!pass_outline.hasFocus()){
                    pass_outline.setHasFocus(true);
                    verif_pass_outline.setHasFocus(false);
                }
                else pass_outline.setHasFocus(false);
            }
        });

        verif_pass_outline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!verif_pass_outline.hasFocus()){
                    pass_outline.setHasFocus(false);
                    verif_pass_outline.setHasFocus(true);
                }
                else verif_pass_outline.setHasFocus(false);
            }
        });
    }

    @Override
    public void onBackPressed() {

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
                    openForthPannel();

            } break;
        }
        return false;
    }

    public void openForthPannel() {
        password = pass.getText().toString().trim();
        String verifPassword = verifpass.getText().toString().trim();

        if (password.length() == 0)
        {
            pass_outline.startAnimation(make_error);
            pass.setError("Campul este gol");
            return;
        }

        if (verifPassword.length() == 0)
        {
            verif_pass_outline.startAnimation(make_error);
            verifpass.setError("Campul este gol");
            return;
        }

        if (!password.isEmpty() && !verifPassword.isEmpty() && password.compareTo(verifPassword) == 0) {
            if (password.length() < 8)
            {
                pass_outline.startAnimation(make_error);
                pass.setError("Parola trebuie sa fie de cel putin de 8 caractere");
                return;
            }
            else {

                boolean letterSmall = false;
                boolean letterBig = false;
                boolean digit = false;
                for (int i = 0; i < password.length(); i++) {
                    if (password.charAt(i) >= 'a' && password.charAt(i) <= 'z')
                        letterSmall = true;
                    if (password.charAt(i) >= 'A' && password.charAt(i) <= 'Z')
                        letterBig = true;
                    if (password.charAt(i) >= '0' && password.charAt(i) <= '9')
                        digit = true;
                }
                if (letterBig && letterSmall && digit) {
                    Intent intent = new Intent(ThirdPannel.this, FourthPannel.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                } else
                {
                    pass_outline.startAnimation(make_error);
                    pass.setError("Parola trebuie sa contina minim litere mari, mici si cifre");
                    return;
                }
            }
        } else
        {
            verif_pass_outline.startAnimation(make_error);
            verifpass.setError("Parolele nu coincid!");
            return;
        }

    }

    public static String getPassword() {
        return password;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
