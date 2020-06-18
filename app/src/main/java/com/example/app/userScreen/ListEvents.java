package com.example.app.userScreen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class ListEvents extends Fragment {

    @SuppressLint("StaticFieldLeak")
    private static ListEvents INSTANCE = null;
    private ListView eventsListing;
    private View view;
    private static Adapter adapter;
    private static ArrayList<String> names = new ArrayList<>();
    private static ArrayList<String> paths = new ArrayList<>();
    private static ArrayList<Integer> ids = new ArrayList<>();


    public ListEvents(){
    }

    public static ListEvents getINSTANCE(){
        if (INSTANCE == null)
            INSTANCE = new ListEvents();
        return INSTANCE;
    }

    public static void resetINSTANCE(){
        names.clear();
        paths.clear();
        ids.clear();
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
        view = inflater.inflate(R.layout.list_events, container, false);

        serverRun();
        eventsListing = view.findViewById(R.id.myEvents);

        eventsListing.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(),PrevizEvent.class);
                intent.putExtra("ID",ids.get(position));
                startActivity(intent);
            }
        });

        return view;
    }

    class Adapter extends ArrayAdapter<String> {

        Context context;
        ArrayList<String> names;
        ArrayList<String> paths;
        TextView name;
        ImageView image;

        Adapter(Context c, ArrayList<String> names, ArrayList<String> paths){
            super(c, R.layout.event_item,R.id.eventName, names);
            this.context = c;
            this.names = names;
            this.paths = paths;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            @SuppressLint("ViewHolder") View item = layoutInflater.inflate(R.layout.event_item,parent,false);
            name = item.findViewById(R.id.eventName);
            image = item.findViewById(R.id.eventPic);

            name.setText(names.get(position));
            Picasso.get().load(paths.get(position)).into(image);

//            setImageRounded();
            return item;
        }

        private void setImageRounded(){
            Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(),bitmap);
            roundedBitmapDrawable.setCircular(true);
            image.setImageDrawable(roundedBitmapDrawable);
        }
    }

    private void serverRun(){
        String urlUpload = "http://gladiaholdings.com/PHP/findMyEvents.php";

        StringRequest stringRequest =  new StringRequest(Request.Method.POST, urlUpload, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getBoolean("existance")){
                        for (int i = 0; i < (jsonObject.length() / 3); i++) {
                            ids.add(jsonObject.getInt("id" + i));
                            names.add(jsonObject.getString("nume" + i));
                            paths.add(jsonObject.getString("poza" + i));
                        }
                    }
                    adapter = new Adapter(getContext(), names, paths);
                    eventsListing.setAdapter(adapter);
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Error loading your events", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Connection Timeout", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userID","23");
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(stringRequest);
    }

    public static void addNames(String s){
        names.add(0,s);
    }

    public static void addPaths(String s){
        paths.add(0,s);
    }

    public static void addIds(Integer i){
        ids.add(0,i);
    }

    public static void change(){
        adapter.notifyDataSetChanged();
    }

}