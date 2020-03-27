package com.example.app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.florent37.materialtextfield.MaterialTextField;

import java.util.Calendar;

public class SecondPannel extends AppCompatActivity {

    private EditText nume,prenume,username,login,date;
    private static String num,prenum,usern,log;
    private MaterialTextField login_outline;

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
        login_outline = findViewById(R.id.login_outline);
        date = findViewById(R.id.zi_nastere);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(SecondPannel.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String dateOn = day + "/" + month + "/" + year;
                        date.setText(dateOn);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

    }

    @Override
    public void onBackPressed() {

    }

    public void openThirdPannel(View view){

        num = nume.getText().toString().trim();
        prenum = prenume.getText().toString().trim();
        usern = username.getText().toString().trim();
        log = login.getText().toString().trim();

        if(num.length() == 0)
            nume.setError("Campul este gol");

        if(prenum.length() == 0)
            prenume.setError("Campul este gol");

        if(usern.length() == 0)
            username.setError("Campul este gol");

        if(log.length() == 0)
            login.setError("Campul este gol");

        if(!num.isEmpty() && !prenum.isEmpty() && !usern.isEmpty() && !log.isEmpty())
        {
            Intent intent = new Intent(this,ThirdPannel.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    public static String getLogin(){
        return log;
    }

    public static String getNume(){
        return num;
    }

    public static String getUsername() {
        return usern;
    }

    public static String getPrenume() {
        return prenum;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
