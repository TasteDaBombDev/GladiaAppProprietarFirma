package com.example.app.registerFirma;

import android.os.Bundle;
import android.os.Handler;
import android.transition.TransitionManager;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.app.R;

import java.util.ArrayList;

public class RegisterFirma extends AppCompatActivity {

    private ArrayList<String> texts = new ArrayList<>();
    private ConstraintLayout header;
    private Button save;
    private boolean ok = true;
    private Animation fadeIn = new AlphaAnimation(0.0f, 1.0f), fadeOut = new AlphaAnimation(1.0f, 0.0f);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_firma_main);
        fadeIn.setDuration(300);
        fadeIn.setFillAfter(true);
        fadeOut.setDuration(300);
        fadeOut.setFillAfter(true);


        save = findViewById(R.id.save);
        save.setBackgroundResource(R.drawable.circle);

        header = findViewById(R.id.header);
        TransitionManager.beginDelayedTransition(header);


        final TextView text = findViewById(R.id.textPrompt);
        texts.add("Let's set up your profile picture, name and email");
        texts.add("Now, let's set up your bussiness location");
        texts.add("Details about your firm");
        texts.add("Your menu");


        final ViewPager vp = findViewById(R.id.registerVP);
        Bundle extras = getIntent().getExtras();
        final EnumFragmentsRegister enumFragmentsRegister = new EnumFragmentsRegister(getSupportFragmentManager(), this, extras.getBoolean("locatie"));
        vp.setAdapter(enumFragmentsRegister);
        vp.setOffscreenPageLimit(3);

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position)
            {
                text.startAnimation(fadeOut);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        text.setText(texts.get(position));
                    }
                },300);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        text.startAnimation(fadeIn);
                    }
                },600);
                if(position == (enumFragmentsRegister.getCount() - 1) && ok) {
                    save.setVisibility(View.VISIBLE);
                    save.startAnimation(fadeIn);
                    ok = false;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
