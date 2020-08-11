package com.zone_bussiness.app.userScreen.createEvents.vernisaje;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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

import com.zone_bussiness.app.R;
import com.zone_bussiness.app.userScreen.createEvents.petreceri.SelectLocation;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

public class VernisajPage1 extends Fragment {
    private static VernisajPage1 INSTANCE = null;

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

    public VernisajPage1(){
    }

    public static VernisajPage1 getINSTANCE(){
        if (INSTANCE == null)
            INSTANCE = new VernisajPage1();
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
        view = inflater.inflate(R.layout.vernisaj_page1,container,false);

        init();
        timePikers();
        focusListener();

        selectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CropImage.activity().setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(5,4)
                        .getIntent(getContext());

                startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });

        setLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),SelectLocation.class);
                intent.putExtra("redirectedPage", 2);
                startActivity(intent);
            }
        });

        return view;
    }

    private void init(){
        selectImg = view.findViewById(R.id.selectImgVernisaj);

        titleLayout = view.findViewById(R.id.vernisajTitle);
        title = view.findViewById(R.id.vernisajT);


        dateLayout = view.findViewById(R.id.vernisajData);
        data = view.findViewById(R.id.vernisajD);


        oraStart = view.findViewById(R.id.oraStartV);
        oraEnd = view.findViewById(R.id.oraEndV);
        setLocation = view.findViewById(R.id.setLocationV);
        address = view.findViewById(R.id.addressPlaceV);
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
        roundedBitmapDrawable.setCornerRadius(40);
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
                        String mnt = "" + (month + 1), dy = "" + day;
                        if((month + 1) < 10)
                            mnt = "0" + (month + 1);
                        if(day < 10)
                            dy = "0" + day;
                        String date = dy + "/" + mnt + "/" + year;
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
                        if(hourOfDay < 10)
                            h = "0" + h;
                        if(minute < 10)
                            min = "0" + min;
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
                        if(hourOfDay == 0)
                            h = "24";
                            else if(hourOfDay < 10)
                                h = "0" + h;
                        if(minute < 10)
                            min = "0" + min;
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
        selectImg.setImageResource(R.drawable.prof_pic);
        title.setText("");
        data.setText("");
        oraEnd.setText("");
        oraStart.setText("");
        address.setText("");
    }
}
