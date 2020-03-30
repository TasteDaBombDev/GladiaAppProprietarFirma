package com.example.app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        openApp();
    }

    public void openApp(){
        //register
        //Sterge Liniile de mai jos. Ele sunt facute sa te redirectioneze catre clasa de login.
        String nume = SecondPannel.getNume();
        String prenume = SecondPannel.getPrenume();
        String date = SecondPannel.getBirthDate();
        String username = SecondPannelPrime.getUsername();
        String login = SecondPannelPrime.getLogin();
        String pass = ThirdPannel.getPassword();

        String method = "register";
        BackgroundWork backgroundWork = new BackgroundWork(this);
        backgroundWork.execute(method,username, nume, prenume, pass, login, date);
        finish();
        //        Intent intent = new Intent(this,MainScreen.class);
//        startActivity(intent);

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
