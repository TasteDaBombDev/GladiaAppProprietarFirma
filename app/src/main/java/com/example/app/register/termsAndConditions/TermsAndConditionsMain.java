package com.example.app.register.termsAndConditions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.app.R;

public class TermsAndConditionsMain extends AppCompatActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terms_and_conditions_main);

        viewPager = findViewById(R.id.tcPager);
        EnumFragmentsTerms enumFragmentsTerms = new EnumFragmentsTerms(getSupportFragmentManager(),this);
        viewPager.setAdapter(enumFragmentsTerms);

    }
}
