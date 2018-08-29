package com.example.udaysaikumar.clgattendance;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.udaysaikumar.clgattendance.Fragments.Fragment_Attendance;
import com.example.udaysaikumar.clgattendance.Fragments.Fragment_Feedback;
import com.example.udaysaikumar.clgattendance.Fragments.Fragment_Home;
import com.example.udaysaikumar.clgattendance.Fragments.Fragment_Marks;
import com.example.udaysaikumar.clgattendance.Login.MainActivity;

import java.util.Objects;

public class BottomBarActivity extends AppCompatActivity {
    Fragment_Home fragment_home;
   Fragment_Attendance fragment_attendance;
   Fragment_Marks fragment_marks;
BottomNavigationView bottomNavigationView;
RelativeLayout relativeLayout;
CoordinatorLayout coordinatorLayout;
FrameLayout frameLayout;
int i;
    ViewPager viewPager;
Fragment frag=null;
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.action_bar_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
       switch (item.getItemId())
       {
           case R.id.logat:{
               SharedPreferences sharedPreferences=getSharedPreferences("MyLogin",MODE_PRIVATE);
               sharedPreferences.edit().remove("logged").apply();
               sharedPreferences.edit().remove("username").apply();
               sharedPreferences.edit().remove("password").apply();
               sharedPreferences.edit().remove("phone").apply();
               sharedPreferences.edit().remove("otp").apply();
               sharedPreferences.edit().remove("marks").apply();
               sharedPreferences.edit().remove("profile").apply();
               sharedPreferences.edit().remove("attendance").apply();
               Intent i=new Intent(getApplicationContext(),MainActivity.class);
               startActivity(i);
               finish();
           }
       }
        return  super.onOptionsItemSelected(item);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_bar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        coordinatorLayout=findViewById(R.id.mycoordinate);
        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        relativeLayout=findViewById(R.id.myrelative);
       // frameLayout=findViewById(R.id.frame);
viewPager=findViewById(R.id.viewPager);
viewPager.setOffscreenPageLimit(3);

viewPager.setPageTransformer(false,new PagerTransformer());
      fragment_attendance=new Fragment_Attendance();
      fragment_marks=new Fragment_Marks();
      fragment_home=new Fragment_Home();
        bottomNavigationView.setSelectedItemId(R.id.menu_home);
bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        CharSequence s1;
        switch (menuItem.getItemId())
        {
            case R.id.menu_home:
                viewPager.setCurrentItem(0);
                return  true;

            case R.id.menu_attendance:
                viewPager.setCurrentItem(1);
                return  true;

            case R.id.menu_marks:
                viewPager.setCurrentItem(2);
                return true;
            case R.id.feedback:
                viewPager.setCurrentItem(3);
                return  true;


        }
        return  false;
    }
});
checkNet();
    }
    public void checkNet(){
        ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        assert networkInfo != null;
        if(networkInfo.isConnected())
        {
            bottomNavigationView.setSelectedItemId(R.id.menu_home);
            setUpPager(viewPager);
        }
        else{
            Snackbar snackbar=Snackbar.make(relativeLayout,"No internt connection",Snackbar.LENGTH_INDEFINITE).setAction("retry", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkNet();
                }
            });
            snackbar.show();
            

        }
    }
    private  void setUpPager(ViewPager pager){

        BottomPagerAdapter pagerAdapter = new BottomPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFrag(new Fragment_Home());
        pagerAdapter.addFrag(new Fragment_Attendance());
        pagerAdapter.addFrag(new Fragment_Marks());
        pagerAdapter.addFrag(new Fragment_Feedback());
        pager.setAdapter(pagerAdapter);

    }

}
