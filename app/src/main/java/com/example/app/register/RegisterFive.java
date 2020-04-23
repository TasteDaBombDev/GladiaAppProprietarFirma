package com.example.app.register;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app.R;
import com.github.florent37.materialtextfield.MaterialTextField;

import java.util.Random;

import okhttp3.OkHttpClient;

import static java.lang.Character.isDigit;

public class RegisterFive extends Fragment {

    private EditText cod;
    private String trueCode;
    private String method;
    private OkHttpClient client;
    private Animation make_error;
    private MaterialTextField code_outline;
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
        view = inflater.inflate(R.layout.activity_fourth_pannel,container,false);
        make_error = AnimationUtils.loadAnimation(getContext(),R.anim.shake);
        code_outline = view.findViewById(R.id.code_outline);

        cod = view.findViewById(R.id.cod);
        trueCode = generateCode();
        sendCode();
        client = new OkHttpClient();

        code_outline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!code_outline.hasFocus()) code_outline.setHasFocus(true);
                else code_outline.setHasFocus(false);
            }
        });

        Button register = view.findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
                if(cinci)
                {
                    Intent intent = new Intent(getContext(), Register.class);
                    startActivity(intent);
                }
            }
        });

        return view;
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_fourth_pannel);
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
            code_outline.startAnimation(make_error);
            cod.setError("Nu ai introdus codul corect!");
        }
    }
}
