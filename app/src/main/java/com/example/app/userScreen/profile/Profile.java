package com.example.app.userScreen.profile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.app.MainActivity;
import com.example.app.R;
import com.example.app.userScreen.ListEvents;
import com.example.app.userScreen.MainScreen;
import com.example.app.userScreen.events.Evenimente;
import com.squareup.picasso.Transformation;

import java.io.File;

import jp.wasabeef.picasso.transformations.MaskTransformation;

public class Profile extends Fragment {


    private static Profile INSTANCE = null;
    private LinearLayout logout;
    private View view;
    private ImageView profilePic;
    private TextView name, adress, email, tema, decor, muzica, dresscode;

    public Profile(){
    }

    public static Profile getINSTANCE(){
        if (INSTANCE == null)
            INSTANCE = new Profile();
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
        view = inflater.inflate(R.layout.user_screen_dashboard,container,false);

        logout = view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                File fdelete = new File(MainScreen.getPath());
                if (fdelete.exists()) {
                    boolean t = fdelete.delete();
                    if(t)
                    {
                        Profile.resetINSTANCE();
                        Evenimente.resetINSTANCE();
                        ListEvents.resetINSTANCE();
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(getContext(),"Failed to logout",Toast.LENGTH_LONG).show();
                }
            }
        });

        init();

        final Transformation transformation = new MaskTransformation(getContext(), R.drawable.circle);
//        Picasso.get().load(MainScreen.getPath()).transform(transformation).into(profilePic);

        name.setText(MainScreen.getNume());
        adress.setText(MainScreen.getAdresa());
        email.setText(MainScreen.getMail());
        tema.setText(MainScreen.getTema());
        dresscode.setText(MainScreen.getDressCode());
        muzica.setText(MainScreen.getMuzica());
        decor.setText(MainScreen.getDecor());

        return view;
    }

    private void init(){
        profilePic = view.findViewById(R.id.profilePic);
        name = view.findViewById(R.id.name);
        adress = view.findViewById(R.id.adress);
        email = view.findViewById(R.id.mail);
        tema = view.findViewById(R.id.tema);
        muzica = view.findViewById(R.id.muzica);
        dresscode = view.findViewById(R.id.desscode);
        decor = view.findViewById(R.id.decor);
    }
}
