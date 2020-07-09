package com.example.app.registerFirma;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.app.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Register1 extends Fragment {

    private static Register1 INSTANCE = null;
    private View view;
    private boolean show;
    private static TextInputEditText name, email;
    private TextInputLayout emailL;

    public Register1(boolean show){
        this.show = show;
    }

    public static Register1 getINSTANCE(boolean show){
        if (INSTANCE == null)
            INSTANCE = new Register1(show);
        return INSTANCE;
    }

    public static void resetINSTANCE(){
        INSTANCE = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.register1, container, false);
        emailL = view.findViewById(R.id.emailFirmaL);
        name = view.findViewById(R.id.nameFirma);
        email = view.findViewById(R.id.emailFirma);

        if(!show)
            emailL.setVisibility(View.GONE);

        return view;
    }

    public static String getName(){
        return name.getText().toString().trim();
    }

    public static String getEmail(){
        return email.getText().toString().trim();
    }

    public static boolean areCompleted(){
        return !name.getText().toString().trim().equals("") && !email.getText().toString().trim().equals("");
    }
}
