package com.example.app.userScreen.events.previzEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app.R;
import com.example.app.userScreen.createEvents.petreceri.SelectLocation;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import jp.wasabeef.picasso.transformations.MaskTransformation;

import static android.view.View.GONE;

public class PrevizEvent extends Fragment{


    private static PrevizEvent INSTANCE = null;
    private View view;
    private ArrayList<String> a = new ArrayList<>();
    
    private static ProgressDialog loading;
    private static String imgPath, title;
    private ImageView profPic, artistPic;
    private static EditText titleTV,adresa, dataXml, oraStartXml, oraEndXml, tematicaXml, numeArtistXml, genuriMuzicaleXml, descriereXml, tinutaXml, pretMancareXml, pretBauturaXml, pretBiletXml;

    private String data, oraStart, oraEnd, tematica, pozaArtist, numeArtist, genuriMuzicale, descriere, tinuta, adr;
    private int mancare, bautura;
    private static double pretMancare, pretBautura, pretBilet, lat, lng;
    private boolean editmode = false;

    public PrevizEvent(){

    }

    public static PrevizEvent getINSTANCE(){
        if(INSTANCE == null)
            INSTANCE = new PrevizEvent();
        return INSTANCE;
    }

    public static void resetINSTANCE(){
        INSTANCE = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.previz_event,container,false);
        
        init();

