package com.example.app.register;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static java.lang.Character.isDigit;

public class RegisterFive extends Fragment {

    private TextInputEditText cod;
    private String trueCode;
    private String method;
    private Animation make_error;
    private static boolean cinci = false;
    private static RegisterFive INSTANCE = null;
    private EditText d1,d2,d3,d4,d5;
    private ProgressDialog loading;
    private String urlUpload = "http://gladiaholdings.com/PHP/send_mail.php";

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
        d1 = view.findViewById(R.id.d1);
        d2 = view.findViewById(R.id.d2);
        d3 = view.findViewById(R.id.d3);
        d4 = view.findViewById(R.id.d4);
        d5 = view.findViewById(R.id.d5);
        loading = new ProgressDialog(getContext());

        trueCode = RegisterMainScreen.getCode();
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


        //Request Focus
        d1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    d1.setText("");
            }
        });
        d1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count == 1)
                {
                    d2.requestFocus();
                    d2.setText("");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        d2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count == 1)
                {
                    d3.requestFocus();
                    d3.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        d3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count == 1)
                {
                    d4.requestFocus();
                    d4.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        d4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count == 1)
                {
                    d5.requestFocus();
                    d5.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

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

    private void sendCode(){
        method = RegisterTrei.getLogin();
        String regex = "[0-9]+";
        if(method.matches(regex)){
            //is a phone number
            Toast.makeText(getContext(),"you introduced a phone number " + trueCode,Toast.LENGTH_LONG).show();
        }
        else{
            //is a mail
            StringRequest request = new StringRequest(Request.Method.POST, urlUpload, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    loading.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");
                        if(!success){
                            Toast.makeText(getContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    loading.dismiss();
                    Toast.makeText(getContext(), "Eroare de procesare! Va rugam sa incercati din nou!", Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("mail",RegisterTrei.getLogin());
                    params.put("code",trueCode);
                    params.put("username",RegisterTrei.getUsername());

                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(request);
            createDialog();

        }
    }

    private boolean check(){
        String code = d1.getText().toString() + d2.getText().toString() + d3.getText().toString() + d4.getText().toString() + d5.getText().toString();
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

    private void createDialog(){
        loading.setCancelable(false);
        loading.setTitle("Sending code");
        loading.setMessage("We are sending the code. Please wait...");
        loading.show();
    }
}
