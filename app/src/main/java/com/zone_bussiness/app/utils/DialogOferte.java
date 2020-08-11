package com.zone_bussiness.app.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.zone_bussiness.app.R;
import com.zone_bussiness.app.userScreen.profile.Sales;

public class DialogOferte extends AppCompatDialogFragment implements DialogInterface.OnDismissListener{

    private boolean isAdded = false;
    private TextView name, desc;
    private String type, title, descriere;
    private Integer which;

    public DialogOferte(String type, String title, String descriere, Integer which){
        this.type = type;
        this.descriere = descriere;
        this.title = title;
        this.which = which;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout_oferte, null);

        name = view.findViewById(R.id.nume);
        desc = view.findViewById(R.id.descriere);
        final Button add = view.findViewById(R.id.add);
        final Button remove = view.findViewById(R.id.remove);

        name.setText(title);
        desc.setText(descriere);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAdded = true;
                dismiss();
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        builder.setView(view).setTitle("Adauga oferta");

        return builder.create();
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isAdded){
                    if(type.equals("add")){
                        if(Sales.getOferteRoot1().getVisibility() != View.VISIBLE){
                            Sales.getTitleOferte1().setText(name.getText());
                            Sales.getDescriereOferte1().setText(desc.getText());
                            Sales.getOferteRoot1().setVisibility(View.VISIBLE);
                        } else {
                            Sales.getOfertaRoot2().setVisibility(View.VISIBLE);
                            Sales.getDescriereOferte2().setText(desc.getText());
                            Sales.getTitleOferte2().setText(name.getText());
                            Sales.getAddOferte().setVisibility(View.GONE);
                        }
                    } else {
                        if(which == 1){
                            Sales.getTitleOferte1().setText(name.getText());
                            Sales.getDescriereOferte1().setText(desc.getText());
                        } else if(which == 2) {
                            Sales.getDescriereOferte2().setText(desc.getText());
                            Sales.getTitleOferte2().setText(name.getText());
                        }
                    }
                }
            }
        },200);
    }
}
