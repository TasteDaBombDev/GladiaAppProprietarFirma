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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.app.R;
import com.example.app.ServerRequest;
import com.github.florent37.materialtextfield.MaterialTextField;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterTrei extends Fragment {

    private static String usern = "-", log = "-";
    private EditText login, username;
    private MaterialTextField login_outline, username_outline;
    private Animation make_error;
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
        view = inflater.inflate(R.layout.activity_second_pannel_prime,container,false);
        make_error = AnimationUtils.loadAnimation(getContext(), R.anim.shake);

        username = view.findViewById(R.id.username);
        login = view.findViewById(R.id.login);
        login_outline = view.findViewById(R.id.login_outline);
        username_outline = view.findViewById(R.id.username_outline);


        username_outline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!username_outline.hasFocus()) {
                    username_outline.setHasFocus(true);
                    login_outline.setHasFocus(false);
                } else username_outline.setHasFocus(false);
            }
        });


        login_outline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!login_outline.hasFocus()) {
                    username_outline.setHasFocus(false);
                    login_outline.setHasFocus(true);
                } else login_outline.setHasFocus(false);
            }
        });
        return view;
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_second_pannel_prime);
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

    public void openThirdPannel() {

        usern = username.getText().toString().trim();
        log = login.getText().toString().trim();

        if (usern.length() == 0) {
            username.setError("Campul este gol");
//            username_outline.setBackgroundResource(R.color.error);
            username_outline.startAnimation(make_error);
        }

        if (log.length() == 0) {
            login.setError("Campul este gol");
//            login_outline.setBackgroundResource(R.color.error);
            login_outline.startAnimation(make_error);
            return;
        }

        if (!usern.isEmpty() && !log.isEmpty()) {

            Response.Listener<String> responseListener = new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");

                        if (success) {
                            trei = true;
                        } else {
                            String message = jsonObject.getString("msg");
                            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(getContext(), "JSON_err", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            };


            RequestQueue queue = Volley.newRequestQueue(getContext());

            String regex = "[0-9]+";
            if (log.matches(regex)) {
                ServerRequest registerRequest2 = new ServerRequest("0", log, usern, "http://gladiaholdings.com/PHP/checkTelefon.php", responseListener);
                queue.add(registerRequest2);
            } else {
                ServerRequest registerRequest2 = new ServerRequest("0", log, usern, "http://gladiaholdings.com/PHP/checkMail.php", responseListener);
                queue.add(registerRequest2);
            }
        }
    }
}
