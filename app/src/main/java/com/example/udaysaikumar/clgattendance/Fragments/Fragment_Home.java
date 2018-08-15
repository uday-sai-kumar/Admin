package com.example.udaysaikumar.clgattendance.Fragments;


import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
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
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.udaysaikumar.clgattendance.R;
import com.example.udaysaikumar.clgattendance.RetrofitPack.RetroGet;
import com.example.udaysaikumar.clgattendance.RetrofitPack.RetroServer;
import com.example.udaysaikumar.clgattendance.RetrofitPack.RetrofitFirebase;
import com.example.udaysaikumar.clgattendance.RetrofitPack.RetrofitMarksServer;
import com.example.udaysaikumar.clgattendance.UserDetails;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Home extends Fragment {


    String URL_PROFILE="data:image/png;base64,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";
ImageView profile_photo;
ProgressBar imageProgress,homeProgress;
String BRANCH="CSE_2015_2019";
TextView appusername,regno;
RetroGet retroGet;
TableLayout basic,btech;
LinearLayout linearProgress;
FirebaseStorage firebaseStorage;
    String API_KEY="AKPhEaFsE8c1f98hiX1VXa0dj5_7KFq0";
    String f;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v=inflater.inflate(R.layout.fragment_fragment__home, container, false);
        profile_photo=v.findViewById(R.id.profile_photo);
        imageProgress=v.findViewById(R.id.imageProgress);
        homeProgress=v.findViewById(R.id.homeprogress);
        linearProgress=v.findViewById(R.id.linearprogress);
        appusername=v.findViewById(R.id.appusername);
        regno=v.findViewById(R.id.appregno);
        basic=v.findViewById(R.id.basic);
        btech=v.findViewById(R.id.btech);
        RetroGet retroGet;
        SharedPreferences sharedPreferences=v.getContext().getSharedPreferences("MyLogin",MODE_PRIVATE);
        final String UNAME=sharedPreferences.getString("username","");
        String PROFILE=sharedPreferences.getString("profile","");
         retroGet= RetrofitFirebase.getRetrofits().create(RetroGet.class);
       Call<String>retro= retroGet.getFireImage(UNAME+".JPG");
       retro.enqueue(new Callback<String>() {
           @Override
           public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
               String res = response.body();
               assert response.body() != null;
               if (response.body() == null & !response.body().isEmpty()) {
                   Toast.makeText(v.getContext(), "failure", Toast.LENGTH_SHORT).show();
                   imageProgress.setVisibility(View.INVISIBLE);
               } else{
                   try {
                       JSONObject j = new JSONObject(res);
                       f = j.getString("downloadTokens");
                       System.out.println("123456response");
                       String url = "https://firebasestorage.googleapis.com/v0/b/site-74340.appspot.com/o/Photos%2F"+UNAME+".JPG?"+"alt=media&token="+f;
                       System.out.println("hello123" + url);
                       Glide.with(v.getContext()).load(url).apply(RequestOptions.circleCropTransform()).listener(new RequestListener<Drawable>() {
                           @Override
                           public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                               imageProgress.setVisibility(View.INVISIBLE);
                               return false;
                           }

                           @Override
                           public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                               imageProgress.setVisibility(View.INVISIBLE);
                               return false;
                           }
                       }).into(profile_photo);
                   } catch (JSONException e) {
                       e.printStackTrace();
                   }
           }

           }

           @Override
           public void onFailure(Call<String> call, Throwable t) {
               System.out.println("123456none");

           }
       });


        String q="{\"regno\":{$eq:\""+UNAME+"\"}}";
        retroGet = RetrofitMarksServer.getSecRetrofit().create(RetroGet.class);
        Call<String> dataCall = retroGet.getProfile(PROFILE,API_KEY,q);
        dataCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if(response.body()!=null)
                {
                    String json=response.body();
                    try {
                        JSONArray j = new JSONArray(json);
                        JSONObject job = j.getJSONObject(0);
                        JSONObject jj = job.getJSONObject("SI");
                        JSONObject jj1 = job.getJSONObject("BTECH");
                        Iterator<String> it = jj.keys();
                        Iterator<String>it1=jj1.keys();
                        appusername.setText(job.get("Name").toString());
                        regno.setText(job.get("regno").toString());
                        Typeface typeface= ResourcesCompat.getFont(v.getContext(),R.font.open_sans);
                        while(it.hasNext()){
                            String key=it.next();
                            TableRow tableRow=new TableRow(v.getContext());
                            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                            TextView t1=new TextView(v.getContext());
                            t1.setTypeface(typeface);
                            t1.setTextSize(TypedValue.COMPLEX_UNIT_SP,21);
                            t1.setGravity(Gravity.CENTER);
                            t1.setBackgroundResource(R.drawable.table_custom_text);
                            t1.setText(key);
                            tableRow.addView(t1);
                            TextView t2=new TextView(v.getContext());
                            t2.setTypeface(typeface);
                            t2.setTextSize(TypedValue.COMPLEX_UNIT_SP,21);
                            t2.setBackgroundResource(R.drawable.table_custom_text);
                            t2.setText(jj.get(key).toString());
                            t2.setGravity(Gravity.CENTER);
                            tableRow.addView(t2);
                            basic.addView(tableRow,new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                        }
                       TableRow tableRow1=new TableRow(v.getContext());
                        tableRow1.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        TextView ttt=new TextView(v.getContext());
                        ttt.setText("");
                        ttt.setTypeface(typeface);
                        tableRow1.addView(ttt);
                        TextView tt=new TextView(v.getContext());
                        tt.setText(getResources().getText(R.string.sem1));
                        tt.setGravity(Gravity.CENTER);
                        tt.setTypeface(typeface);
                        tt.setTextSize(TypedValue.COMPLEX_UNIT_SP,21);
                        tableRow1.addView(tt);
                        TextView tt1=new TextView(v.getContext());
                        tt1.setText(getResources().getText(R.string.sem2));
                        tt1.setTextSize(TypedValue.COMPLEX_UNIT_SP,21);
                        tt1.setTypeface(typeface);
                        tt1.setGravity(Gravity.CENTER);
                        tableRow1.addView(tt1);
                        btech.addView(tableRow1);
                        while (it1.hasNext()){
                            String key=it1.next();
                            TableRow tableRow=new TableRow(v.getContext());
                            tableRow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                            TextView t1=new TextView(v.getContext());
                            t1.setText(key);
                            t1.setTypeface(typeface);
                            t1.setGravity(Gravity.CENTER);
                            t1.setTextSize(TypedValue.COMPLEX_UNIT_SP,21);
                            t1.setBackgroundResource(R.drawable.table_custom_text_conclusion);
                            tableRow.addView(t1);
                            JSONObject sem=jj1.getJSONObject(key);
                            Iterator<String> semit=sem.keys();
                            while (semit.hasNext()){
                                String sems=semit.next();
                                TextView t2=new TextView(v.getContext());
                                t2.setTypeface(typeface);
                                t2.setGravity(Gravity.CENTER);
                                t2.setTextSize(TypedValue.COMPLEX_UNIT_SP,21);
                                t2.setBackgroundResource(R.drawable.table_custom_text_conclusion);
                                t2.setText(sem.get(sems).toString());
                                tableRow.addView(t2);
                            }
                            btech.addView(tableRow);
linearProgress.setVisibility(View.VISIBLE);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                linearProgress.setVisibility(View.INVISIBLE);
homeProgress.setVisibility(View.INVISIBLE);
imageProgress.setVisibility(View.INVISIBLE);

            }
        });
        return v;
    }

}
