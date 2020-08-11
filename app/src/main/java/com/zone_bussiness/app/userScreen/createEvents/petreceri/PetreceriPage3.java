package com.zone_bussiness.app.userScreen.createEvents.petreceri;

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

import com.zone_bussiness.app.R;
import com.wefika.flowlayout.FlowLayout;

import java.util.ArrayList;
import java.util.Arrays;

public class PetreceriPage3 extends Fragment {

    private static PetreceriPage3 INSTANCE = null;
    private static FlowLayout doriane;
    private static String searchKey = "";
    private static TextView genuriMuzicale;
    private static ArrayList<String> displaymusic;
    private static boolean[] toggle;

    private View view;

    public PetreceriPage3(){
    }

    public static PetreceriPage3 getINSTANCE(){
        if (INSTANCE == null)
            INSTANCE = new PetreceriPage3();
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
        view = inflater.inflate(R.layout.petreceri_page3,container,false);
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
                        displaymusic.add((String) child.getText());
                    }
                    else {
                        child.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.colorPrimary));
                        toggle[finalI] = false;
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
        return displaymusic.toString();
    }

    public static void reset(){
        genuriMuzicale.setText("");
        displaymusic.clear();
        Arrays.fill(toggle, false);
    }
    public static FlowLayout getDoriane(){
        return doriane;
    }
}