        timePikers();
        disable_content();
        adresa.setFocusable(false);
        adresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editmode){
                    Intent i = new Intent(getContext(), SelectLocation.class);
                    SelectLocation.setAddress(String.valueOf(adresa.getText()));
                    i.putExtra("redirectedPage",-1);
                    startActivity(i);
                }
            }
        });

        loading = new ProgressDialog(getContext());
        createDialog();

        PrevizEventMain.getEdit().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit();
            }
        });


        fetchData();

        LinearLayout root = view.findViewById(R.id.root);
        for (int i = 3; i < root.getChildCount(); i++) {
            final ConstraintLayout c = (ConstraintLayout) root.getChildAt(i);
            final CheckBox cb = (CheckBox) c.getChildAt(0);

            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    EditText e = (EditText) c.getChildAt(2);
                    TextView ttv = (TextView) c.getChildAt(3);
                    String p = ttv.getText().toString() + ": " + e.getText().toString();

                    if(!e.getText().toString().equals(""))
                        if(isChecked)
                            a.add(p);
                        else
                            a.remove(p);
                }
            });

            EditText infos = (EditText) c.getChildAt(2);
            if(infos.getText().toString().length() == 0)
                cb.setEnabled(false);
            infos.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(s.length() == 0)
                    {
                        cb.setEnabled(false);
                        TextView ttv = (TextView) c.getChildAt(3);
                        for (int j = 0; j < a.size(); j++) {
                            if(a.get(j).contains(ttv.getText().toString()))
                                a.remove(j);
                        }
                        cb.setChecked(false);
                    }
                        else
                            cb.setEnabled(true);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }

        return view;
    }

    public void edit(){
        if(!editmode) {
            enable_content();
            PrevizEventMain.getEdit().setImageResource(R.drawable.ic_check_black_24dp);
            editmode = true;
        }else{
            sendDataToServer();
            disable_content();
            PrevizEventMain.getEdit().setImageResource(R.drawable.ic_edit_black_24dp);
            editmode = false;
        }
        display();
    }

    private void createDialog(){
        loading.setCancelable(false);
        loading.setTitle("Fetching event");
        loading.setMessage("We are fetching your event...");
        loading.show();
    }

    private void createSendingDialog(){
        loading.setCancelable(false);
        loading.setTitle("Registering changes");
        loading.setMessage("We are keeping your event...");
        loading.show();
    }

    private void init(){
        profPic = view.findViewById(R.id.profPic);
        titleTV = view.findViewById(R.id.title);
        adresa = view.findViewById(R.id.adresa);

        dataXml = view.findViewById(R.id.dataXml);
        oraStartXml = view.findViewById(R.id.oraStartXml);
        oraEndXml = view.findViewById(R.id.oraEndXml);
        tematicaXml = view.findViewById(R.id.tematicaXml);
        numeArtistXml = view.findViewById(R.id.numeArtistXml);
        genuriMuzicaleXml = view.findViewById(R.id.genuriMuzicaleXml);
        descriereXml = view.findViewById(R.id.descriereXml);
        tinutaXml = view.findViewById(R.id. tinutaXml);
        pretMancareXml = view.findViewById(R.id.pretMancareXml);
        pretBauturaXml = view.findViewById(R.id. pretBauturaXml);
        pretBiletXml = view.findViewById(R.id.pretBiletXml);
        artistPic = view.findViewById(R.id.artistPic);
    }

    private void disable_content(){
        LinearLayout root = view.findViewById(R.id.root);
        for (int i = 1; i < root.getChildCount(); i++) {
            ConstraintLayout c = (ConstraintLayout) root.getChildAt(i);
            c.getChildAt(2).setBackgroundResource(R.drawable.aggro_zone);
            c.getChildAt(2).setEnabled(false);
            if(i == 2) {
                c.getChildAt(3).setBackgroundResource(R.drawable.aggro_zone);
                c.getChildAt(3).setEnabled(false);
                c.getChildAt(4).setBackgroundResource(R.drawable.aggro_zone);
                c.getChildAt(4).setEnabled(false);
            }
        }
    }

    private void enable_content(){
        LinearLayout root = view.findViewById(R.id.root);
        for (int i = 1; i < root.getChildCount(); i++) {
            ConstraintLayout c = (ConstraintLayout) root.getChildAt(i);
            c.getChildAt(2).setBackgroundResource(R.drawable.edit_text_shape);
            c.getChildAt(2).setEnabled(true);
            if(i == 2) {
                c.getChildAt(3).setBackgroundResource(R.drawable.edit_text_shape);
                c.getChildAt(3).setEnabled(true);
                c.getChildAt(4).setBackgroundResource(R.drawable.edit_text_shape);
                c.getChildAt(4).setEnabled(true);
            }
        }
    }

    public static void setAdr(String adrss){
        adresa.setText(adrss);
    }

    private void display(){
        LinearLayout root = view.findViewById(R.id.root);
        if(editmode)
            for (int i = 3; i < root.getChildCount(); i++) {
                ConstraintLayout child = (ConstraintLayout) root.getChildAt(i);
                child.setVisibility(View.VISIBLE);
                CheckBox ck = (CheckBox) child.getChildAt(0);
                ck.setVisibility(View.VISIBLE);
            }
        else
            for (int i = 3; i < root.getChildCount(); i++) {
                ConstraintLayout child = (ConstraintLayout) root.getChildAt(i);
                CheckBox ck = (CheckBox) child.getChildAt(0);
                ck.setVisibility(View.INVISIBLE);
                EditText t = (EditText) child.getChildAt(2);
                if(String.valueOf(t.getText()).length() == 0)
                    child.setVisibility(GONE);
            }
    }

    private void fetchData(){
        String urlUpload = "http://gladiaholdings.com/PHP/fetchEvent.php";

        StringRequest stringRequest =  new StringRequest(Request.Method.POST, urlUpload, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    LinearLayout root = view.findViewById(R.id.root);
                    JSONObject jsonObject = new JSONObject(response);
                    imgPath = jsonObject.getString("poza");
                    title = jsonObject.getString("title");

                    data = jsonObject.getString("data");
                    oraStart = jsonObject.getString("oraStart");
                    oraEnd = jsonObject.getString("oraEnd");
                    tematica = jsonObject.getString("tematica");
                    pozaArtist = jsonObject.getString("pozaArtist");
                    numeArtist = jsonObject.getString("numeArtist");
                    genuriMuzicale = jsonObject.getString("genuriMuzicale");
                    descriere = jsonObject.getString("descriere");
                    tinuta = jsonObject.getString("tinuta");
                    mancare = jsonObject.getInt("mancare");
                    lat = jsonObject.getDouble("lat");
                    lng = jsonObject.getDouble("lng");

                    if(mancare == 1)
                        pretMancareXml.setText(String.valueOf(jsonObject.getDouble("pretMancare")));
                    else
                        root.getChildAt(8).setVisibility(GONE);

                    bautura = jsonObject.getInt("bautura");
                    if(bautura == 1)
                        pretBauturaXml.setText(String.valueOf(jsonObject.getDouble("pretBautura")));
                    else
                        root.getChildAt(9).setVisibility(GONE);

                    if(pretBilet != 0.0)
                        pretBiletXml.setText(String.valueOf(jsonObject.getDouble("pretBilet")));
                    else
                        root.getChildAt(10).setVisibility(GONE);

                    titleTV.setText(title);
                    Picasso.get().load(imgPath).into(profPic);

                    adresa.setText(jsonObject.getString("adresa"));
                    adr = adresa.getText().toString().trim();

                    dataXml.setText(data);
                    oraStartXml.setText(oraStart + "  -");
                    oraEndXml.setText(oraEnd);

                    if(tematica.length() != 0)
                        tematicaXml.setText(tematica);
                    else
                        root.getChildAt(3).setVisibility(GONE);


                    if(numeArtist.length() != 0)
                        numeArtistXml.setText(numeArtist);
                    else
                        root.getChildAt(4).setVisibility(GONE);

                    final Transformation transformation = new MaskTransformation(getContext(), R.drawable.circle);
                    Picasso.get().load(pozaArtist).transform(transformation).into(artistPic);


                    genuriMuzicale = genuriMuzicale.replace("#",", ");
                    genuriMuzicale = genuriMuzicale.substring(1);
                    genuriMuzicaleXml.setText(genuriMuzicale);

                    if(descriere.length() != 0)
                        descriereXml.setText(descriere);
                    else
                        root.getChildAt(6).setVisibility(GONE);


                    if(tinuta.length() != 0)
                        tinutaXml.setText(tinuta);
                    else
                        root.getChildAt(7).setVisibility(GONE);

                    a.add(adr);
                    a.add(data);
                    a.add(oraStart + " - " + oraEnd);
                    Stats.setUp(a);


                    loading.dismiss();
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Error loading your event" + response, Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Check your internet connection and try again.", Toast.LENGTH_SHORT).show();
                loading.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                int ID = PrevizEventMain.getID();
                params.put("ID", String.valueOf(ID));
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(stringRequest);
    }

    private void sendDataToServer(){
        String urlUpload = "http://gladiaholdings.com/PHP/updateEventPetreceri.php";

        StringRequest stringRequest =  new StringRequest(Request.Method.POST, urlUpload, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(getContext(), jsonObject.getString("mesaj"), Toast.LENGTH_SHORT).show();

                    loading.dismiss();
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Error loading your event" + response, Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Check your internet connection and try again.", Toast.LENGTH_SHORT).show();
                loading.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                LinearLayout root = view.findViewById(R.id.root);

                params.put("no",root.getChildCount() + "");
                for (int i = 1; i < root.getChildCount(); i++) {
                    ConstraintLayout c = (ConstraintLayout) root.getChildAt(i);
                    EditText e = (EditText) c.getChildAt(2);
                    params.put("val_" + i, e.getText().toString().trim());
                }
                params.put("x",String.valueOf(SelectLocation.getLat()));
                params.put("y",String.valueOf(SelectLocation.getLng()));
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(stringRequest);
        createSendingDialog();
    }

    private void timePikers(){
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);

        dataXml.setFocusable(false);
        dataXml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        String mnt = "" + (month + 1), dy = "" + day;
                        if((month + 1) < 10)
                            mnt = "0" + (month + 1);
                        if(day < 10)
                            dy = "0" + day;
                        String date = dy + "/" + mnt + "/" + year;
                        dataXml.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        oraStartXml.setFocusable(false);
        oraStartXml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editmode) {
                    TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            String min = String.valueOf(minute);
                            String h = String.valueOf(hourOfDay);
                            if(hourOfDay < 10)
                                h = "0" + h;
                            if(minute < 10)
                                min = "0" + min;
                            String time = h + ":" + min + "  -";
                            oraStartXml.setText(time);
                        }
                    }, hour, minute, android.text.format.DateFormat.is24HourFormat(getContext()));
                    timePickerDialog.show();
                }
            }
        });

        oraEndXml.setFocusable(false);
        oraEndXml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editmode) {
                    TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            String min = String.valueOf(minute);
                            String h = String.valueOf(hourOfDay);
                            if(hourOfDay < 10)
                                h = "0" + h;
                            if(minute < 10)
                                min = "0" + min;
                            String time = h + ":" + min;
                            oraEndXml.setText(time);
                        }
                    }, hour, minute, android.text.format.DateFormat.is24HourFormat(getContext()));
                    timePickerDialog.show();
                }
            }
        });
    }

    public static double getLat() {
        return lat;
    }

    public static double getLng() {
        return lng;
    }

    public static String getTitle(){
        return title;
    }

    public static String getImgPath() {
        return imgPath;
    }
}
