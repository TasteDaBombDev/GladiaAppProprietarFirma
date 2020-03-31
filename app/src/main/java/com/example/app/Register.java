package com.example.app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Register extends AppCompatActivity {

    private boolean isRegistred = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        openApp();
    }

    public void openApp() {
        //register
        String nume = SecondPannel.getNume();
        String prenume = SecondPannel.getPrenume();
        String date = SecondPannel.getBirthDate();
        String username = SecondPannelPrime.getUsername();
        String login = SecondPannelPrime.getLogin();
        String pass = ThirdPannel.getPassword();


        Response.Listener<String> responseListener = new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    String message = jsonObject.getString("msg");
                    int userID = jsonObject.getInt("userID");


                    if(success){
                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Register.this, MainScreen.class);
                        intent.putExtra("idUser", userID);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),"JSON_err",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        };

        ServerRequest serverRequest = new ServerRequest(username,nume,prenume,pass,login,date,"http://gladiaholdings.com/PHP/register.php",responseListener);
        RequestQueue queue = Volley.newRequestQueue(Register.this);
        queue.add(serverRequest);
//        finish();

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
