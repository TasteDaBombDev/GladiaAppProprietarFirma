package com.example.app.userScreen;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.app.userScreen.profile.Profile;
import com.example.app.userScreen.profile.ProfileMainClass;

public class EnumFragments extends FragmentPagerAdapter {


    private Context context;

    @SuppressWarnings("deprecation")
    public EnumFragments(@NonNull FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position){
        if(position == 1)
            return ProfileMainClass.getINSTANCE();
        else if(position == 0)
            return Poze.getINSTANCE();
        else if(position == 2)
            return MapActivity.getINSTANCE();
        else if(position == 3)
            return Evenimente.getINSTANCE();
        return null;
    }

//    public EnumFragments(Context context) {
//        this.context = context;
//    }

//    @NonNull
//    @Override
//    public Object instantiateItem(@NonNull ViewGroup container, int position) {
//        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view;
//
//        switch (position){
//            case 0:
//                view = layoutInflater.inflate(R.layout.profile_fragment,null);
//                break;
//            default:
//                view = layoutInflater.inflate(R.layout.map_fragment,null);
//                break;
//
//        }


//        ConstraintLayout mapContent = view.findViewById(R.id.bg), profile = view.findViewById(R.id.profile);
//
//        EditText username = view.findViewById(R.id.usernameProfile);
//        username.setText(MainScreen.getUsername());
//
//        if(position == 0) {
//            mapContent.setVisibility(View.VISIBLE);
//            profile.setVisibility(View.INVISIBLE);
//        }
//        else
//        {
//            mapContent.setVisibility(View.INVISIBLE);
//            profile.setVisibility(View.VISIBLE);
//        }

//        ViewPager viewPager = (ViewPager)container;
//        viewPager.addView(view);
//
//        return view;
//    }

//    @Override
//    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        ViewPager viewPager = (ViewPager)container;
//        View view = (View) object;
//        viewPager.removeView(view);
//    }
//
    @Override
    public int getCount() {
        return 4;
    }
//
//    @Override
//    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
//        return view == object;
//    }
}
