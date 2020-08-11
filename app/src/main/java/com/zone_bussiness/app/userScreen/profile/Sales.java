package com.zone_bussiness.app.userScreen.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.zone_bussiness.app.R;
import com.zone_bussiness.app.utils.DialogOferte;
import com.zone_bussiness.app.utils.DialogReducere;

import java.util.Objects;

import static android.view.View.GONE;

public class Sales extends Fragment {

    private View view;
    private static TextView reducereName, reducereProcent, titleOferte1, descriereOferte1, titleOferte2, descriereOferte2;
    private ConstraintLayout root;
    private ImageButton edit, removeReducere, removeOferta1, removeOferta2;
    private static ImageButton addReducere, addOferte;
    private static CardView reduceriRoot, oferteRoot1, ofertaRoot2;
    private boolean isEditing = false;

    public Sales(){
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sales, container, false);
        init();
        removeOferta2.setVisibility(View.GONE);
        removeOferta1.setVisibility(View.GONE);
        removeReducere.setVisibility(View.GONE);

        reduceriRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogReducere d = new DialogReducere("update", reducereName.getText().toString(), reducereProcent.getText().toString().replace("%", ""));
                d.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "OFERTE UPDATE");

            }
        });

        oferteRoot1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogOferte d = new DialogOferte("update", titleOferte1.getText().toString(), descriereOferte1.getText().toString(), 1);
                d.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "OFERTE UPDATE");
            }
        });

        ofertaRoot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogOferte d = new DialogOferte("update", titleOferte2.getText().toString(), descriereOferte2.getText().toString(), 2);
                d.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "OFERTE UPDATE");
            }
        });

        removeOferta1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oferteRoot1.setVisibility(View.GONE);
                addOferte.setVisibility(View.VISIBLE);
            }
        });

        removeOferta2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ofertaRoot2.setVisibility(View.GONE);
                addOferte.setVisibility(View.VISIBLE);
            }
        });


        removeReducere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addReducere.setVisibility(View.VISIBLE);
                reduceriRoot.setVisibility(View.VISIBLE);
            }
        });

        addReducere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogReducere d = new DialogReducere("add", "", "");
                d.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "REDUCERI");
            }
        });

        addOferte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogOferte d = new DialogOferte("add", "", "", -1);
                d.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "OFERTE");
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {

            ConstraintSet set = new ConstraintSet();

            @Override
            public void onClick(View view) {
                TransitionManager.beginDelayedTransition(root);
                set.clone(root);

                if(!isEditing){

                    set.setHorizontalBias(R.id.reducereRoot, 0.1f);
                    set.setHorizontalBias(R.id.oferteRoot1, 0.1f);
                    set.setHorizontalBias(R.id.oferteRoot2, 0.1f);

                    isEditing = true;
                    if(reduceriRoot.getVisibility() == View.VISIBLE)
                        removeReducere.setVisibility(View.VISIBLE);
                    if(oferteRoot1.getVisibility() == View.VISIBLE)
                        removeOferta1.setVisibility(View.VISIBLE);
                    if(ofertaRoot2.getVisibility() == View.VISIBLE)
                        removeOferta2.setVisibility(View.VISIBLE);
                    edit.setImageResource(R.drawable.ic_check_black_24dp);

                } else {

                    set.setHorizontalBias(R.id.reducereRoot, 0.5f);
                    set.setHorizontalBias(R.id.oferteRoot1, 0.5f);
                    set.setHorizontalBias(R.id.oferteRoot2, 0.5f);

                    isEditing = false;
                    removeReducere.setVisibility(View.GONE);
                    removeOferta1.setVisibility(View.GONE);
                    removeOferta2.setVisibility(View.GONE);
                    edit.setImageResource(R.drawable.ic_edit_black_24dp);

                }

                set.applyTo(root);
            }
        });

        return view;
    }

    private void init(){
        reducereName = view.findViewById(R.id.reducereName);
        reducereProcent = view.findViewById(R.id.reducereProcent);
        root = view.findViewById(R.id.root);
        edit = view.findViewById(R.id.edit);
        reduceriRoot = view.findViewById(R.id.reducereRoot);
        removeReducere = view.findViewById(R.id.deleteReducere);
        addReducere = view.findViewById(R.id.addReducere);
        addOferte = view.findViewById(R.id.addOferte);
        ofertaRoot2 = view.findViewById(R.id.oferteRoot2);
        oferteRoot1 = view.findViewById(R.id.oferteRoot1);
        titleOferte1 = view.findViewById(R.id.titleOferta1);
        descriereOferte1 = view.findViewById(R.id.descriereOferta1);
        titleOferte2 = view.findViewById(R.id.titleOferta2);
        descriereOferte2 = view.findViewById(R.id.descriereOferta2);
        removeOferta1 = view.findViewById(R.id.removeOferta1);
        removeOferta2 = view.findViewById(R.id.removeOferta2);
    }

    public static ImageButton getAddReducere() {
        return addReducere;
    }

    public static TextView getReducereName() {
        return reducereName;
    }

    public static TextView getReducereProcent() {
        return reducereProcent;
    }

    public static CardView getReduceriRoot() {
        return reduceriRoot;
    }

    public static CardView getOfertaRoot2() {
        return ofertaRoot2;
    }

    public static TextView getDescriereOferte1() {
        return descriereOferte1;
    }

    public static CardView getOferteRoot1() {
        return oferteRoot1;
    }

    public static TextView getDescriereOferte2() {
        return descriereOferte2;
    }

    public static TextView getTitleOferte1() {
        return titleOferte1;
    }

    public static TextView getTitleOferte2() {
        return titleOferte2;
    }

    public static ImageButton getAddOferte() {
        return addOferte;
    }
}
