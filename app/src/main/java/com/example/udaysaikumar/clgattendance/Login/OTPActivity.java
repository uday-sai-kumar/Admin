package com.example.udaysaikumar.clgattendance.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.udaysaikumar.clgattendance.BottomBarActivity;
import com.example.udaysaikumar.clgattendance.R;
import com.example.udaysaikumar.clgattendance.RetrofitPack.RetroGet;
import com.example.udaysaikumar.clgattendance.RetrofitPack.RetroServer;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPActivity extends AppCompatActivity {
    EditText optText;
    Button login;
    TextView resent;
    RetroGet retroGet;
    List<LoginData> list;
    ProgressBar progressBar;
    RelativeLayout main;
    String API_KEY = "AKPhEaFsE8c1f98hiX1VXa0dj5_7KFq0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        Objects.requireNonNull(getSupportActionBar()).hide();
        optText = findViewById(R.id.opt);
        login = findViewById(R.id.login);
        resent = findViewById(R.id.resentotp);
        main=findViewById(R.id.main);
        progressBar=findViewById(R.id.progressBar);
        resent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!optText.getText().toString().equals("") || optText.getText().toString().length()>0) {
                    SharedPreferences sharedPreferences = getSharedPreferences("MyLogin", MODE_PRIVATE);
                    int i = sharedPreferences.getInt("otp", 0);
                        int k = Integer.parseInt(optText.getText().toString());
                        if (k == i) {
                            final SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("logged", true);
                            SharedPreferences sharedPref = getSharedPreferences("MyLogin", MODE_PRIVATE);
                            String username = sharedPref.getString("username", "");
                            System.out.println("important"+username);
                            String q = "{\"regno\":{$eq:\""+ username+"\"}}";
                            retroGet = RetroServer.getRetrofit().create(RetroGet.class);
                            Call<List<LoginData>> dataCall = retroGet.getPhone("LOGIN", API_KEY, q);
                            progressBar.setVisibility(View.VISIBLE);
                            resent.setEnabled(false);
                            optText.setEnabled(false);
                            login.setEnabled(false);
                            dataCall.enqueue(new Callback<List<LoginData>>() {
                                @Override
                                public void onResponse(@NonNull Call<List<LoginData>> call, @NonNull Response<List<LoginData>> response) {
                                    assert response.body() != null;
                                    if (response.body() != null & !response.body().isEmpty()) {
                                        list = response.body();
                                        System.out.println("important"+list);
                                        if (list != null && !list.isEmpty()) {
                                            System.out.println("OTP activity" + list.get(0).getMarks());
                                            editor.putString("marks", list.get(0).getMarks());
                                            editor.putString("profile", list.get(0).getProfile());
                                            editor.putString("attendance", list.get(0).getAttendance());
                                            Intent intent = new Intent(getApplicationContext(), BottomBarActivity.class);
                                            editor.apply();
                                            startActivity(intent);
                                            finish();

                                        }

                                    }else{
                                        Toast.makeText(getApplicationContext(),"sorry, no document found",Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.INVISIBLE);
                                        resent.setEnabled(true);
                                        optText.setEnabled(true);
                                        login.setEnabled(true);                                    }

                                }
                                @Override
                                public void onFailure(@NonNull Call<List<LoginData>> call, @NonNull Throwable t) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    resent.setEnabled(true);
                                    optText.setEnabled(true);
                                    login.setEnabled(true);
                                    Toast.makeText(getApplicationContext(),"please connect to active network",Toast.LENGTH_SHORT).show();
                                }

                            });

                        } else {
                            Toast.makeText(getApplicationContext(), "please enter valid OTP", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                    Toast.makeText(getApplicationContext(), "please enter valid OTP", Toast.LENGTH_SHORT).show();

                }
                }


        });
    }
}
