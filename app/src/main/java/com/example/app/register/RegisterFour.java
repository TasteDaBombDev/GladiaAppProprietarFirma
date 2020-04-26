package com.example.app.register;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.app.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterFour extends Fragment {

    private static TextInputEditText pass,verifPassword;
    private static TextInputLayout pass_layout, verif_pass_layout;
    private static String password,verifpass;
    private static Animation make_error;
    private static RegisterFour INSTANCE = null;
    private static boolean patru = false;
    View view;

    public RegisterFour(){

    }

    public static RegisterFour getINSTANCE(){
        if (INSTANCE == null)
            INSTANCE = new RegisterFour();
        return INSTANCE;
    }

    public static void resetINSTANCE(){
        INSTANCE = null;
    }

    public static String getPassword() {
        return password;
    }

    public static boolean getPatru(){
        return patru;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.register_p3,container,false);
        pass = view.findViewById(R.id.pass);
        verifPassword = view.findViewById(R.id.verif_pass);
        pass_layout = view.findViewById(R.id.pass_layout);
        verif_pass_layout = view.findViewById(R.id.verif_pass_layout);

        make_error = AnimationUtils.loadAnimation(getContext(),R.anim.shake);

        return view;
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.register_p3);
//        pass = (EditText)findViewById(R.id.pass);
//        verifpass = (EditText)findViewById(R.id.verif_pass);
//        make_error = AnimationUtils.loadAnimation(this,R.anim.shake);
//
//        pass_outline = findViewById(R.id.pass_outline);
//        verif_pass_outline = findViewById(R.id.verif_pass_outline);
//
//        pass_outline.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(!pass_outline.hasFocus()){
//                    pass_outline.setHasFocus(true);
//                    verif_pass_outline.setHasFocus(false);
//                }
//                else pass_outline.setHasFocus(false);
//            }
//        });
//
//        verif_pass_outline.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(!verif_pass_outline.hasFocus()){
//                    pass_outline.setHasFocus(false);
//                    verif_pass_outline.setHasFocus(true);
//                }
//                else verif_pass_outline.setHasFocus(false);
//            }
//        });
//    }
//
//    @Override
//    public void onBackPressed() {
//
//    }

    public static void next() {
        password = pass.getText().toString().trim();
        verifpass = verifPassword.getText().toString().trim();

        if (password.length() == 0)
        {
            pass_layout.setErrorEnabled(true);
            pass_layout.setError("Campul este gol");
            pass_layout.startAnimation(make_error);
            return;
        }
        else{
            pass_layout.setErrorEnabled(false);
        }

        if (verifpass.length() == 0)
        {
            verif_pass_layout.setErrorEnabled(true);
            verif_pass_layout.setError("Campul este gol");
            verif_pass_layout.startAnimation(make_error);
            return;
        }
        else{
            verif_pass_layout.setErrorEnabled(false);
        }

        if (!password.isEmpty() && !verifpass.isEmpty() && password.compareTo(verifpass) == 0) {
            if (password.length() < 8)
            {
                pass_layout.setErrorEnabled(true);
                pass_layout.setError("Parola trebuie sa fie de cel putin de 8 caractere");
                pass_layout.startAnimation(make_error);
                return;
            }
            else {
                pass_layout.setErrorEnabled(false);

                boolean letterSmall = false;
                boolean letterBig = false;
                boolean digit = false;
                for (int i = 0; i < password.length(); i++) {
                    if (password.charAt(i) >= 'a' && password.charAt(i) <= 'z')
                        letterSmall = true;
                    if (password.charAt(i) >= 'A' && password.charAt(i) <= 'Z')
                        letterBig = true;
                    if (password.charAt(i) >= '0' && password.charAt(i) <= '9')
                        digit = true;
                }
                if (letterBig && letterSmall && digit) {
                    patru = true;
                } else
                {
                    pass_layout.setErrorEnabled(true);
                    pass_layout.setError("Parola trebuie sa contina minim litere mari, mici si cifre");
                    pass_layout.startAnimation(make_error);
                    return;
                }
            }
        }
        else
        {
            verif_pass_layout.setErrorEnabled(true);
            verif_pass_layout.setError("Parolele nu coincid!");
            verif_pass_layout.startAnimation(make_error);
            return;
        }

    }
}
