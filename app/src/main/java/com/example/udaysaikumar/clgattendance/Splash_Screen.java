package com.example.udaysaikumar.clgattendance;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.udaysaikumar.clgattendance.Login.MainActivity;

public class Splash_Screen extends AppCompatActivity {
ImageView splash_image;
String SPLASH_URL="http://www.goqwickly.com/imgs/computer-screens/attendance-splash.png";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash__screen);
        getSupportActionBar().hide();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                SharedPreferences sharedPreferences=getSharedPreferences("MyLogin",MODE_PRIVATE);
                if(sharedPreferences.getBoolean("logged",false))
                {
                    Intent i=new Intent(getApplicationContext(),Trial.class);
                    startActivity(i);
                    finish();
                }
                else {
                    Intent i=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        }).start();

    }
}
