package com.example.app.userScreen.events.previzEvent.previzEventConcert;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app.R;
import com.example.app.userScreen.MainScreen;
import com.example.app.userScreen.createEvents.petreceri.SelectLocation;
import com.example.app.userScreen.events.previzEvent.EnumArtists;
import com.example.app.userScreen.events.previzEvent.PrevizEventMain;
import com.example.app.userScreen.events.previzEvent.Stats;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import jp.wasabeef.picasso.transformations.MaskTransformation;

import static android.view.View.GONE;

public class PrevizEventConcert extends Fragment{


    private static PrevizEventConcert INSTANCE = null;
    private View view;
    private static ArrayList<String> a = new ArrayList<>();

    private static ProgressDialog loading;
    private static String imgPath, title;
    private ImageView profPic;
    private static EditText titleTV,adresa, dataXml, oraStartXml, oraEndXml, genuriMuzicaleXml, descriereXml, pretMancareXml, pretBauturaXml, pretBiletXml;
    private LinearLayout repertoriuRoot;

    private String data = "", oraStart = "", oraEnd = "", repertoriu = "", pozaArtist = "", numeArtist = "", genuriMuzicale = "", descriere = "", tinuta = "", adr = "";
    private int mancare = 0, bautura = 0;
    private static double pretMancare, pretBautura, pretBilet, lat, lng;
    private boolean editmode = false;
    private String[] VIEWS = new String[15];
    private Integer[] permisiuni = new Integer[15];

    public PrevizEventConcert(){
        Arrays.fill(VIEWS, "");
        Arrays.fill(permisiuni, 0);
        permisiuni[1] = 1;
        permisiuni[2] = 1;
    }

