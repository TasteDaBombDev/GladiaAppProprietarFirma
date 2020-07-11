package com.example.app.registerFirma;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.app.R;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class Register4 extends Fragment {

    class Item{
        String felMancare;
        Double pret;

        public Item(String felMancare, Double pret){
            this.felMancare = felMancare;
            this.pret = pret;
        }
    }

    private static Register4 INSTANCE = null;
    private View view;
    private SwipeMenuListView menu;
    private static ArrayList<Item> items = new ArrayList<>();
    private ImageButton add;
    private EditText mancare, pret;
    private Adapter adapter;

    public Register4(){
    }

    public static Register4 getINSTANCE(){
        if (INSTANCE == null)
            INSTANCE = new Register4();
        return INSTANCE;
    }

    public static void resetINSTANCE(){
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
        view = inflater.inflate(R.layout.register4, container, false);
        menu = view.findViewById(R.id.menu);

        adapter = new Adapter(getContext(), new ArrayList<String>(), items);
        menu.setAdapter(adapter);


//        SwipeMenuCreator creator = new SwipeMenuCreator() {
//            @Override
//            public void create(SwipeMenu menu) {
//                SwipeMenuItem deleteItem = new SwipeMenuItem(getContext());
//                deleteItem.setBackground(R.drawable.circle);
//                deleteItem.setWidth(300);
//                deleteItem.setIcon(R.drawable.ic_delete_black_24dp);
//                menu.addMenuItem(deleteItem);
//            }
//        };
//
//        menu.setMenuCreator(creator);
        menu.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        menu.smoothCloseMenu();
        menu.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                menu.smoothOpenMenu(position);
            }

            @Override
            public void onSwipeEnd(int position) {

            }
        });

        menu.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                if (index == 0) {
                    items.remove(position);
                }
                return false;
            }
        });

        mancare = view.findViewById(R.id.name);
        pret = view.findViewById(R.id.pret);
        add = view.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.add(new Item(mancare.getText().toString().trim(), Double.valueOf(pret.getText().toString().trim())));
                mancare.setText("");
                pret.setText("");
                adapter = null;
                ArrayList<String> names = new ArrayList<>();
                for (int i = 0; i < items.size(); i++) {
                    names.add(items.get(i).felMancare);
                }
                adapter = new Adapter(getContext(), names,items);
                menu.setAdapter(adapter);
            }
        });
        
        
        
        
        return view;
    }

    class Adapter extends ArrayAdapter<String> {

        Context context;
        ArrayList<Item> fel;
        TextView felMancare, pret;

        Adapter(Context c, ArrayList<String> names, ArrayList<Item> fel){
            super(c, R.layout.event_item,R.id.eventName, names);
            this.context = c;
            this.fel = fel;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            @SuppressLint("ViewHolder") View item = layoutInflater.inflate(R.layout.menu_item,parent,false);
            felMancare = item.findViewById(R.id.nume);
            pret = item.findViewById(R.id.pret);

            felMancare.setText(fel.get(position).felMancare);
            pret.setText(String.valueOf(fel.get(position).pret));

            return item;
        }
    }

    public static String getItems() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < items.size(); i++)
            s.append(items.get(i).felMancare).append(": ").append(items.get(i).pret).append(", ");
        return s.toString();
    }

    public static void clear(){
        items.clear();
    }
}
