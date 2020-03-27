package com.example.app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

import static java.lang.Character.isDigit;

public class FourthPannel extends AppCompatActivity {

    private EditText cod;
    private String trueCode;
    private String method;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth_pannel);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        cod = findViewById(R.id.cod);
        trueCode = generateCode();
        sendCode();
    }

    private String generateCode(){
        Random r = new Random( System.currentTimeMillis() );
        int a = 10000 + r.nextInt(20000);
        return String.valueOf(a);
    }

    private void sendCode(){
        method = SecondPannel.getLogin();
        String regex = "[0-9]+";
        if(method.matches(regex)){
            //is a phone number
            Toast.makeText(getApplicationContext(),"you introduced a phone number " + trueCode,Toast.LENGTH_LONG).show();
        }
        else{
            //is a mail
            Toast.makeText(getApplicationContext(),"you introduced a mail" + trueCode,Toast.LENGTH_LONG).show();
        }
    }

    private boolean check(){
        String code = cod.getText().toString();
        return code.equals(trueCode);
    }

    public void registerUser(View view) {
        if(check()){
            Intent intent = new Intent(FourthPannel.this, Register.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
        else
            cod.setError("Nu ai introdus codul corect!");
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
