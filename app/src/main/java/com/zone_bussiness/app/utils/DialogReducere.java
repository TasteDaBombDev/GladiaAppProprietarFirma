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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.zone_bussiness.app.R;
import com.zone_bussiness.app.userScreen.profile.Sales;

import org.w3c.dom.Text;

public class DialogReducere extends AppCompatDialogFragment implements DialogInterface.OnDismissListener{

    private boolean isAdded = false;
    private TextView name, procent;
    private String type, title, descriere;

    public DialogReducere(String type, String title, String descriere){
        this.type = type;
        this.title = title;
        this.descriere = descriere;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout_reduceri, null);

        name = view.findViewById(R.id.nume);
        procent = view.findViewById(R.id.procent);
        final Button add = view.findViewById(R.id.add);
        final Button remove = view.findViewById(R.id.remove);

        name.setText(title);
        procent.setText(descriere);

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

        builder.setView(view).setTitle("Adauga reducere");

        return builder.create();
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isAdded){
                    if(type.equals("add")){
                        Sales.getReducereName().setText(name.getText());
                        Sales.getReducereProcent().setText(procent.getText()+"%");
                        Sales.getReduceriRoot().setVisibility(View.VISIBLE);
                        Sales.getAddReducere().setVisibility(View.GONE);
                    } else {
                        Sales.getReducereName().setText(name.getText());
                        Sales.getReducereProcent().setText(procent.getText()+"%");
                    }
                }
            }
        },200);
    }
}
