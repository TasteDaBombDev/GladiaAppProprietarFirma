package com.example.app.userScreen.events.petreceri;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.app.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonElement;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

public class PetreceriPage1 extends Fragment {
    private static PetreceriPage1 INSTANCE = null;

    private View view;
    private static Bitmap bitmap;

    @SuppressLint("StaticFieldLeak")
    private static ImageView selectImg;

    @SuppressLint("StaticFieldLeak")
    private TextInputLayout titleLayout,dateLayout;
    private static TextInputEditText title,data;
    private static EditText oraStart,oraEnd;

    @SuppressLint("StaticFieldLeak")
    private static Button setLocation;

    @SuppressLint("StaticFieldLeak")
    private static TextView address;

    public PetreceriPage1(){
    }

    public static PetreceriPage1 getINSTANCE(){
        if (INSTANCE == null)
            INSTANCE = new PetreceriPage1();
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
        view = inflater.inflate(R.layout.petreceri_page1,container,false);

        init();
        setImageRounded();
        timePikers();
        focusListener();

        selectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CropImage.activity().setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .getIntent(getContext());

                startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });

        setLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),SelectLocation.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void init(){
        selectImg = view.findViewById(R.id.selectImgPetrecere);

        titleLayout = view.findViewById(R.id.petrecereTitle);
        title = view.findViewById(R.id.petrecereT);


        dateLayout = view.findViewById(R.id.petrecereData);
        data = view.findViewById(R.id.petrecereD);


        oraStart = view.findViewById(R.id.oraStart);
        oraEnd = view.findViewById(R.id.oraEnd);
        setLocation = view.findViewById(R.id.setLocation);
        address = view.findViewById(R.id.addressPlace);
    }

    private void focusListener(){
        title.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    titleLayout.setCounterEnabled(true);
                    titleLayout.setCounterMaxLength(20);
                }
                else
                {
                    titleLayout.setCounterEnabled(false);
                    titleLayout.setCounterMaxLength(0);
                }
            }
        });
    }

    private void setImageRounded(){
        Bitmap bitmap = ((BitmapDrawable)selectImg.getDrawable()).getBitmap();
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(),bitmap);
        roundedBitmapDrawable.setCircular(true);
        selectImg.setImageDrawable(roundedBitmapDrawable);
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

                    selectImg.setImageBitmap(bitmap);
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

    private void timePikers(){
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);

        data.setFocusable(false);
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        String date = String.valueOf(day) + "/" + String.valueOf(month + 1) + "/" + year;
                        data.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        oraStart.setFocusable(false);
        oraStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String min = String.valueOf(minute);
                        String h = String.valueOf(hourOfDay);
                        String time = h + ":" + min;
                        oraStart.setText(time);
                    }
                },hour,minute,android.text.format.DateFormat.is24HourFormat(getContext()));
                timePickerDialog.show();
            }
        });

        oraEnd.setFocusable(false);
        oraEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String min = String.valueOf(minute);
                        String h = String.valueOf(hourOfDay);
                        String time = h + ":" + min;
                        oraEnd.setText(time);
                    }
                },hour,minute,android.text.format.DateFormat.is24HourFormat(getContext()));
                timePickerDialog.show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public static void updateValue(){
        if(SelectLocation.getAddress() == null)
            address.setText("Adresa nestiuta, coordonatele retinute");
        else
            address.setText(SelectLocation.getAddress());
    }

    private static String imgToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] imageBytes = byteArrayOutputStream.toByteArray();

        String encodeImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodeImage;
    }

    public static String getPoza() {
        String imageData = imgToString(bitmap);
        return imageData;
    }

    public static String getTitle(){
        return title.getText().toString();
    }

    public static String getData(){
        return data.getText().toString();
    }

    public static String getOraStart(){
        return oraStart.getText().toString();
    }

    public static String getOraEnd(){
        return  oraEnd.getText().toString();
    }

    public static double getLatitudine(){
        return SelectLocation.getLat();
    }

    public static double getLongitudine(){
        return SelectLocation.getLng();
    }

    public static String getAdresa(){
        return address.getText().toString();
    }

    public static void reset(){
        selectImg.setImageResource(R.drawable.nopic_round);
        title.setText("");
        data.setText("");
        oraEnd.setText("");
        oraStart.setText("");
        address.setText("");
    }
}
