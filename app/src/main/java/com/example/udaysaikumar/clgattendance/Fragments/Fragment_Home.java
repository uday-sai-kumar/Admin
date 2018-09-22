package com.example.udaysaikumar.clgattendance.Fragments;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.ObjectKey;
import com.example.udaysaikumar.clgattendance.Login.LoginData;
import com.example.udaysaikumar.clgattendance.R;
import com.example.udaysaikumar.clgattendance.RetrofitPack.RetroGet;
import com.example.udaysaikumar.clgattendance.RetrofitPack.RetrofitFirebase;
import com.example.udaysaikumar.clgattendance.RetrofitPack.RetrofitMarksServer;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Home extends Fragment {


    String URL_PROFILE="https://firebasestorage.googleapis.com/v0/b/site-74340.appspot.com/o/default.png?alt=media&token=805af3b9-0cf3-4a26-9831-c5cee7b827ca";
ImageView profile_photo;
ProgressBar imageProgress,homeProgress;
String BRANCH="CSE_2015_2019";
TextView appusername,regno;
RetroGet retroGet;
TableLayout basic,btech;
LinearLayout linearProgress;
StorageReference mStorageRef,childRef,mStorage;
ImageButton change_profile;
Bitmap bitmap;
    String UNAME;
    public final int REQUEST_CODE = 2;
    View v;

    String API_KEY="AKPhEaFsE8c1f98hiX1VXa0dj5_7KFq0";
    String f;
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         v=inflater.inflate(R.layout.fragment_fragment__home, container, false);
        profile_photo=v.findViewById(R.id.profile_photo);
        imageProgress=v.findViewById(R.id.imageProgress);
        homeProgress=v.findViewById(R.id.homeprogress);
        linearProgress=v.findViewById(R.id.linearprogress);
       // change_profile=v.findViewById(R.id.change_profile);
        appusername=v.findViewById(R.id.appusername);
        regno=v.findViewById(R.id.appregno);
        basic=v.findViewById(R.id.basic);
        btech=v.findViewById(R.id.btech);

        RetroGet retroGet;
        mStorageRef = FirebaseStorage.getInstance().getReference();
        SharedPreferences sharedPreferences=v.getContext().getSharedPreferences("MyLogin",MODE_PRIVATE);
         UNAME=sharedPreferences.getString("username","");
        String PROFILE=sharedPreferences.getString("profile","");
        LoginData data=new LoginData();
        profile();
        profile_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStorage = FirebaseStorage.getInstance().getReference();
                //upload = v.findViewById(R.id.upload);

                //checkpermission();
                if (ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Permission is not granted
                    requestPermissions( //Method of Fragment
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                            1);
                } else {
                            Intent i = new Intent(Intent.ACTION_PICK);
                            i.setType("image/*");
                            startActivityForResult(i, REQUEST_CODE);

                        

                }
            }
        });
      /*   retroGet= RetrofitFirebase.getRetrofits().create(RetroGet.class);
       Call<String>retro= retroGet.getFireImage(UNAME+".JPG");
       retro.enqueue(new Callback<String>() {
           @Override
           public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
               String res = response.body();
               assert response.body() != null;
               try {
                   if (res == null || res.equals("") || res.length() == 0 ) {
                       System.out.println("averyim" + response.body());
                       Glide.with(v.getContext()).load(URL_PROFILE).apply(RequestOptions.circleCropTransform()).into(profile_photo);
                       imageProgress.setVisibility(View.INVISIBLE);
                   } else {
                       try {
                           JSONObject j = new JSONObject(res);
                           f = j.getString("downloadTokens");
                           System.out.println("123456response");
                           String url = "https://firebasestorage.googleapis.com/v0/b/site-74340.appspot.com/o/Photos%2F" + UNAME + ".JPG?" + "alt=media&token=" + f;
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
               catch(Exception e){

               }



           }

           @Override
           public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
               Toast.makeText(v.getContext(), "please connect to active network", Toast.LENGTH_LONG).show();
           }
       });

*/
        String q="{\"regno\":{$eq:\""+UNAME+"\"}}";
        retroGet = RetrofitMarksServer.getSecRetrofit().create(RetroGet.class);
        Call<String> dataCall = retroGet.getProfile(PROFILE,API_KEY,q);
        dataCall.enqueue(new Callback<String>()

           {
               @Override
               public void onResponse
               (@NonNull Call < String > call, @NonNull Response < String > response){
               if (response.body() != null) {
                   String json = response.body();
                   try {
                       JSONArray j = new JSONArray(json);
                       JSONObject job = j.getJSONObject(0);
                       JSONObject jj = job.getJSONObject("SI");
                       JSONObject jj1 = job.getJSONObject("BTECH");
                       Iterator<String> it = jj.keys();
                       Iterator<String> it1 = jj1.keys();
                       appusername.setText(job.get("Name").toString());
                       regno.setText(job.get("regno").toString());
                       Typeface typeface = ResourcesCompat.getFont(v.getContext(), R.font.open_sans);
                       while (it.hasNext()) {
                           String key = it.next();
                           TableRow tableRow = new TableRow(v.getContext());
                           tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                           TextView t1 = new TextView(v.getContext());
                           t1.setTypeface(typeface);
                           t1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
                           t1.setGravity(Gravity.CENTER);
                           t1.setBackgroundResource(R.drawable.table_custom_text);
                           t1.setText(key);
                           tableRow.addView(t1);
                           TextView t2 = new TextView(v.getContext());
                           t2.setTypeface(typeface);
                           t2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
                           t2.setBackgroundResource(R.drawable.table_custom_text);
                           t2.setText(jj.get(key).toString());
                           t2.setGravity(Gravity.CENTER);
                           tableRow.addView(t2);
                           basic.addView(tableRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                       }
                       TableRow tableRow1 = new TableRow(v.getContext());
                       tableRow1.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                       TextView ttt = new TextView(v.getContext());
                       ttt.setText("");
                       ttt.setTypeface(typeface);
                       tableRow1.addView(ttt);
                       TextView tt = new TextView(v.getContext());
                       tt.setText(getResources().getText(R.string.sem1));
                       tt.setGravity(Gravity.CENTER);
                       tt.setTypeface(typeface);
                       tt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
                       tableRow1.addView(tt);
                       TextView tt1 = new TextView(v.getContext());
                       tt1.setText(getResources().getText(R.string.sem2));
                       tt1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
                       tt1.setTypeface(typeface);
                       tt1.setGravity(Gravity.CENTER);
                       tableRow1.addView(tt1);
                       btech.addView(tableRow1);
                       while (it1.hasNext()) {
                           String key = it1.next();
                           TableRow tableRow = new TableRow(v.getContext());
                           tableRow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                           TextView t1 = new TextView(v.getContext());
                           t1.setText(key);
                           t1.setTypeface(typeface);
                           t1.setGravity(Gravity.CENTER);
                           t1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
                           t1.setBackgroundResource(R.drawable.table_custom_text_conclusion);
                           tableRow.addView(t1);
                           JSONObject sem = jj1.getJSONObject(key);
                           Iterator<String> semit = sem.keys();
                           while (semit.hasNext()) {
                               String sems = semit.next();
                               TextView t2 = new TextView(v.getContext());
                               t2.setTypeface(typeface);
                               t2.setGravity(Gravity.CENTER);
                               t2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
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
               public void onFailure (@NonNull Call < String > call, @NonNull Throwable t){
               linearProgress.setVisibility(View.INVISIBLE);
               homeProgress.setVisibility(View.INVISIBLE);
               imageProgress.setVisibility(View.INVISIBLE);

           }

        });

        /*profile_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                try {
                    FragmentTransaction fragmentTransaction = fragmentManager != null ? fragmentManager.beginTransaction() : null;
                    PhotoFragment photoFragment = new PhotoFragment();
                    if (fragmentTransaction != null) {
                        fragmentTransaction.replace(R.id.frahome, photoFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                }
                catch (Exception e){

                }



            }
        });*/

        //System.out.println("wowbitmap1"+bitmap);

        return v;
    }
    public void profile()
    {
        childRef=mStorageRef.child("Photos/"+UNAME+".JPG");
        GlideApp.with(v.getContext()).asBitmap().load(childRef).apply(new RequestOptions().transform(new RoundedCorners(40))).signature(new ObjectKey(System.currentTimeMillis())).listener(new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                imageProgress.setVisibility(View.INVISIBLE);
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                profile_photo.setImageBitmap(resource);
                bitmap=resource;
                System.out.println("wowbitmap"+bitmap.toString());
                imageProgress.setVisibility(View.INVISIBLE);
                return false;
            }
        }).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).submit();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super method removed
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                final Uri returnUri = data.getData();
                System.out.println("moreuri"+returnUri);
                try {
                    final StorageReference storageReference=mStorage.child("Photos/"+UNAME+".JPG");
                    storageReference.putFile(returnUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    //GlideApp.with(v.getContext()).asBitmap().load(storageReference).apply(new RequestOptions().transform(new RoundedCorners(40))).into(profile_photo);
                                    Toast.makeText(v.getContext(),"upload successful",Toast.LENGTH_SHORT).show();
                                    final Bitmap bitmapImage;
                                    try {
                                        GlideApp.with(v.getContext()).load(returnUri).apply(new RequestOptions().transform(new RoundedCorners(40))).into(profile_photo);
                                      //  bitmapImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), returnUri);
                                        //profile_photo.setImageBitmap(bitmapImage);

                                    }finally {

                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(v.getContext(),"failed to upload",Toast.LENGTH_SHORT).show();

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
              //  profile();
                //.setImageBitmap(bitmapImage);
            }
        }
    }

}
