package com.example.app.registerFirma;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.app.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegsiterDescriereOrganizatie extends Fragment {

    private static RegsiterDescriereOrganizatie INSTANCE = null;
    private View view;
    private static TextInputEditText descriere;

    public RegsiterDescriereOrganizatie(){
        
    }

    public static RegsiterDescriereOrganizatie getINSTANCE(){
        if (INSTANCE == null)
            INSTANCE = new RegsiterDescriereOrganizatie();
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
        view = inflater.inflate(R.layout.regsiter_descriere_organizatie, container, false);
        descriere = view.findViewById(R.id.descriereFirma);
        return view;
    }

    public static String getDescriere() {
        return descriere.getText().toString().trim();
    }
}
