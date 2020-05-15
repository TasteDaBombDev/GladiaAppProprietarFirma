package com.example.app.register.termsAndConditions;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.app.MainActivity;
import com.example.app.R;
import com.example.app.register.RegisterMainScreen;
import com.example.app.userScreen.MainScreen;

public class Terms3 extends Fragment {

    private static Terms3 INSTANCE = null;
    private View view;

    public Terms3(){

    }

    public static Terms3 getINSTANCE() {
        if(INSTANCE == null)
            return new Terms3();
        return INSTANCE;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.terms3, container, false);

        Button toRegister = view.findViewById(R.id.toRegister), cancelRegister = view.findViewById(R.id.cancelRegister);

        toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), RegisterMainScreen.class);
                startActivity(intent);
            }
        });

        cancelRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
