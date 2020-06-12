package com.example.app.userScreen.events.petreceri;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.app.R;
import com.wefika.flowlayout.FlowLayout;

public class PetreceriPage3 extends Fragment {

    private static PetreceriPage3 INSTANCE = null;
    private FlowLayout doriane;
    private String searchKey = "";
    private TextView genuriMuzicale;

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

        final boolean[] toggle = new boolean[doriane.getChildCount()];
        for (int i = 0; i < toggle.length; i++) {
            toggle[i] = false;
        }

        for (int i = 0; i < doriane.getChildCount() ; i++) {
            final Button child = (Button) doriane.getChildAt(i);
            final int finalI = i;
            child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!toggle[finalI]) {
                        child.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.tinted));
                        toggle[finalI] = true;
                        searchKey = searchKey + "_" + child.getText();
                    }
                    else {
                        child.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.colorPrimary));
                        toggle[finalI] = false;
                        searchKey = searchKey.replace("_" + child.getText(),"");
                    }

                    genuriMuzicale.setText(searchKey);

                }
            });
        }


        return view;
    }

    private void init(){
        doriane = view.findViewById(R.id.doriane);
        genuriMuzicale = view.findViewById(R.id.genuriMuzicale);
    }
}
