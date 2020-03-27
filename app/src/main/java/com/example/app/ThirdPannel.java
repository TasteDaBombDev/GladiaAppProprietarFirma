package com.example.app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

public class ThirdPannel extends AppCompatActivity {

    private EditText pass,verifpass;
    private static String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_pannel);
        pass = (EditText)findViewById(R.id.pass);
        verifpass = (EditText)findViewById(R.id.verif_pass);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    @Override
    public void onBackPressed() {

    }

    public void openForthPannel(View view) {
        password = pass.getText().toString().trim();
        String verifPassword = verifpass.getText().toString().trim();

        if (password.length() == 0)
        {
            pass.setError("Campul este gol");
            return;
        }

        if (verifPassword.length() == 0)
        {
            verifpass.setError("Campul este gol");
            return;
        }

        if (!password.isEmpty() && !verifPassword.isEmpty() && password.compareTo(verifPassword) == 0) {
            if (password.length() < 8)
            {
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
                    pass.setError("Parola trebuie sa contina minim litere mari, mici si cifre");
                    return;
                }
            }
        } else
        {
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
