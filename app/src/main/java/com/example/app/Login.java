package com.example.app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    @Override
    public void onBackPressed() {

    }

    public void login(View view) {
        EditText data = findViewById(R.id.cod);
        EditText pass = findViewById(R.id.login_pass);

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    String message = jsonObject.getString("msg");
                    int ID = jsonObject.getInt("userID");
                    String username = jsonObject.getString("username");

                    if(success){

                        Button relative = findViewById(R.id.login_btn);

                        Pair[] pairs = new Pair[1];
                        pairs[0] = new Pair<View, String>(relative,"imgTransition");

                        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(Login.this,pairs);


                        Intent intent = new Intent(Login.this, MainScreen.class);
                        intent.putExtra("userID", ID);
                        intent.putExtra("username",username);
                        startActivity(intent,activityOptions.toBundle());
                        finish();
//                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    } else{
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };


        ServerRequest serverRequest = new ServerRequest(data.getText().toString().trim(), pass.getText().toString().trim(),"http://gladiaholdings.com/PHP/login.php",listener);
        RequestQueue queue = Volley.newRequestQueue(Login.this);
        queue.add(serverRequest);




    }
}
