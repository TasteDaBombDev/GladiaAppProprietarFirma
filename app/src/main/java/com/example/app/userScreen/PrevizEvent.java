package com.example.app.userScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PrevizEvent extends AppCompatActivity {

    private static ProgressDialog loading;
    private int ID;
    private String imgPath, title;
    private ImageView profPic;
    private TextView titleTV,adresa;
    private ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.previz_event);
        profPic = findViewById(R.id.profPic);
        titleTV = findViewById(R.id.title);
        adresa = findViewById(R.id.adresa);
        loading = new ProgressDialog(PrevizEvent.this);
        back = findViewById(R.id.back);
        createDialog();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        Bundle extras = getIntent().getExtras();
        ID = extras.getInt("ID");
        String urlUpload = "http://gladiaholdings.com/PHP/fetchEvent.php";

        StringRequest stringRequest =  new StringRequest(Request.Method.POST, urlUpload, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    imgPath = jsonObject.getString("poza");
                    title = jsonObject.getString("title");

                    titleTV.setText(title);
                    Picasso.get().load(imgPath).into(profPic);
                    setImageRounded();
                    adresa.setText(jsonObject.getString("adresa"));



                    loading.dismiss();
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Error loading your event" + response, Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Check your internet connection and try again.", Toast.LENGTH_SHORT).show();
                loading.dismiss();
                onBackPressed();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ID", String.valueOf(ID));
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);

    }


    private void setImageRounded(){
        Bitmap bitmap = ((BitmapDrawable)profPic.getDrawable()).getBitmap();
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(),bitmap);
        roundedBitmapDrawable.setCircular(true);
        profPic.setImageDrawable(roundedBitmapDrawable);
    }

    private void createDialog(){
        loading.setCancelable(false);
        loading.setTitle("Fetching event");
        loading.setMessage("We are fetching your event...");
        loading.show();
    }

}
