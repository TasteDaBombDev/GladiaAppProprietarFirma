package com.example.app.userScreen.createEvents.vernisaje;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;

import com.example.app.R;
import com.example.app.utils.Artist;
import com.google.android.material.textfield.TextInputEditText;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class VernisajPage3 extends Fragment {

    private static VernisajPage3 INSTANCE = null;
    private View view;

    private ImageView artistPic;
    private TextInputEditText artistNume;
    private EditText descriere;
    private static ArrayList<Artist> artists = new ArrayList<>();
    private ArrayList<String> name = new ArrayList<>();
    private ImageButton add;
    private ListView artisti;
    private static Adapter adapter;
    private Bitmap bitmap = null;

    public VernisajPage3(){
    }

    public static VernisajPage3 getINSTANCE(){
        if (INSTANCE == null)
            INSTANCE = new VernisajPage3();
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
        view = inflater.inflate(R.layout.vernisaj_page3,container,false);
        init();
        adapter = new Adapter(getContext(), name, artists);
        artisti.setAdapter(adapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bitmap != null && artistNume.getText().toString().trim().length() != 0) {
                    artists.add(0, new Artist(bitmap, artistNume.getText().toString().trim(), descriere.getText().toString().trim()));
                    name.add(0, artistNume.getText().toString().trim());
                    adapter.notifyDataSetChanged();
                    artistNume.setText("");
                    artistPic.setImageResource(R.drawable.nopic_round);
                    descriere.setText("");
                }
            }
        });

        artistPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CropImage.activity().setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .getIntent(getContext());

                startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });

        return view;
    }

    public Bitmap getBitmap(ImageView img){
        Bitmap bitmap = ((BitmapDrawable)img.getDrawable()).getBitmap();
//      Bitmap bitmap = ((BitmapDrawable)((LayerDrawable)img.getDrawable()).getDrawable(0)).getBitmap();
        return bitmap;
    }

    private void init(){
        artistPic = view.findViewById(R.id.artistPicVernisaj);
        artistNume = view.findViewById(R.id.vedetaNameVernisaj);
        descriere = view.findViewById(R.id.descriereArtistVernisaj);
        add = view.findViewById(R.id.addArtistVernisaj);
        artisti = view.findViewById(R.id.vernisajArtisti);
    }

    class Adapter extends ArrayAdapter<String> {

        Context context;
        ArrayList<Artist> artistiArray;
        ImageView pic;
        TextView name, descriere;

        Adapter(Context c, ArrayList<String> names, ArrayList<Artist> fel){
            super(c, R.layout.event_item,R.id.eventName, names);
            this.context = c;
            this.artistiArray = fel;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            @SuppressLint("ViewHolder") View item = layoutInflater.inflate(R.layout.vc_artist_item,parent,false);
            pic = item.findViewById(R.id.artistPicItem);
            name = item.findViewById(R.id.artistNameItem);
            descriere = item.findViewById(R.id.descriereItem);

            name.setText(artistiArray.get(position).getName());
            descriere.setText(artistiArray.get(position).getDescriere());
            pic.setImageBitmap(artistiArray.get(position).getImg());
            setImageRoundedAdapter();

            if(artistiArray.get(position).getDescriere().length() == 0)
                descriere.setVisibility(View.GONE);

            return item;
        }

        private void setImageRoundedAdapter(){
            Bitmap bitmap = ((BitmapDrawable)pic.getDrawable()).getBitmap();
            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(),bitmap);
            roundedBitmapDrawable.setCircular(true);
            pic.setImageDrawable(roundedBitmapDrawable);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                InputStream inputStream = null;
                try {
                    inputStream = getActivity().getApplicationContext().getContentResolver().openInputStream(resultUri);
                    bitmap = BitmapFactory.decodeStream(inputStream);

                    Bitmap scaledBitmap = scaleDown(bitmap, 600, true);
                    bitmap = scaledBitmap;

                    artistPic.setImageBitmap(bitmap);
                    setImageRounded();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(getContext(), "Please try again!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize, boolean filter) {
        float ratio = Math.min((float) maxImageSize / realImage.getWidth(), (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }

    private void setImageRounded(){
        Bitmap bitmap = ((BitmapDrawable)artistPic.getDrawable()).getBitmap();
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(),bitmap);
        roundedBitmapDrawable.setCircular(true);
        artistPic.setImageDrawable(roundedBitmapDrawable);
    }

    public static String getArtistsDetalis() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < artists.size(); i++) {
            s.append(artists.get(i).getName()).append(" # ").append(artists.get(i).getDescriere()).append(" | ");
        }
        return s.toString();
    }

    private static String imgToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] imageBytes = byteArrayOutputStream.toByteArray();

        String encodeImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodeImage;
    }

    public static String[] getArtistsPic(){

        String[] s = new String[artists.size()];

        for (int i = 0; i < artists.size(); i++) {
            s[i] = imgToString(artists.get(i).getImg());
        }
        return s;

    }

    public static void reset(){
        artists.clear();
        adapter = null;
    }
}
