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

public class RegisterTrei extends Fragment {

    private static String usern, log;
    private static TextInputEditText login, username;
    private static TextInputLayout username_layout,login_layout;
    private static Animation make_error;
    private static RegisterTrei INSTANCE = null;
    private static boolean trei = false;

    public static String getLogin() {
        return log;
    }

    public static String getUsername() {
        return usern;
    }

    public static boolean getTrei(){
        return trei;
    }

    View view;

    public RegisterTrei(){
    }

    public static RegisterTrei getINSTANCE(){
        if (INSTANCE == null)
            INSTANCE = new RegisterTrei();
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
        view = inflater.inflate(R.layout.register_p2,container,false);
        make_error = AnimationUtils.loadAnimation(getContext(), R.anim.shake);

        username = view.findViewById(R.id.username);
        login = view.findViewById(R.id.login);
        username_layout = view.findViewById(R.id.username_layout);
        login_layout = view.findViewById(R.id.login_layout);

        return view;
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.register_p2);
//        make_error = AnimationUtils.loadAnimation(this, R.anim.shake);
//
//        username = findViewById(R.id.username);
//        login = findViewById(R.id.login);
//        login_outline = findViewById(R.id.login_outline);
//        username_outline = findViewById(R.id.username_outline);
//
//
//        username_outline.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!username_outline.hasFocus()) {
//                    username_outline.setHasFocus(true);
//                    login_outline.setHasFocus(false);
//                } else username_outline.setHasFocus(false);
//            }
//        });
//
//
//        login_outline.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!login_outline.hasFocus()) {
//                    username_outline.setHasFocus(false);
//                    login_outline.setHasFocus(true);
//                } else login_outline.setHasFocus(false);
//            }
//        });
//    }
//
//    @Override
//    public void onBackPressed() {
//
//    }

    public static void next() {

        usern = username.getText().toString().trim();
        log = login.getText().toString().trim();

        if (usern.length() == 0) {
            username_layout.setErrorEnabled(true);
            username_layout.setError("Campul este gol");
            username_layout.startAnimation(make_error);
            return;
        }
        else{
            username_layout.setErrorEnabled(false);
        }

        if (log.length() == 0) {
            login_layout.setErrorEnabled(true);
            login_layout.setError("Campul este gol");
            login_layout.startAnimation(make_error);
            return;
        } else {
            login_layout.setErrorEnabled(false);
        }

        trei = true;
//            Response.Listener<String> responseListener = new Response.Listener<String>() {
//
//                @Override
//                public void onResponse(String response) {
//                    try {
//                        JSONObject jsonObject = new JSONObject(response);
//                        boolean success = jsonObject.getBoolean("success");
//
//                        if (success) {
//                            trei = true;
//                        } else {
//                            String message = jsonObject.getString("msg");
//                            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
//                        }
//                    } catch (JSONException e) {
//                        Toast.makeText(getContext(), "JSON_err", Toast.LENGTH_SHORT).show();
//                        e.printStackTrace();
//                    }
//                }
//            };
//
//
//            RequestQueue queue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
//
//            String regex = "[0-9]+";
//            if (log.matches(regex)) {
//                ServerRequest registerRequest2 = new ServerRequest("0", log, usern, "http://gladiaholdings.com/PHP/checkTelefon.php", responseListener);
//                queue.add(registerRequest2);
//            } else {
//                ServerRequest registerRequest2 = new ServerRequest("0", log, usern, "http://gladiaholdings.com/PHP/checkMail.php", responseListener);
//                queue.add(registerRequest2);
//            }
    }
}
