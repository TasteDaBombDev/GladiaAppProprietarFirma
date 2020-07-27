package com.example.app.userScreen.createEvents.concerte;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.app.R;
import com.wefika.flowlayout.FlowLayout;

import java.util.ArrayList;

public class ConcertePage4 extends Fragment {

    private static ConcertePage4 INSTANCE = null;
    private static FlowLayout doriane;
    private static String searchKey = "";
    private static TextView genuriMuzicale;
    private static ArrayList<String> displaymusic;
    private static boolean[] toggle;

    private View view;

    public ConcertePage4(){
    }

    public static ConcertePage4 getINSTANCE(){
        if (INSTANCE == null)
            INSTANCE = new ConcertePage4();
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
        view = inflater.inflate(R.layout.concerte_page4,container,false);
        init();
        displaymusic = new ArrayList<>();
        toggle = new boolean[doriane.getChildCount()];
        for (int i = 0; i < toggle.length; i++) {
            toggle[i] = false;
            doriane.getChildAt(i).setBackgroundResource(R.drawable.circle);
            Button btn = (Button) doriane.getChildAt(i);
            btn.setTextColor(Color.WHITE);
        }

        for (int i = 0; i < doriane.getChildCount() ; i++) {
            final Button child = (Button) doriane.getChildAt(i);
            final int finalI = i;
            child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!toggle[finalI]) {
                        child.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.orange));
                        toggle[finalI] = true;
                        searchKey = searchKey + "#" + child.getText();
                        displaymusic.add((String) child.getText());
                    }
                    else {
                        child.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.colorPrimary));
                        toggle[finalI] = false;
                        searchKey = searchKey.replace("#" + child.getText(),"");
                        displaymusic.remove(child.getText());
                    }

                    String s = "";
                    for (int j = 0; j < displaymusic.size(); j++) {
                        s = s + displaymusic.get(j) + ", ";
                    }
                    //s = s + displaymusic.get(displaymusic.size() - 1 );
                    if ( !s.isEmpty() ){
                        s = s.substring(0, s.length() - 2);
                    }
                    genuriMuzicale.setText(s);


                }
            });
        }


        return view;
    }

    private void init(){
        doriane = view.findViewById(R.id.doriane);
        genuriMuzicale = view.findViewById(R.id.genuriMuzicale);
    }

    public static String getGenuri(){
        return searchKey;
    }

    public static void reset(){
        genuriMuzicale.setText("");
        displaymusic.clear();
        for (int i = 0; i < toggle.length; i++) {
            toggle[i] = false;
        }
    }
    public static FlowLayout getDoriane(){
        return doriane;
    }
}
