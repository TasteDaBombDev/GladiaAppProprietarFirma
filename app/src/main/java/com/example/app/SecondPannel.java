package com.example.app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SecondPannel extends AppCompatActivity {

    private EditText nume,prenume,username,login;
    private String num,prenum,usern,log;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_pannel);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        nume = findViewById(R.id.nume);
        prenume = findViewById(R.id.prenume);
        username = findViewById(R.id.username);
        login = findViewById(R.id.login);

        num = nume.getText().toString();
        prenum = prenume.getText().toString();
        usern = username.getText().toString();
        log = login.getText().toString();
    }

    public void openThirdPannel(View view){
        if(num.isEmpty())
            nume.setError("Campul este gol");

        if(prenum.isEmpty())
            prenume.setError("Campul este gol");

        if(usern.isEmpty())
            username.setError("Campul este gol");

        if(log.isEmpty())
            login.setError("Campul este gol");


        if(!num.isEmpty() && prenum.isEmpty() && usern.isEmpty() && !log.isEmpty())
        {
            Intent intent = new Intent(this,ThirdPannel.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
