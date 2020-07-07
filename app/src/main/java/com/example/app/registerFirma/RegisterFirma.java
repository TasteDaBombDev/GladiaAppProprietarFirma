package com.example.app.registerFirma;

import android.os.Bundle;

import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.app.R;

public class RegisterFirma extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_firma_main);
        ViewPager vp = findViewById(R.id.registerVP);

        EnumFragmentsRegister enumFragmentsRegister = new EnumFragmentsRegister(getSupportFragmentManager(), this);
        vp.setAdapter(enumFragmentsRegister);
        vp.setOffscreenPageLimit(3);
    }

    @Override
    public void onBackPressed() {

    }
}
