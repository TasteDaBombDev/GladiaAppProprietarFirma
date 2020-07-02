package com.example.app.userScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
    private EditText titleTV,adresa, dataXml, oraStartXml, oraEndXml, tematicaXml, numeArtistXml, genuriMuzicaleXml, descriereXml, tinutaXml, pretMancareXml, pretBauturaXml, pretBiletXml;
    private ImageButton back, butonEditat;

    private String data, oraStart, oraEnd, tematica, pozaArtist, numeArtist, genuriMuzicale, descriere, tinuta;
    private int mancare, bautura;
    private double pretMancare, pretBautura, pretBilet;
    private boolean clicked=false;
    private int i = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.previz_event);
        profPic = findViewById(R.id.profPic);
        titleTV = findViewById(R.id.title);
        adresa = findViewById(R.id.adresa);

        dataXml = findViewById(R.id.dataXml);
        oraStartXml = findViewById(R.id.oraStartXml);
        oraEndXml = findViewById(R.id.oraEndXml);
        tematicaXml = findViewById(R.id.tematicaXml);
        numeArtistXml = findViewById(R.id.numeArtistXml);
        genuriMuzicaleXml = findViewById(R.id.genuriMuzicaleXml);
        descriereXml = findViewById(R.id.descriereXml);
        tinutaXml = findViewById(R.id. tinutaXml);
        pretMancareXml = findViewById(R.id.pretMancareXml);
        pretBauturaXml = findViewById(R.id. pretBauturaXml);
        pretBiletXml = findViewById(R.id.pretBiletXml);

        profPic.setFocusable(false);
        profPic.setEnabled(false);
        titleTV.setFocusable(false);
        titleTV.setEnabled(false);
        adresa.setFocusable(false);
        adresa.setEnabled(false);
        dataXml.setFocusable(false);
        dataXml.setEnabled(false);
        oraStartXml.setFocusable(false);
        oraStartXml.setEnabled(false);
        oraEndXml.setFocusable(false);
        oraEndXml.setEnabled(false);
        tematicaXml.setFocusable(false);
        tematicaXml.setEnabled(false);
        numeArtistXml.setFocusable(false);
        numeArtistXml.setEnabled(false);
        genuriMuzicaleXml.setFocusable(false);
        genuriMuzicaleXml.setEnabled(false);
        descriereXml.setFocusable(false);
        descriereXml.setEnabled(false);
        tinutaXml.setFocusable(false);
        tinutaXml.setEnabled(false);
        pretMancareXml.setFocusable(false);
        pretMancareXml.setEnabled(false);
        pretBauturaXml.setFocusable(false);
        pretBauturaXml.setEnabled(false);
        pretBiletXml.setFocusable(false);
        pretBiletXml.setEnabled(false);

        loading = new ProgressDialog(PrevizEvent.this);
        back = findViewById(R.id.back);
        butonEditat = findViewById(R.id.butonEditat);
        createDialog();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        butonEditat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(butonEditat.isPressed())
                    i++;
                    if(i % 2 == 1) {
                        profPic.setFocusable(true);
                        profPic.setEnabled(true);
                        titleTV.setFocusable(true);
                        titleTV.setEnabled(true);
                        adresa.setFocusable(true);
                        adresa.setEnabled(true);
                        dataXml.setFocusable(true);
                        dataXml.setEnabled(true);
                        oraStartXml.setFocusable(true);
                        oraStartXml.setEnabled(true);
                        oraEndXml.setFocusable(true);
                        oraEndXml.setEnabled(true);
                        tematicaXml.setFocusable(true);
                        tematicaXml.setEnabled(true);
                        numeArtistXml.setFocusable(true);
                        numeArtistXml.setEnabled(true);
                        genuriMuzicaleXml.setFocusable(true);
                        genuriMuzicaleXml.setEnabled(true);
                        descriereXml.setFocusable(true);
                        descriereXml.setEnabled(true);
                        tinutaXml.setFocusable(true);
                        tinutaXml.setEnabled(true);
                        pretMancareXml.setFocusable(true);
                        pretMancareXml.setEnabled(true);
                        pretBauturaXml.setFocusable(true);
                        pretBauturaXml.setEnabled(true);
                        pretBiletXml.setFocusable(true);
                        pretBiletXml.setEnabled(true);
                    }else{
                        profPic.setFocusable(false);
                        profPic.setEnabled(false);
                        titleTV.setFocusable(false);
                        titleTV.setEnabled(false);
                        adresa.setFocusable(false);
                        adresa.setEnabled(false);
                        dataXml.setFocusable(false);
                        dataXml.setEnabled(false);
                        oraStartXml.setFocusable(false);
                        oraStartXml.setEnabled(false);
                        oraEndXml.setFocusable(false);
                        oraEndXml.setEnabled(false);
                        tematicaXml.setFocusable(false);
                        tematicaXml.setEnabled(false);
                        numeArtistXml.setFocusable(false);
                        numeArtistXml.setEnabled(false);
                        genuriMuzicaleXml.setFocusable(false);
                        genuriMuzicaleXml.setEnabled(false);
                        descriereXml.setFocusable(false);
                        descriereXml.setEnabled(false);
                        tinutaXml.setFocusable(false);
                        tinutaXml.setEnabled(false);
                        pretMancareXml.setFocusable(false);
                        pretMancareXml.setEnabled(false);
                        pretBauturaXml.setFocusable(false);
                        pretBauturaXml.setEnabled(false);
                        pretBiletXml.setFocusable(false);
                        pretBiletXml.setEnabled(false);

                    }


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

                    data = jsonObject.getString("data");
                    oraStart = jsonObject.getString("oraStart");
                    oraEnd = jsonObject.getString("oraEnd");
                    tematica = jsonObject.getString("tematica");
                    pozaArtist = jsonObject.getString("pozaArtist"); //trebuie adaugata
                    numeArtist = jsonObject.getString("numeArtist");
                    genuriMuzicale = jsonObject.getString("genuriMuzicale");
                    descriere = jsonObject.getString("descriere");
                    tinuta = jsonObject.getString("tinuta");
                    mancare = jsonObject.getInt("mancare");
                    if(mancare == 1){
                        pretMancare = jsonObject.getDouble("pretMancare");
                        pretMancareXml.setText(String.valueOf(pretMancare));
                    }else{
                        pretMancareXml.setText("Nu ati selectat mancare");
                    }
                    bautura = jsonObject.getInt("bautura");
                    if(bautura == 1){
                        pretBautura = jsonObject.getDouble("pretBautura");
                        pretBauturaXml.setText(String.valueOf(pretBautura));
                    }else{
                        pretBauturaXml.setText("Nu ati selectat bautura");
                    }

                    pretBilet = jsonObject.getDouble("pretBilet");
                    if(pretBilet == 0.0){
                        pretBiletXml.setText("Petrecerea e moca");
                    }else{
                        pretBiletXml.setText(String.valueOf(pretBilet));
                    }

                    titleTV.setText(title);
                    Picasso.get().load(imgPath).into(profPic);
                    setImageRounded();
                    adresa.setText(jsonObject.getString("adresa"));

                    dataXml.setText(data);
                    oraStartXml.setText(oraStart);
                    oraEndXml.setText(oraEnd);
                    tematicaXml.setText(tematica);
                    numeArtistXml.setText(numeArtist);
                    genuriMuzicaleXml.setText(genuriMuzicale);
                    descriereXml.setText(descriere);
                    tinutaXml.setText(tinuta);





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


//    private void setImageRounded(){
//        Bitmap bitmap = ((BitmapDrawable)profPic.getDrawable()).getBitmap();
//        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(),bitmap);
//        roundedBitmapDrawable.setCircular(true);
//        profPic.setImageDrawable(roundedBitmapDrawable);
//    }

    private void setImageRounded(){
        Bitmap bitmap = ((BitmapDrawable)profPic.getDrawable()).getBitmap();
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(),bitmap);
        roundedBitmapDrawable.setCornerRadius(40);
        profPic.setImageDrawable(roundedBitmapDrawable);
    }

    private void createDialog(){
        loading.setCancelable(false);
        loading.setTitle("Fetching event");
        loading.setMessage("We are fetching your event...");
        loading.show();
    }

}
