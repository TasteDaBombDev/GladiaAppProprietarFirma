package com.example.app.register;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.example.app.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Random;

import static java.lang.Character.isDigit;

public class RegisterFive extends Fragment {

    private TextInputEditText cod;
    private String trueCode;
    private String method;
    private Animation make_error;
    private static boolean cinci = false;
    private static RegisterFive INSTANCE = null;

    View view;

    public RegisterFive(){

    }

    public static RegisterFive getINSTANCE(){
        if (INSTANCE == null)
            INSTANCE = new RegisterFive();
        return INSTANCE;
    }

    public static void resetINSTANCE(){
        INSTANCE = null;
    }

    public static boolean getCinci(){
        return cinci;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.register_p4,container,false);
        make_error = AnimationUtils.loadAnimation(getContext(),R.anim.shake);

        cod = view.findViewById(R.id.cod);
        trueCode = generateCode();
        sendCode();

        Button register = view.findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
                if(cinci)
                {
                    Intent intent = new Intent(getContext(), Register.class);
                    Bundle bundle = ActivityOptions.makeCustomAnimation(getContext(), R.anim.fade_in, R.anim.fade_to_black).toBundle();
                    startActivity(intent, bundle);
                }
            }
        });

        return view;
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.register_p4);
//        make_error = AnimationUtils.loadAnimation(this,R.anim.shake);
//        code_outline = findViewById(R.id.code_outline);
//
//        cod = findViewById(R.id.cod);
//        trueCode = generateCode();
//        sendCode();
//        client = new OkHttpClient();
//
//        code_outline.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(!code_outline.hasFocus()) code_outline.setHasFocus(true);
//                else code_outline.setHasFocus(false);
//            }
//        });
//    }

    private String generateCode(){
        Random r = new Random( System.currentTimeMillis() );
        int a = 10000 + r.nextInt(20000);
        Toast.makeText(getContext(), "" + a, Toast.LENGTH_SHORT).show();
        return String.valueOf(a);
    }

    private void sendCode(){
        method = RegisterTrei.getLogin();
        String regex = "[0-9]+";
        if(method.matches(regex)){
            //is a phone number
            Toast.makeText(getContext(),"you introduced a phone number " + trueCode,Toast.LENGTH_LONG).show();
        }
        else{
            //is a mail
            Toast.makeText(getContext(),"you introduced a mail" + trueCode,Toast.LENGTH_LONG).show();
        }
    }

    private boolean check(){
        String code = cod.getText().toString();
        return code.equals(trueCode);
    }

    public void registerUser() {
        if(check()){
            cinci = true;
        }
        else
        {
            cod.setError("Nu ai introdus codul corect!");
        }
    }
}
