package com.example.app.userScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.app.userScreen.events.petreceri.SelectLocation;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.view.View.GONE;

public class PrevizEvent extends AppCompatActivity {

    private static ProgressDialog loading;
    private int ID;
    private String imgPath, title;
    private ImageView profPic;
    private static EditText titleTV,adresa, dataXml, oraStartXml, oraEndXml, tematicaXml, numeArtistXml, genuriMuzicaleXml, descriereXml, tinutaXml, pretMancareXml, pretBauturaXml, pretBiletXml;
    private ImageButton back, butonEditat;

    private String data, oraStart, oraEnd, tematica, pozaArtist, numeArtist, genuriMuzicale, descriere, tinuta;
    private int mancare, bautura;
    private double pretMancare, pretBautura, pretBilet;
    private boolean editmode = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.previz_event);
        init();

        disable_content();
        adresa.setFocusable(false);
        adresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editmode){
                    Intent i = new Intent(PrevizEvent.this, SelectLocation.class);
                    SelectLocation.setAddress(String.valueOf(adresa.getText()));
                    i.putExtra("redirectedPage",-1);
                    startActivity(i);
                }
            }
        });

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
                if(!editmode) {
                    enable_content();
                    butonEditat.setImageResource(R.drawable.ic_check_black_24dp);
                    editmode = true;
                }else{
                    disable_content();
                    butonEditat.setImageResource(R.drawable.ic_edit_black_24dp);
                    editmode = false;
                }
                display();
            }
        });


        fetchData();

    }

    private void setImageRounded(int radius){
        Bitmap bitmap = ((BitmapDrawable)profPic.getDrawable()).getBitmap();
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(),bitmap);
        roundedBitmapDrawable.setCornerRadius(radius);
        profPic.setImageDrawable(roundedBitmapDrawable);
    }

    private void createDialog(){
        loading.setCancelable(false);
        loading.setTitle("Fetching event");
        loading.setMessage("We are fetching your event...");
        loading.show();
    }


    private void init(){
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
    }

    private void disable_content(){
        LinearLayout root = findViewById(R.id.root);
        for (int i = 1; i < 7; i += 2) {
            if(i == 7){
                LinearLayout l = findViewById(R.id.hour);
                l.getChildAt(0).setEnabled(false);
                l.getChildAt(1).setEnabled(false);
            }
            root.getChildAt(i).setEnabled(false);
        }
        for (int i = 8; i < root.getChildCount(); i++) {
            ConstraintLayout child = (ConstraintLayout) root.getChildAt(i);
            LinearLayout ll = (LinearLayout) child.getChildAt(1);
            ll.getChildAt(1).setEnabled(false);

        }
    }

    private void enable_content(){
        LinearLayout root = findViewById(R.id.root);
        for (int i = 1; i < 7; i += 2) {
            if(i == 7){
                LinearLayout l = findViewById(R.id.hour);
                l.getChildAt(0).setEnabled(true);
                l.getChildAt(1).setEnabled(true);
            }
            root.getChildAt(i).setEnabled(true);
        }
        for (int i = 8; i < root.getChildCount(); i++) {
            ConstraintLayout child = (ConstraintLayout) root.getChildAt(i);
            LinearLayout ll = (LinearLayout) child.getChildAt(1);
            ll.getChildAt(1).setEnabled(true);
        }
    }

    public static void setAdr(String adrss){
        adresa.setText(adrss);
    }

    private void display(){
        LinearLayout root = findViewById(R.id.root);
        if(editmode)
            for (int i = 8; i < root.getChildCount(); i++) {
                ConstraintLayout child = (ConstraintLayout) root.getChildAt(i);
                child.setVisibility(View.VISIBLE);
                CheckBox ck = (CheckBox) child.getChildAt(0);
                ck.setVisibility(View.VISIBLE);
            }
        else
            for (int i = 8; i < root.getChildCount(); i++) {
                ConstraintLayout child = (ConstraintLayout) root.getChildAt(i);
                CheckBox ck = (CheckBox) child.getChildAt(0);
                ck.setVisibility(GONE);
                LinearLayout ll = (LinearLayout) child.getChildAt(1);
                EditText t = (EditText) ll.getChildAt(1);
                if(String.valueOf(t.getText()).length() == 0)
                    child.setVisibility(GONE);
            }
    }

    private void fetchData(){
        Bundle extras = getIntent().getExtras();
        ID = extras.getInt("ID");
        String urlUpload = "http://gladiaholdings.com/PHP/fetchEvent.php";

        StringRequest stringRequest =  new StringRequest(Request.Method.POST, urlUpload, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    LinearLayout root = findViewById(R.id.root);
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

                    if(mancare == 1)
                        pretMancareXml.setText(String.valueOf(jsonObject.getDouble("pretMancare")));
                    else
                        root.getChildAt(13).setVisibility(GONE);

                    bautura = jsonObject.getInt("bautura");
                    if(bautura == 1)
                        pretBauturaXml.setText(String.valueOf(jsonObject.getDouble("pretBautura")));
                    else
                        root.getChildAt(14).setVisibility(GONE);

                    if(pretBilet != 0.0)
                        pretBiletXml.setText(String.valueOf(jsonObject.getDouble("pretBilet")));
                    else
                        root.getChildAt(15).setVisibility(GONE);

                    titleTV.setText(title);
                    Picasso.get().load(imgPath).into(profPic);
                    setImageRounded(40);
                    adresa.setText(jsonObject.getString("adresa"));

                    dataXml.setText(data);
                    oraStartXml.setText(oraStart);
                    oraEndXml.setText(oraEnd);

                    if(tematica.length() != 0)
                        tematicaXml.setText(tematica);
                    else
                        root.getChildAt(8).setVisibility(GONE);


                    if(numeArtist.length() != 0)
                        numeArtistXml.setText(numeArtist);
                    else
                        root.getChildAt(9).setVisibility(GONE);


                    genuriMuzicale = genuriMuzicale.replace("#",", ");
                    genuriMuzicale = genuriMuzicale.substring(1);
                    genuriMuzicaleXml.setText(genuriMuzicale);

                    if(descriere.length() != 0)
                        descriereXml.setText(descriere);
                    else
                        root.getChildAt(11).setVisibility(GONE);


                    if(tinuta.length() != 0)
                        tinutaXml.setText(tinuta);
                    else
                        root.getChildAt(12).setVisibility(GONE);




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
}
