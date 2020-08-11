package com.zone_bussiness.app.userScreen.events.previzEvent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.zone_bussiness.app.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import jp.wasabeef.picasso.transformations.MaskTransformation;

public class PageArtist extends Fragment {

    private ImageView img;
    private TextView nameAfis, descriere;
    private View view;
    private static PageArtist INSTANCE = null;
    private String path, name, descreie;

    public PageArtist(String path, String name, String descriere){
        this.path = path;
        this.name = name;
        this.descreie = descriere;
    }

    public static PageArtist getINSTANCE(String path, String name, String descriere){
        if(INSTANCE == null)
            return new PageArtist(path, name, descriere);
        return INSTANCE;
    }

    public static void resetINSTANCE(){
        INSTANCE = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.artist_page_vernisaj, container, false);

        img = view.findViewById(R.id.artistPicItem2);
        nameAfis = view.findViewById(R.id.artistNameItem);
        descriere = view.findViewById(R.id.descriereItem);

        final Transformation transformation = new MaskTransformation(getContext(), R.drawable.circle);
        Picasso.get().load(path).placeholder(R.drawable.nopic_round).error(R.drawable.nopic_round).transform(transformation).into(img);
        nameAfis.setText(name);
        descriere.setText(descreie);

        return view;
    }


}
