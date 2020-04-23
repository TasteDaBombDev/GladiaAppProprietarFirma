package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.app.register.RegisterDoi;
import com.example.app.register.RegisterMainScreen;


public class MainActivity extends AppCompatActivity {

    private float x1,x2,y1,y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView welcome = findViewById(R.id.welcome);
        Animation anim = AnimationUtils.loadAnimation(this,R.anim.popin);
        welcome.startAnimation(anim);
//        getWindow().setTransitionBackgroundFadeDuration(2000);
    }


    @Override
    public void onBackPressed() {

    }

    public boolean onTouchEvent(MotionEvent touchEvent){
        switch (touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:{
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
            } break;
            case MotionEvent.ACTION_UP:{
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if(x1 < x2)
                    login();
                else if (x1 > x2)
                    openSecondScreen();

            } break;
        }
//        Toast.makeText(getApplicationContext(),x1 + " " + x2 + " " + y1 + " " + y2, Toast.LENGTH_SHORT).show();
        return false;
    }


    public void openSecondScreen() {
        Intent intent = new Intent(this, RegisterMainScreen.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }


    public void login() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}
