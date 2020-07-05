package com.example.app.userScreen;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.app.R;
import com.example.app.userScreen.previzEvent.PrevizEvent;
import com.example.app.userScreen.previzEvent.PrevizEventMain;
import com.example.app.utils.EventDetails;
import com.example.app.utils.GladiaReciclerAdapter;
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
    private static RecyclerView eventsListing;
    private RecyclerView.Adapter reciclerAdapter;
    private RecyclerView.LayoutManager reciclerLayoutManager;
    private View view;
    private static ArrayList<EventDetails> eventsInfo = new ArrayList<>();
    private static SwipeRefreshLayout swipeRefresh;
    private static SwipeRefreshLayout.OnRefreshListener swipeRefreshListner;

    public ListEvents(){
    }

    public static ListEvents getINSTANCE(){
        if (INSTANCE == null)
            INSTANCE = new ListEvents();
        return INSTANCE;
    }

    public static void resetINSTANCE(){
        eventsInfo.clear();
        INSTANCE = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.list_events2, container, false);
        serverRun(true);
        eventsListing = view.findViewById(R.id.myEvents);
        eventsListing.setHasFixedSize(true);
        setUpRecicler();

        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        swipeRefresh.setEnabled(false);
        swipeRefreshListner = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                eventsInfo.clear();
                serverRun(false);
                swipeRefresh.setRefreshing(false);
            }
        };
        swipeRefresh.setOnRefreshListener(swipeRefreshListner);

        return view;
    }

    private void serverRun(final boolean firstTime){
        String urlUpload = "http://gladiaholdings.com/PHP/findMyEvents.php";

        StringRequest stringRequest =  new StringRequest(Request.Method.POST, urlUpload, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getBoolean("existance")){
                        for (int i = 0; i < (jsonObject.length() / 5); i++) {
                            eventsInfo.add(new EventDetails(
                                    jsonObject.getInt("id" + i),
                                    jsonObject.getString("poza" + i),
                                    jsonObject.getString("nume" + i),
                                    jsonObject.getString("date" + i),
                                    jsonObject.getString("hours" + i))
                            );
                        }
                    }
                    if (!firstTime)
                    {
                        reciclerAdapter = null;
                        reciclerLayoutManager = null;
                    }

                    reciclerLayoutManager = new LinearLayoutManager(getContext());
                    reciclerAdapter = new GladiaReciclerAdapter(eventsInfo);

                    eventsListing.setLayoutManager(reciclerLayoutManager);
                    eventsListing.setAdapter(reciclerAdapter);
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Error loading your events" + response, Toast.LENGTH_LONG).show();
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

    private void deleteFromServer(final int id){
        String urlUpload = "http://gladiaholdings.com/PHP/deleteEvent.php";

        StringRequest stringRequest =  new StringRequest(Request.Method.POST, urlUpload, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getBoolean("success"))
                    {
                        eventsInfo.clear();
                        serverRun(false);
                    }
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Error deleting your event", Toast.LENGTH_LONG).show();
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
                params.put("pID",String.valueOf(id));
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(stringRequest);
    }

    public static void refresh(){
        swipeRefreshListner.onRefresh();
    }

    public void setUpRecicler(){
//        eventsListing.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//            }
//        });





        AnimationSet set = new AnimationSet(true);
        Animation animation = new TranslateAnimation(200.0f,0.0f,10.0f,0.0f);
        Animation animation1 = new AlphaAnimation(0.0f,1.0f);
        animation1.setDuration(500);
        animation.setDuration(500);
        set.addAnimation(animation);
        set.addAnimation(animation1);
        LayoutAnimationController controller = new LayoutAnimationController(set, 0.2f);
        eventsListing.setLayoutAnimation(controller);
    }


}
