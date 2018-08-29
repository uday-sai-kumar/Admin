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
import android.widget.Toast;

import com.example.udaysaikumar.clgattendance.R;
import com.example.udaysaikumar.clgattendance.RetrofitPack.RetroGet;
import com.example.udaysaikumar.clgattendance.RetrofitPack.RetrofitMarksServer;
import com.example.udaysaikumar.clgattendance.ViewPagerAdapter;
import com.github.pavlospt.CircleView;

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
TextView bad,satisfacotry,excellent;
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
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v=inflater.inflate(R.layout.fragment_fragment__marks, container, false);
        progressBar=v.findViewById(R.id.marksprogress);
        relativeLayout=v.findViewById(R.id.relativemarks);
        bad=v.findViewById(R.id.bad);
        satisfacotry=v.findViewById(R.id.satisfactory);
        excellent=v.findViewById(R.id.excellent);
//        cd=v.findViewById(R.id.circledisplay);
//        cd.setValueWidthPercent(10f);
//        cd.setTextSize(30f);
//        cd.setColor(Color.RED);
//        cd.setDrawText(true);
//        cd.setFormatDigits(2);
//        cd.setUnit("%");
//        cd.setStepSize(0.08f);

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
            @SuppressLint("SetTextI18n")
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
                        Iterator<String> itt1=jj1.keys();
                        Iterator<String> itt2=jj1.keys();
                        String ss=null;
                        String ss1=null;
                        boolean b=false;
                        while (itt1.hasNext()){
                            ss=itt1.next();
                            if(ss.contains("CGPA")){
                                b=true;
                                break;
                            }

                        }
                        if(!b){
                            while (itt2.hasNext()){
                                ss1=itt2.next();
                                if(ss1.contains("SGPA")){
                                    ss=ss1;
                                    break;
                                }

                            }
                        }
                        String DE="%";
                        SharedPreferences sharedPreferences=v.getContext().getSharedPreferences("MyLogin",MODE_PRIVATE);
                        final String UNAME=sharedPreferences.getString("username","");
                        if (Integer.parseInt(UNAME.subSequence(0, 2).toString()) > 15) {
                            Float f=Float.parseFloat(jj1.get(ss).toString());
                            // Float floatval=6.4f;
                            if(f>=7){
                                excellent.setText(f.toString());
                                excellent.setBackground(getResources().getDrawable(R.drawable.circle_aggregades));
                               // excellent.setText(f.toString());
                                                         }
                                else if(f<7 && f>6){
                                satisfacotry.setText(f.toString());
                                satisfacotry.setBackground(getResources().getDrawable(R.drawable.circle_aggregades));
                                //satisfacotry.setText(f.toString());
                                                         }
                            else {
                                bad.setText(f.toString());
                                bad.setBackground(getResources().getDrawable(R.drawable.circle_aggregades));
                                                         }
                        }
                        else{
                            Float f=Float.parseFloat(jj1.get("%f").toString());
                            if(f>=75){
                                excellent.setText(f.toString());
                                excellent.setBackground(getResources().getDrawable(R.drawable.circle_aggregades));
                              //  excellent.setText(f.toString());

                            }else if(f<75 && f>65){
                                satisfacotry.setText(f.toString());
                                satisfacotry.setBackground(getResources().getDrawable(R.drawable.circle_aggregades));
                            }
                            else {
                                bad.setText(f.toString());
                                bad.setBackground(getResources().getDrawable(R.drawable.circle_aggregades));
                            }
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
                else{
                    progressBar.setVisibility(View.INVISIBLE);

                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(v.getContext(), "please connect to active network", Toast.LENGTH_LONG).show();
            }
        });

        return v;
    }

}
