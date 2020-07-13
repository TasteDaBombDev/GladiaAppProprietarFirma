package com.example.app.userScreen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.example.app.userScreen.previzEvent.PrevizEventMain;
import com.example.app.utils.EventDetails;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class ListEvents extends Fragment {

    @SuppressLint("StaticFieldLeak")
    private static SwipeMenuListView eventsListing;
    private static Adapter adapter;
    private View view;
    private static ArrayList<EventDetails> eventsInfo = new ArrayList<>();
    private static SwipeRefreshLayout swipeRefresh;
    private static SwipeRefreshLayout.OnRefreshListener swipeRefreshListner;

    public ListEvents(){
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.list_events, container, false);
        serverRun(true);
        eventsListing = view.findViewById(R.id.myEvents);

        SwipeMenuCreator creator = new SwipeMenuCreator() {
                @Override
                public void create(SwipeMenu menu) {
                    SwipeMenuItem deleteItem = new SwipeMenuItem(getContext());
                    deleteItem.setBackground(R.drawable.background_item);
                    deleteItem.setWidth(300);
                    deleteItem.setIcon(R.drawable.ic_delete_black_24dp);
                    menu.addMenuItem(deleteItem);
                }
        };

        eventsListing.setMenuCreator(creator);
        eventsListing.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        eventsListing.smoothCloseMenu();
        eventsListing.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                eventsListing.smoothOpenMenu(position);
            }

            @Override
            public void onSwipeEnd(int position) {

            }
        });

        eventsListing.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), PrevizEventMain.class);
                intent.putExtra("ID",eventsInfo.get(position).getId());
                startActivity(intent);
            }
        });

        eventsListing.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                if (index == 0) {
                    deleteFromServer(eventsInfo.get(position).getId());
                }
                return false;
            }
        });

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

    class Adapter extends ArrayAdapter<String> {

        Context context;
        ArrayList<EventDetails> event;
        TextView name, date, hour;
        ImageView image;

        Adapter(Context c, ArrayList<String> names, ArrayList<EventDetails> eventsInfo){
            super(c, R.layout.event_item,R.id.eventName, names);
            this.context = c;
            this.event = eventsInfo;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            @SuppressLint("ViewHolder") View item = layoutInflater.inflate(R.layout.event_item,parent,false);
            name = item.findViewById(R.id.eventName);
            image = item.findViewById(R.id.eventPic);
            hour = item.findViewById(R.id.eventOra);
            date = item.findViewById(R.id.eventDate);

            name.setText(event.get(position).getName());

            Picasso.get().load(event.get(position).getImgPath()).into(image);

            hour.setText(event.get(position).getDate());
            date.setText(event.get(position).getHour());

            return item;
        }
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
//                    for (int i = 0; i < 35; i++) {
//                        eventsInfo.add(new EventDetails(i,"http://gladiaholdings.com/FILES/AFACERI/23/1342629831_1594022827.png","Titlu eveniment", "20/02/2020", "12:20 - 12:30"));
//                    }
                    ArrayList<String> name = new ArrayList<>();
                    for (int i = 0; i < eventsInfo.size(); i++) {
                        name.add(eventsInfo.get(i).getName());
                    }
                    if (!firstTime)
                        adapter = null;
                    adapter = new Adapter(getContext(),name,eventsInfo);
                    eventsListing.setAdapter(adapter);
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
                params.put("userID", String.valueOf(MainScreen.getUserID()));
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

    public static void deleteAll(){
        adapter = null;
        eventsInfo.clear();
    }


}
