package com.example.app.userScreen.events.petreceri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.app.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;

public class PetreceriPage2 extends Fragment {

    @SuppressLint("StaticFieldLeak")
    private static PetreceriPage2 INSTANCE = null;
    private static ImageView vedetaPic;
    private static Bitmap bitmap;
    private static TextInputLayout tematicaLayout, vedetaNameLayout;
    private static TextInputEditText tematica, vedetaName;
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
        init();
        setImageRounded();
        focusListener();

        vedetaPic.setOnClickListener(new View.OnClickListener() {
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

    private void setImageRounded(){
        Bitmap bitmap = ((BitmapDrawable)vedetaPic.getDrawable()).getBitmap();
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(),bitmap);
        roundedBitmapDrawable.setCircular(true);
        vedetaPic.setImageDrawable(roundedBitmapDrawable);
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

                    Bitmap scaledBitmap = scaleDown(bitmap, 400, true);
                    bitmap = scaledBitmap;

                    vedetaPic.setImageBitmap(bitmap);
                    setImageRounded();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(getContext(), "Please try again!" + error, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void init(){
        vedetaPic = view.findViewById(R.id.vedetaPic);

//        vedetaNameLayout = view.findViewById(R.id.vedetaNameLayout);
        vedetaName = view.findViewById(R.id.vedetaName);

        tematicaLayout = view.findViewById(R.id.tematicaLayout);
        tematica = view.findViewById(R.id.tematica);
    }

    private void focusListener(){
        tematica.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    tematicaLayout.setCounterEnabled(true);
                    tematicaLayout.setCounterMaxLength(20);
                }
                else
                {
                    tematicaLayout.setCounterEnabled(false);
                    tematicaLayout.setCounterMaxLength(0);
                }
            }
        });
    }

    private static String imgToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] imageBytes = byteArrayOutputStream.toByteArray();

        String encodeImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodeImage;
    }

    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize, boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }

    public static String getTematica() {
        return tematica.getText().toString();
    }

    public static String getVedetaName() {
        return vedetaName.getText().toString();
    }

    public static String getVedetaPic() {
        String data = imgToString(bitmap);
        return data;
    }

    public static void reset(){
        tematica.setText("");
        vedetaName.setText("");
        vedetaPic.setImageResource(R.drawable.nopic_round);
    }
}
