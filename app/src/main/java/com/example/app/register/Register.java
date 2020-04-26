package com.example.app.register;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.app.userScreen.MainScreen;
import com.example.app.R;
import com.example.app.ServerRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class Register extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        openApp();
    }

    public void openApp() {
        //register
        String nume = RegisterDoi.getNume();
        String prenume = RegisterDoi.getPrenume();
        String date = RegisterDoi.getBirthDate();
        String username = RegisterTrei.getUsername();
        String login = RegisterTrei.getLogin();
        String pass = RegisterFour.getPassword();


        Response.Listener<String> responseListener = new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    String message = jsonObject.getString("msg");
                    String username = jsonObject.getString("username");
                    int userID = jsonObject.getInt("userID");

                    String nume = jsonObject.getString("nume");
                    String prenume = jsonObject.getString("prenume");
                    String password = jsonObject.getString("password");
                    String mail = jsonObject.getString("mail");
                    String ziuaDeNastere = jsonObject.getString("ziuaDeNastere");
                    String sex = jsonObject.getString("sex");
                    String nrtel = jsonObject.getString("nrtel");

                    if(success){
                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Register.this, MainScreen.class);
                        intent.putExtra("idUser", userID);
                        intent.putExtra("username",username);

                        intent.putExtra("nume", nume);
                        intent.putExtra("prenume", prenume);
                        intent.putExtra("password", password);
                        intent.putExtra("mail", mail);
                        intent.putExtra("ziuaDeNastere", ziuaDeNastere);
                        intent.putExtra("sex", sex);
                        intent.putExtra("nrtel", nrtel);
                        intent.putExtra("pannel",2);

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