    public static PrevizEventConcert getINSTANCE(){
        if(INSTANCE == null)
            INSTANCE = new PrevizEventConcert();
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
        view = inflater.inflate(R.layout.previz_event_concert,container,false);
        
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
                    i.putExtra("redirectedPage",-3);
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
        for (int i = 4; i < root.getChildCount(); i++) {
            if(i != 5){
                final ConstraintLayout c = (ConstraintLayout) root.getChildAt(i);
                final CheckBox cb = (CheckBox) c.getChildAt(0);

                final int finalI = i;
                cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        EditText e = (EditText) c.getChildAt(2);
                        LinearLayout root = view.findViewById(R.id.root);
                        createVIEWS(root);

                        if(!e.getText().toString().equals(""))
                            if(isChecked){
                                a.add(VIEWS[finalI]);
                                permisiuni[finalI] = 1;
//                            nowChecket ++;
                            }
                            else {
                                a.remove(VIEWS[finalI]);
                                permisiuni[finalI] = 0;
//                            nowChecket --;
                            }
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
            Stats.setUp(a,getContext());
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
        repertoriuRoot = view.findViewById(R.id.repertoriuRoot);
        genuriMuzicaleXml = view.findViewById(R.id.genuriMuzicaleXml);
        descriereXml = view.findViewById(R.id.descriereXml);
        pretMancareXml = view.findViewById(R.id.pretMancareXml);
        pretBauturaXml = view.findViewById(R.id. pretBauturaXml);
        pretBiletXml = view.findViewById(R.id.pretBiletXml);
    }

    private void disable_content(){
        LinearLayout root = view.findViewById(R.id.root);
        for (int i = 1; i < root.getChildCount(); i++) {
            if(i != 3 && i != 5){
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
    }

    private void enable_content(){
        LinearLayout root = view.findViewById(R.id.root);
        for (int i = 1; i < root.getChildCount(); i++) {
            if(i != 3 && i != 5){
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
    }

    public static void setAdr(String adrss, double latitudine, double longitudine){
        adresa.setText(adrss);
        lat = latitudine;
        lng = longitudine;
    }

    private void display(){
        LinearLayout root = view.findViewById(R.id.root);
        if(editmode)
            for (int i = 3; i < root.getChildCount(); i++) {
                if(i != 5 && i != 3){
                    ConstraintLayout child = (ConstraintLayout) root.getChildAt(i);
                    child.setVisibility(View.VISIBLE);
                    CheckBox ck = (CheckBox) child.getChildAt(0);
                    ck.setVisibility(View.VISIBLE);
                }
            }
        else
            for (int i = 3; i < root.getChildCount(); i++) {
                if(i != 5 && i != 3) {
                    ConstraintLayout child = (ConstraintLayout) root.getChildAt(i);
                    CheckBox ck = (CheckBox) child.getChildAt(0);
                    ck.setVisibility(View.INVISIBLE);
                    EditText t = (EditText) child.getChildAt(2);
                    if (String.valueOf(t.getText()).length() == 0)
                        child.setVisibility(GONE);
                } else {
                    if(i == 3){
                        if(numeArtist.equals(""))
                            root.getChildAt(3).setVisibility(GONE);
                    } else {
                        if(repertoriu.equals(""))
                            root.getChildAt(5).setVisibility(GONE);
                    }
                }
            }
    }

    private void fetchData(){
        String urlUpload = "http://gladiaholdings.com/PHP/firma/getEvent/fetchEventConcerte.php";

        StringRequest stringRequest =  new StringRequest(Request.Method.POST, urlUpload, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    LinearLayout root = view.findViewById(R.id.root);
                    JSONObject jsonObject = new JSONObject(response);

                    constructInterface(jsonObject, root);
                    createVIEWS(root);

                    a.add(title);
                    a.add(imgPath);
                    a.add(VIEWS[1]);
                    a.add(VIEWS[2]);
                    String permis = jsonObject.getString("permisiuni");
                    for (int i = 4; i < root.getChildCount(); i++) {
                        ConstraintLayout c = (ConstraintLayout) root.getChildAt(i);
                        CheckBox cb = (CheckBox) c.getChildAt(0);
                        if (permis.charAt(i) == '1')
                            cb.setChecked(true);
                    }
                    Stats.setUp(a, getContext());


                    loading.dismiss();
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Error loading your event" + response, Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    loading.dismiss();
                    Log.e("err", response);
                    getActivity().onBackPressed();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Check your internet connection and try again.", Toast.LENGTH_SHORT).show();
                loading.dismiss();
                getActivity().onBackPressed();
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
        String urlUpload = "http://gladiaholdings.com/PHP/firma/updateEvent/updateEventConcerte.php";

        StringRequest stringRequest =  new StringRequest(Request.Method.POST, urlUpload, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    LinearLayout root = view.findViewById(R.id.root);
                    createVIEWS(root);

                    loading.dismiss();
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Error loading your event" + response, Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    loading.dismiss();
                    getActivity().onBackPressed();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Check your internet connection and try again.", Toast.LENGTH_SHORT).show();
                loading.dismiss();
                getActivity().onBackPressed();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("ID", String.valueOf(PrevizEventMain.getID()));
                params.put("title", titleTV.getText().toString().trim());
                params.put("adresa", adresa.getText().toString().trim());
                params.put("data", dataXml.getText().toString().trim());
                String oraS = oraStartXml.getText().toString().trim().replace("-", "").trim();
                params.put("oraStart", oraS);
                params.put("oraEnd", oraEndXml.getText().toString().trim());
                params.put("descriere", descriereXml.getText().toString().trim());
                params.put("genuriMuzicale", genuriMuzicaleXml.getText().toString().trim());
                if(pretBauturaXml.getText().toString().trim().length() != 0) {
                    params.put("bautura", "1");
                    params.put("pretBautura", pretBauturaXml.getText().toString().trim());
                } else {
                    params.put("bautura", "0");
                    params.put("pretBautura", "");
                }
                if(pretMancareXml.getText().toString().trim().length() != 0) {
                    params.put("mancare", "1");
                    params.put("pretMancare", pretMancareXml.getText().toString().trim());
                } else {
                    params.put("mancare", "0");
                    params.put("pretMancare", "");
                }
                params.put("pretBilet", pretBauturaXml.getText().toString().trim());
                params.put("IDorganizator", String.valueOf(MainScreen.getUserID()));
                StringBuilder t = new StringBuilder();
                for (int i = 0; i < permisiuni.length; i++) {
                    if (permisiuni[i] == 0)
                        t.append("0");
                    else t.append("1");
                }
                params.put("permisiuni", t.toString());
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

    @SuppressLint("SetTextI18n")
    private void constructInterface(JSONObject jsonObject, LinearLayout root) throws JSONException{
        imgPath = jsonObject.getString("poza");
        title = jsonObject.getString("numeEvent");

        data = jsonObject.getString("data");
        oraStart = jsonObject.getString("oraStart");
        oraEnd = jsonObject.getString("oraEnd");
        pozaArtist = jsonObject.getString("pozaArtist");
        numeArtist = jsonObject.getString("numeArtist");
        genuriMuzicale = jsonObject.getString("genuriMuzicale");
        descriere = jsonObject.getString("descriere");
        repertoriu = jsonObject.getString("repertoriu");
        mancare = jsonObject.getInt("mancare");
        lat = jsonObject.getDouble("latitudine");
        lng = jsonObject.getDouble("longitudine");
        Stats.goToLoc(getContext(),lat,lng);

        /*

        Construct INTERFACE ---->

         */

        titleTV.setText(title);
        Picasso.get().load(imgPath).into(profPic);

        adresa.setText(jsonObject.getString("adresa"));
        adr = adresa.getText().toString().trim();

        dataXml.setText(data);
        oraStartXml.setText(oraStart + "  -");
        oraEndXml.setText(oraEnd);

        if(numeArtist.length() != 0){
            String[] lists = numeArtist.split("\\|");
            String[] pics = pozaArtist.split(" | ");
            ArrayList<String> paths = new ArrayList<>();
            ArrayList<String> names = new ArrayList<>();
            ArrayList<String> descriere = new ArrayList<>();

            for (int i = 0; i < lists.length - 1; i++) {
                String[] p = lists[i].split("#");
                names.add(p[0].trim());
                descriere.add(p[1].trim());
            }
            for (int i = 2; i < pics.length; i+=2) {
                paths.add(pics[i]);
            }

            ViewPager enumArtisti = view.findViewById(R.id.enumArtisti);
            EnumArtists enumArtists = new EnumArtists(getFragmentManager(), paths, names, descriere);
            enumArtisti.setAdapter(enumArtists);
        } else root.getChildAt(3).setVisibility(GONE);


        if(genuriMuzicale.length() != 2) {
            genuriMuzicale = genuriMuzicale.replace("[", "");
            genuriMuzicale = genuriMuzicale.replace("]", "");
            genuriMuzicaleXml.setText(genuriMuzicale);
        } else root.getChildAt(4).setVisibility(GONE);

        if(repertoriu.length() != 0) {
            String[] repertorii = repertoriu.split("\\|");
            for (String s : repertorii) {
                TextView t = new TextView(getContext());
                t.setText(s.trim());
                repertoriuRoot.addView(t);
            }
        } else root.getChildAt(5).setVisibility(GONE);


        if(descriere.length() != 0)
            descriereXml.setText(descriere);
        else
            root.getChildAt(6).setVisibility(GONE);

        if(mancare == 1)
            pretMancareXml.setText(String.valueOf(jsonObject.getDouble("pretMancare")));
        else
            root.getChildAt(7).setVisibility(GONE);

        bautura = jsonObject.getInt("bautura");
        if(bautura == 1)
            pretBauturaXml.setText(String.valueOf(jsonObject.getDouble("pretBautura")));
        else
            root.getChildAt(8).setVisibility(GONE);

        if(pretBilet != 0.0)
            pretBiletXml.setText(String.valueOf(jsonObject.getDouble("pretBilet")));
        else
            root.getChildAt(9).setVisibility(GONE);
    }

    private void createVIEWS(LinearLayout root){
        for (int i = 1; i < root.getChildCount(); i++) {
            if(i != 3 && i != 5){
                ConstraintLayout c = (ConstraintLayout) root.getChildAt(i);
                EditText e = (EditText) c.getChildAt(2);
                if(i == 2) {
                    EditText oraStartData = (EditText) c.getChildAt(3);
                    EditText oraEndData = (EditText) c.getChildAt(4);
                    TextView ttv = (TextView) c.getChildAt(5);
                    VIEWS[i] = ttv.getText().toString().trim() + "\n" + e.getText().toString().trim() + "\n" + oraStartData.getText().toString().trim() + ": " +  oraEndData.getText().toString().trim();
                } else{
                    TextView ttv = (TextView) c.getChildAt(3);
                    VIEWS[i] = ttv.getText().toString().trim() + ": " + e.getText().toString().trim();
                }
            } else VIEWS[i] = "";
        }
    }

    public static ArrayList<String> getA() {
        return a;
    }
}
