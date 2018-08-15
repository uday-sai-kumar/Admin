package com.example.udaysaikumar.clgattendance.Fragments;


import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.udaysaikumar.clgattendance.R;
import com.example.udaysaikumar.clgattendance.RetrofitPack.RetroGet;
import com.example.udaysaikumar.clgattendance.RetrofitPack.RetrofitMarksServer;
import com.example.udaysaikumar.clgattendance.ViewPagerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Marks extends Fragment {
ViewPager viewPager;
TabLayout tabLayout;
ProgressBar progressBar;
RelativeLayout relativeLayout;
String s;
ImageView emoji;
TextView finalpercentage;
    String API_KEY="AKPhEaFsE8c1f98hiX1VXa0dj5_7KFq0";
    RetroGet retroGet;
    JSONArray j;
    JSONObject job;
    String st;
    List<String> list=new ArrayList<>();
    List<String> listSecond=new ArrayList<>();
    Map<String,JSONObject> map=new LinkedHashMap<>();
CircleDisplay cd;

    ViewPagerAdapter viewPagerAdapter;
    public Fragment_Marks() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v=inflater.inflate(R.layout.fragment_fragment__marks, container, false);
        progressBar=v.findViewById(R.id.marksprogress);
        relativeLayout=v.findViewById(R.id.relativemarks);
        emoji=v.findViewById(R.id.emoji);
        cd=v.findViewById(R.id.circledisplay);
        cd.setValueWidthPercent(10f);
        cd.setTextSize(30f);
        cd.setColor(Color.RED);
        cd.setDrawText(true);
        cd.setFormatDigits(2);
        cd.setUnit("%");
        cd.setStepSize(0.08f);

        // cd.setCustomText(...); // sets a custom array of text
        SharedPreferences sharedPreferences=v.getContext().getSharedPreferences("MyLogin",MODE_PRIVATE);
        String UNAME=sharedPreferences.getString("username","");
        String PASS=sharedPreferences.getString("password","");
        String MARKS=sharedPreferences.getString("marks","");
        String q="{\"regno\":{$eq:\""+UNAME+"\"}}";
        //finalpercentage=v.findViewById(R.id.finalpercentage);
        retroGet = RetrofitMarksServer.getSecRetrofit().create(RetroGet.class);
        Call<String> dataCall = retroGet.getMarksData(MARKS,API_KEY,q);
        dataCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if(response.body()!=null)
                {
                    String json=response.body();
                    st=json;

                    try {
                         j = new JSONArray(json);
                         job = j.getJSONObject(0);
                        Iterator<String> it1=job.keys();
                        Iterator<String> it=job.keys();
                        while (it.hasNext()){
                            s=it.next();
                        }
                        JSONObject jj=job.getJSONObject(s);
                        JSONObject jj1=jj.getJSONObject("Final");
                        String DE="%";
                        cd.showValue(Float.parseFloat(jj1.get("%f").toString()), 100f, false);
                        Float floatval=Float.parseFloat(jj1.get("%f").toString());
                        if(floatval>=75){
emoji.setImageResource(R.drawable.smile);
                        }else if(floatval<75 && floatval>65){
                            emoji.setImageResource(R.drawable.average);
                        }
                        else {
                            emoji.setImageResource(R.drawable.sad);
                        }
                      //  finalpercentage.setText(jj1.get("%f").toString()+ DE);
                        //JSONObject jj = job.getJSONObject("11Year");

                            //Iterator<String> it = job.keys();
                            while (it1.hasNext()) {
                                list.add(it1.next());

                            }
                            for (String s : list) {
                                try {
                                    map.put(s, job.getJSONObject(s));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            map.remove("_id");
                            for (Map.Entry<String, JSONObject> maps : map.entrySet()) {
                                listSecond.add(maps.getValue().toString());
                            }

                        viewPager= v.findViewById(R.id.viewPager);
                        tabLayout=v.findViewById(R.id.tabs);
                        viewPager.setOffscreenPageLimit(8);
                        ViewPagerAdapter adapter=new ViewPagerAdapter(getChildFragmentManager(),map,listSecond);
                        viewPager.setAdapter(adapter);
                        tabLayout.setupWithViewPager(viewPager);
                        progressBar.setVisibility(View.INVISIBLE);
                        relativeLayout.setVisibility(View.VISIBLE);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

        return v;
    }

}
