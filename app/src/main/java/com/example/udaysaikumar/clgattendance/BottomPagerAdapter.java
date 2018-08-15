package com.example.udaysaikumar.clgattendance;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.udaysaikumar.clgattendance.Fragments.Fragment_Attendance;
import com.example.udaysaikumar.clgattendance.Fragments.Fragment_Home;
import com.example.udaysaikumar.clgattendance.Fragments.Fragment_Marks;

import java.util.ArrayList;
import java.util.List;

public class BottomPagerAdapter extends FragmentPagerAdapter{
    private final List<Fragment> fragmentList=new ArrayList<>();

    public BottomPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
    public  void addFrag(Fragment fragment){
fragmentList.add(fragment);
    }
}
