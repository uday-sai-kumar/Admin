package com.example.udaysaikumar.clgattendance;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

public class PagerTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(@NonNull View view, float position) {
        if (position < 0) {
            view.setScrollX((int)((float)(view.getWidth()) * position));
        } else if (position > 0) {
            view.setScrollX(-(int) ((float) (view.getWidth()) * -position));
        } else {
            view.setScrollX(0);
        }
    }
}
