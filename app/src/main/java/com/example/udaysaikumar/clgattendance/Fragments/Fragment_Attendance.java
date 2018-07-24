package com.example.udaysaikumar.clgattendance.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.udaysaikumar.clgattendance.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Attendance extends Fragment {


    public Fragment_Attendance() {
        // Required empty public constructor
    }

    String URL_PROFILE="https://lh3.googleusercontent.com/Df4bZYWLSwgZ_TksplBpLiHLjCYl6TLQqDRwwEFfUSvZBpMfizmgBcSVZyk8cgR4pQBZvTDZuv0MfZKYvOSHfCJVijImxWbJsXXaTGQYzq1Jh8yINHb0v-mrOmeuLratj8ZG4CaUReRrXxL2e1rlJZmvVxro74p6aiGMe3QTKcrru88Sn5WgvfLNRzRsVV4aYhr2y8k4iPKOmb3CI7lGbR9Z48oenUVcBD0f895j60N59d3ZchTpQWLJU-yNHD0xpcTqRtPcL6eqKJnOFnaEj5vhxswJWXeaJp96JWiKyfVBYAr9XGOmPwyzjvJz5XICIX2WxStqLM3xbZ3Tlv-h0lZGwITOZiyabwy9GC8ugjqrFmI2KkjqgQc5R58ASkMSW5-5O-AATfFcVzXcosO-ZFkmhBV4ixzO0i0khfY4ubJ0pCFXX7y3RPUEF508ItoYBSyMgqMdVUJncs8IkKaW6aUTzHNgRFUWOTUtlmwpcIf2LKXH2oj2CAWL7MbQ1Gml4PxBhPF_9HUZOewCawggYCVAX-Bb4KNCZITQxwqvTrA7QITUA_b399pdVyo=s665-w499-h665-no";
    ImageView profile_photo;
TextView textUser,textPass;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_fragment__attendance, container, false);
       // textUser=v.findViewById(R.id.shareuser);
       // textPass=v.findViewById(R.id.sharedpass);
      //  SharedPreferences sharedPreferences=getActivity().getSharedPreferences("MyLogin",Context.MODE_PRIVATE);
        //String str=sharedPreferences.getString("username","");
       // Toast.makeText(v.getContext(),str,Toast.LENGTH_SHORT).show();
        return v;
    }

}
