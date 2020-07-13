package com.example.app.userScreen.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.MainActivity;
import com.example.app.R;
import com.example.app.SplashScreen;
import com.example.app.userScreen.ListEvents;
import com.example.app.userScreen.MainScreen;
import com.example.app.userScreen.events.Evenimente;
import com.example.app.userScreen.events.petreceri.PetreceriPage1;
import com.example.app.userScreen.events.petreceri.PetreceriPage2;
import com.example.app.userScreen.events.petreceri.PetreceriPage3;
import com.example.app.userScreen.events.petreceri.PetreceriPage4;
import com.example.app.userScreen.events.petreceri.PetreceriPage5;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class ProfileOrganisation extends Fragment {

    private ImageButton logout;
    private View view;
    private ImageView profilePic;
    private TextView name, adress, descriere;

    public ProfileOrganisation(){
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile_organisation, container, false);
        profilePic = view.findViewById(R.id.profilePic);
        Picasso.get().load(MainScreen.getPozaPath()).into(profilePic);

        name = view.findViewById(R.id.name);
        name.setText(MainScreen.getNume());

        adress = view.findViewById(R.id.adress);
        adress.setText(MainScreen.getAdresa());

        descriere = view.findViewById(R.id.descriereFirma);
        descriere.setText(MainScreen.getDescriere());

        logout = view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                File fdelete = new File(SplashScreen.getPath());
                if (fdelete.exists()) {
                    boolean t = fdelete.delete();
                    if(t)
                    {
                        ListEvents.deleteAll();
                        PetreceriPage1.resetINSTANCE();
                        PetreceriPage2.resetINSTANCE();
                        PetreceriPage3.resetINSTANCE();
                        PetreceriPage4.resetINSTANCE();
                        PetreceriPage5.resetINSTANCE();
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(getContext(),"Failed to logout",Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }
}
