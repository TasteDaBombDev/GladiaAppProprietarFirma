package com.example.app.userScreen.profile.petreceriFiles;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.app.R;

public class PetreceriPage2 extends Fragment {
    @SuppressLint("StaticFieldLeak")
    private static PetreceriPage2 INSTANCE = null;

    private View view;


    public PetreceriPage2(){
    }

    public static PetreceriPage2 getINSTANCE(){
        if (INSTANCE == null)
            INSTANCE = new PetreceriPage2();
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
        view = inflater.inflate(R.layout.petreceri_page2,container,false);

        return view;
    }

}
