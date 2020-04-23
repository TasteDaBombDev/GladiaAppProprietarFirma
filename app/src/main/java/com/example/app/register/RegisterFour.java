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
import android.widget.EditText;

import com.example.app.R;
import com.github.florent37.materialtextfield.MaterialTextField;

public class RegisterFour extends Fragment {

    private EditText pass,verifpass;
    private static String password;
    private MaterialTextField pass_outline,verif_pass_outline;
    private Animation make_error;
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
        view = inflater.inflate(R.layout.activity_third_pannel,container,false);
        pass = view.findViewById(R.id.pass);
        verifpass = view.findViewById(R.id.verif_pass);
        make_error = AnimationUtils.loadAnimation(getContext(),R.anim.shake);

        pass_outline = view.findViewById(R.id.pass_outline);
        verif_pass_outline = view.findViewById(R.id.verif_pass_outline);

        pass_outline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!pass_outline.hasFocus()){
                    pass_outline.setHasFocus(true);
                    verif_pass_outline.setHasFocus(false);
                }
                else pass_outline.setHasFocus(false);
            }
        });

        verif_pass_outline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!verif_pass_outline.hasFocus()){
                    pass_outline.setHasFocus(false);
                    verif_pass_outline.setHasFocus(true);
                }
                else verif_pass_outline.setHasFocus(false);
            }
        });
        return view;
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_third_pannel);
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

    public void openForthPannel() {
        password = pass.getText().toString().trim();
        String verifPassword = verifpass.getText().toString().trim();

        if (password.length() == 0)
        {
            pass_outline.startAnimation(make_error);
            pass.setError("Campul este gol");
            return;
        }

        if (verifPassword.length() == 0)
        {
            verif_pass_outline.startAnimation(make_error);
            verifpass.setError("Campul este gol");
            return;
        }

        if (!password.isEmpty() && !verifPassword.isEmpty() && password.compareTo(verifPassword) == 0) {
            if (password.length() < 8)
            {
                pass_outline.startAnimation(make_error);
                pass.setError("Parola trebuie sa fie de cel putin de 8 caractere");
                return;
            }
            else {

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
                    pass_outline.startAnimation(make_error);
                    pass.setError("Parola trebuie sa contina minim litere mari, mici si cifre");
                    return;
                }
            }
        } else
        {
            verif_pass_outline.startAnimation(make_error);
            verifpass.setError("Parolele nu coincid!");
            return;
        }

    }
}
