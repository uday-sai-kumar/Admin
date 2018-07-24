package com.example.udaysaikumar.clgattendance.Fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.udaysaikumar.clgattendance.R;
import com.example.udaysaikumar.clgattendance.ViewPagerAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Marks extends Fragment {
ViewPager viewPager;
TabLayout tabLayout;
ViewPagerAdapter viewPagerAdapter;
    public Fragment_Marks() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_fragment__marks, container, false);
       viewPager= v.findViewById(R.id.viewPager);
       tabLayout=v.findViewById(R.id.tabs);
        ViewPagerAdapter adapter=new ViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        return v;
    }
private void setUpPager(ViewPager viewPager)
{


}
}
