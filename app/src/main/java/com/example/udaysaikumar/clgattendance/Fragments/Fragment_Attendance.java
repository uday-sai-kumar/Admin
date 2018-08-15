package com.example.udaysaikumar.clgattendance.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.udaysaikumar.clgattendance.R;
import com.example.udaysaikumar.clgattendance.RetrofitPack.RetroGet;
import com.example.udaysaikumar.clgattendance.RetrofitPack.RetrofitMarksServer;
import com.example.udaysaikumar.clgattendance.RetrofitPack.TimeStampClass;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.squareup.timessquare.CalendarPickerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Objects;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Attendance extends Fragment {

    RetroGet retroGet;
    String API_KEY="AKPhEaFsE8c1f98hiX1VXa0dj5_7KFq0";
ProgressBar progressAttendance;
LinearLayout linearLayout;
    String URL_PROFILE="https://lh3.googleusercontent.com/Df4bZYWLSwgZ_TksplBpLiHLjCYl6TLQqDRwwEFfUSvZBpMfizmgBcSVZyk8cgR4pQBZvTDZuv0MfZKYvOSHfCJVijImxWbJsXXaTGQYzq1Jh8yINHb0v-mrOmeuLratj8ZG4CaUReRrXxL2e1rlJZmvVxro74p6aiGMe3QTKcrru88Sn5WgvfLNRzRsVV4aYhr2y8k4iPKOmb3CI7lGbR9Z48oenUVcBD0f895j60N59d3ZchTpQWLJU-yNHD0xpcTqRtPcL6eqKJnOFnaEj5vhxswJWXeaJp96JWiKyfVBYAr9XGOmPwyzjvJz5XICIX2WxStqLM3xbZ3Tlv-h0lZGwITOZiyabwy9GC8ugjqrFmI2KkjqgQc5R58ASkMSW5-5O-AATfFcVzXcosO-ZFkmhBV4ixzO0i0khfY4ubJ0pCFXX7y3RPUEF508ItoYBSyMgqMdVUJncs8IkKaW6aUTzHNgRFUWOTUtlmwpcIf2LKXH2oj2CAWL7MbQ1Gml4PxBhPF_9HUZOewCawggYCVAX-Bb4KNCZITQxwqvTrA7QITUA_b399pdVyo=s665-w499-h665-no";
    TableLayout tableAttendance;
    MaterialCalendarView mCV;
    String timestamp;
    View v;
    String UNAME;
    TextView finalattendance;
    String ATTENDANCE;
    CircleDisplay cd;
    ImageView emoji;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         v=inflater.inflate(R.layout.fragment_fragment__attendance, container, false);
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

//        CalendarPickerView calendar = v.findViewById(R.id.calendeview);
//        Date today = new Date();
//        calendar.init(today, nextYear.getTime())
//                .withSelectedDate(today);
        tableAttendance=v.findViewById(R.id.tableAttendance);
        progressAttendance=v.findViewById(R.id.progressAttendance);
        linearLayout=v.findViewById(R.id.attendacelayout);
        emoji=v.findViewById(R.id.emoji);
        mCV=v.findViewById(R.id.calenderView);
        mCV.canScrollVertically(1);
        cd=v.findViewById(R.id.circledisplay);
        cd.setValueWidthPercent(10f);
        cd.setTextSize(30f);
        cd.setColor(Color.RED);
        cd.setDrawText(true);
        cd.setFormatDigits(2);
        cd.setUnit("%");
        cd.setStepSize(0.08f);
        progressAttendance.setIndeterminate(true);
        SharedPreferences sharedPreferencess=v.getContext().getSharedPreferences("MyLogin",MODE_PRIVATE);
        UNAME=sharedPreferencess.getString("username","");
        String qq="{\"regno\":{$eq:\""+UNAME+"\"}}";
        retroGet = RetrofitMarksServer.getSecRetrofit().create(RetroGet.class);
        Call<String> dataAttendance = retroGet.getPercentage("PERCENTAGE",API_KEY,qq);
        dataAttendance.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                assert response.body() != null;
                String json=response.body();
                assert json != null;
                if(!json.isEmpty()){
                    try {
                       JSONArray jsonArray = new JSONArray(json);
                        JSONObject jsonObject=jsonArray.getJSONObject(0);
                        Iterator<String>iterator=jsonObject.keys();
                        String st=null;
                        while (iterator.hasNext()){
                             st=iterator.next();
                        }
                        String jsonObject1=jsonObject.get(st).toString();

                      Float f=  Float.parseFloat(jsonObject1);
                        cd.showValue(f, 100, false);
                        if(f>=75){
                            emoji.setImageResource(R.drawable.smile);
                        }else if(f<75 && f>65){
                            emoji.setImageResource(R.drawable.average);
                        }
                        else {
                            emoji.setImageResource(R.drawable.sad);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                                   }

            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {

            }
        });
       // finalattendance=v.findViewById(R.id.finalattendance);

        SharedPreferences sharedPreferences=v.getContext().getSharedPreferences("MyLogin",MODE_PRIVATE);
        UNAME=sharedPreferences.getString("username","");
        ATTENDANCE=sharedPreferences.getString("attendance","");

      //  finalattendance.setText("78 %");

retroGet= TimeStampClass.getTimestamp().create(RetroGet.class);
Call<String> stringCall=retroGet.getTimeStamp();
stringCall.enqueue(new Callback<String>() {
    @Override
    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
        String json=response.body();
        try {
            JSONObject jsonObject=new JSONObject(json);
            JSONArray jsonArray=jsonObject.getJSONArray("zones");
            JSONObject jsonObject1=jsonArray.getJSONObject(0);
           timestamp=jsonObject1.getString("timestamp");
           //System.out.println("dateis"+System.currentTimeMillis());
            Timestamp time=new Timestamp((Long.parseLong(timestamp)-19800)*1000L);
            Date date=new Date(time.getTime());
            System.out.println("dateis1"+date);
                mCV.setDateSelected(date,true);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    final CalendarDay calendarDay=mCV.getSelectedDate();

mCV.addDecorator(new DayDecorator(v.getContext(),calendarDay));

      //System.out.println("currentdate"+mCV.getSelectedDate());

       // callAttendance();
        mCV.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

               // Toast.makeText(v.getContext(),mCV.getSelectedDate().toString(),Toast.LENGTH_SHORT).show();

            String adate=getDate(mCV.getSelectedDate().toString());
            callAttendance(adate);

            }
        });

    }

    @Override
    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
        progressAttendance.setVisibility(View.INVISIBLE);
        linearLayout.setVisibility(View.VISIBLE);
    }
});

        progressAttendance.setVisibility(View.INVISIBLE);
        linearLayout.setVisibility(View.VISIBLE);
        return v;
    }
    public String getDate(String str){
        String[] s=str.replace("CalendarDay{","").replace("}","").split("-");
        String ss=s[2]+s[1]+s[0];
        int f=Integer.parseInt(s[1])+1;
        String ff=s[0].substring(2,4);
        System.out.println("hellojson"+s[2]+"/"+f+"/"+ff);
        return s[2]+"/"+f+"/"+ff;
    }
    public void callAttendance(String adate){
        final String q="{\"regno\":{$eq:\""+UNAME+"\"},\"date\":{$eq:\""+adate+"\"}}";
        retroGet = RetrofitMarksServer.getSecRetrofit().create(RetroGet.class);
        Call<String> dataAttendance = retroGet.getAttendance(ATTENDANCE,API_KEY,q);
        dataAttendance.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if(response.body()!=null)
                {
                    if(!Objects.requireNonNull(response.body()).isEmpty())
                    {
                        String json=response.body();
                        System.out.println("hellojson"+json);
                        try {
                            tableAttendance.removeAllViews();
                            System.out.println("hihihi143"+json);
                            Typeface typeface = ResourcesCompat.getFont(v.getContext(), R.font.open_sans);
                            JSONArray jsonArray=new JSONArray(json);
                            JSONObject jsonObject=jsonArray.getJSONObject(0);
                            JSONObject jsonObject1=jsonObject.getJSONObject("attend");
                            System.out.println("hihihihi"+jsonObject1.toString());
                            Iterator<String> iterator=jsonObject1.keys();
                            while (iterator.hasNext()) {
                                String key=iterator.next();
                                //System.out.println(jsonObject1.get(key).toString());
                                TableRow tableRow = new TableRow(v.getContext());
                                tableRow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                TextView t = new TextView(v.getContext());
                                t.setText(key);
                                t.setTypeface(typeface);
                                t.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                                t.setBackgroundResource(R.drawable.table_custom_text_conclusion);
                                t.setGravity(Gravity.CENTER);
                                tableRow.addView(t);
                               /* TextView textView=new TextView(v.getContext());
                                textView.setText(key);
                                textView.setTypeface(typeface);
                                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                                tableRow.addView(textView);*/
                                TextView textView1=new TextView(v.getContext());
                                textView1.setText(jsonObject1.get(key).toString());
                                textView1.setTypeface(typeface);
                                textView1.setGravity(Gravity.CENTER);
                                textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
                                textView1.setBackgroundResource(R.drawable.table_custom_text_conclusion);
                                tableRow.addView(textView1);
                                tableAttendance.addView(tableRow,new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        progressAttendance.setVisibility(View.INVISIBLE);
                        linearLayout.setVisibility(View.VISIBLE);
                    }
                }
                else {
                    progressAttendance.setVisibility(View.INVISIBLE);
                    linearLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                progressAttendance.setVisibility(View.INVISIBLE);
                linearLayout.setVisibility(View.VISIBLE);
            }
        });
    }


}
