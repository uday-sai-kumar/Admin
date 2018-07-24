package com.example.udaysaikumar.clgattendance;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.udaysaikumar.clgattendance.MarksFrgments.FragmentSem_11;
import com.example.udaysaikumar.clgattendance.MarksFrgments.FragmentSem_12;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i)
        {
            case 0:return new FragmentSem_11();
            case 1:return new FragmentSem_12();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
    @Override
    public CharSequence getPageTitle(int i)
    {
        return "page"+i;
    }
}
