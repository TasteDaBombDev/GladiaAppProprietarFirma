package com.zone_bussiness.app.userScreen.events.previzEvent;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.zone_bussiness.app.R;
import com.zone_bussiness.app.userScreen.events.previzEvent.previzEventConcert.PrevizEventConcert;
import com.zone_bussiness.app.userScreen.events.previzEvent.previzEventPetrecere.PrevizEventPetreceri;
import com.zone_bussiness.app.userScreen.events.previzEvent.previzEventVernisaj.PrevizEventVernisaj;
import com.google.android.material.tabs.TabLayout;

public class PrevizEventMain extends AppCompatActivity {


    private ViewPager pager;
    private static int ID;
    private static ImageButton back, editBtn;
    private TabLayout tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.previz_event_main);
        Bundle extras = getIntent().getExtras();
        assert extras != null;
        ID = extras.getInt("ID");
        editBtn = findViewById(R.id.butonEditat);

        pager = findViewById(R.id.pager);
        EnumFragmentsPrevizEvent enumFragmentsPrevizEvent = new EnumFragmentsPrevizEvent(getSupportFragmentManager(),this, extras.getString("type"));
        pager.setAdapter(enumFragmentsPrevizEvent);
        pager.setOffscreenPageLimit(2);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position == 1)
                    editBtn.setVisibility(View.INVISIBLE);
                else
                    editBtn.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tab = findViewById(R.id.tabLayout);
        tab.setupWithViewPager(pager);
        tab.getTabAt(0).setIcon(R.drawable.ic_description_black_24dp);
        tab.getTabAt(1).setIcon(R.drawable.ic_trending_up_black_24dp);


        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public static int getID() {
        return ID;
    }

    public static ImageButton getEdit(){
        return editBtn;
    }

    @Override
    public void onBackPressed() {
        editBtn.setVisibility(View.VISIBLE);
        pager.setCurrentItem(0);
        PrevizEventPetreceri.getA().clear();
        PrevizEventVernisaj.getA().clear();
        PrevizEventConcert.getA().clear();
        super.onBackPressed();
    }


}
