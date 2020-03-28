package com.example.app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.florent37.materialtextfield.MaterialTextField;

import java.util.Calendar;

public class SecondPannel extends AppCompatActivity {

    private EditText nume,prenume,date;
    private static String num,prenum,birthDate;
    private MaterialTextField date_outline,prenume_outline,nume_outline;
    private Animation make_error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_pannel);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        make_error = AnimationUtils.loadAnimation(this,R.anim.shake);

        nume = findViewById(R.id.nume);
        prenume = findViewById(R.id.prenume);
        date_outline = findViewById(R.id.date_outline);
        prenume_outline = findViewById(R.id.prenume_outline);
        nume_outline = findViewById(R.id.nume_outline);
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

        nume_outline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!nume_outline.hasFocus()){
                    prenume_outline.setHasFocus(false);
                    date_outline.setHasFocus(false);
                    nume_outline.setHasFocus(true);
                }
                else nume_outline.setHasFocus(false);
            }
        });

        prenume_outline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!prenume_outline.hasFocus()){
                    nume_outline.setHasFocus(false);
                    date_outline.setHasFocus(false);
                    prenume_outline.setHasFocus(true);
                }
                else prenume_outline.setHasFocus(false);
            }
        });

        date_outline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!date_outline.hasFocus()){
                    prenume_outline.setHasFocus(false);
                    date_outline.setHasFocus(true);
                    nume_outline.setHasFocus(false);
                }
                else date_outline.setHasFocus(false);
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
                    openSecondPannelPrime();

            } break;
        }
        return false;
    }

    public void openSecondPannelPrime(){

        num = nume.getText().toString().trim();
        prenum = prenume.getText().toString().trim();
        birthDate = date.getText().toString().trim();

        if(num.length() == 0)
        {
            nume.setError("Campul este gol");
            nume_outline.startAnimation(make_error);
        }

        if(prenum.length() == 0)
        {
            prenume.setError("Campul este gol");
//            prenume_outline.setBackgroundResource(R.color.error);
            prenume_outline.startAnimation(make_error);
        }

        if(!num.isEmpty() && !prenum.isEmpty())
        {
            Intent intent = new Intent(this,SecondPannelPrime.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    public static String getNume(){
        return num;
    }

    public static String getPrenume() {
        return prenum;
    }

    public static String getBirthDate() {
        return birthDate;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
