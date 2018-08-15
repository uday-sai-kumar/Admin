package com.example.udaysaikumar.clgattendance.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.udaysaikumar.clgattendance.BottomBarActivity;
import com.example.udaysaikumar.clgattendance.R;
import com.example.udaysaikumar.clgattendance.RetrofitPack.RetroGet;
import com.example.udaysaikumar.clgattendance.RetrofitPack.RetroServer;
import com.example.udaysaikumar.clgattendance.RetrofitPack.RetrofitOPTServer;

import java.util.List;
import java.util.Objects;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
RetroGet retroGet;
EditText phone,pass;
String phoneNo,passWord;
List<PhoneData> list;
Button login;
String BRANCH="CSE_2015_2019";

String API_KEY="AKPhEaFsE8c1f98hiX1VXa0dj5_7KFq0";
ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();
        phone=findViewById(R.id.pNo);
       // pass=findViewById(R.id.pass);
        progressBar=findViewById(R.id.progressBar);
        login=findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (phone.getText().toString().equals("")|| phone.getText().length()<=9) {
Toast.makeText(MainActivity.this,"please enter valid number",Toast.LENGTH_SHORT).show();
                }
else{

                progressBar.setIndeterminate(true);
                progressBar.setVisibility(View.VISIBLE);
                login.setEnabled(false);
                phone.setEnabled(false);
                //pass.setEnabled(false);
                phoneNo = phone.getText().toString();

              //  passWord = pass.getText().toString();
               // String REF="https://api.mongolab.com/api/1/databases/usk/collections/newproj?apiKey=KmTJIck8DgWEJikNyhwTNF7cy760f9iW&q={"username":"uday@sasi.ac.in","password":"sasi@123"}
                    //String send="{\"username\":\""+user+"\",\"password\":\""+passWord+"\"}";
                   // https://api.mongolab.com/api/1/databases/sasi_attendance/collections/LOGIN?apiKey=AKPhEaFsE8c1f98hiX1VXa0dj5_7KFq0&q={"regno":{$eq:"15K61A0501"},"password":{$eq:"sasi@123"}}
                  //  q={"regno":{$eq:"15K61A0501"},"password":{$eq:"sasi@123"}}
                   // String q="{\"phone\":{$eq:\""+phoneNo+"\"}}";
                    final String q="{\"parentmobile\":{$eq:\""+phoneNo+"\"}}";
                    final String p="{\"studentmobile\":{$eq:\""+phoneNo+"\"}}";
                    // String q="{\"regno\":{$eq:\""+user+"\"},\"password\":{$eq:\""+passWord+"\"}}";
                retroGet = RetroServer.getRetrofit().create(RetroGet.class);
                     Call<List<PhoneData>> dataCall = retroGet.getPhoneData("PHONENUMBERS",API_KEY,p);
                    //  Call<List<LoginData>> dataCall = retroGet.getPhone("LOGIN",API_KEY,q);
                dataCall.enqueue(new Callback<List<PhoneData>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<PhoneData>> call, @NonNull Response<List<PhoneData>> response) {
                        assert response.body() != null;
                        if (response.body()!= null &!response.body().isEmpty()) {
                                list = response.body();
                                System.out.println("ourresponse"+list);
                            if (list != null && !list.isEmpty()) {
                                if (phoneNo.equals(list.get(0).getStudentmobile())) {
                                    SharedPreferences s = getSharedPreferences("MyLogin", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = s.edit();
                                    Random random=new Random();
                                    int n=random.nextInt(7000)+1000;
                                    String otp="Your one time password is \n"+n;
                                    editor.putString("username",list.get(0).getRegno());
                                    editor.putString("phone", phoneNo);
                                   editor.putInt("otp",n);
                                    editor.apply();
                                    retroGet= RetrofitOPTServer.getRetrofitOTP().create(RetroGet.class);
                                    String un="sasicollege",pass="SITE2002",from="INSITE",to=phoneNo,type="1";
                                    Call<String> retro=retroGet.getOTP(un,pass,from,to,otp,type);
                                    retro.enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {

                                          //  Toast.makeText(view.getContext(), response.body(),Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                                            //Toast.makeText(view.getContext(),"Good",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Intent i = new Intent(MainActivity.this, OTPActivity.class);
                                    startActivity(i);
                                    finish();
                                    //Toast.makeText(MainActivity.this,"LoginSuccess",Toast.LENGTH_LONG).show();
                                }
                                else {
                                    System.out.println("ourresponse 1"+list);

                                    fun();
                                }
                            }
                            else {
                                System.out.println("ourresponse 2"+list);

                                fun();
                            }

                        }

                        else
                        {
                            System.out.println("ourresponse 3"+list);

                            hello(q);
                        }

                    }
                    private void hello(String query){
                        retroGet = RetroServer.getRetrofit().create(RetroGet.class);
                        System.out.println("inhello");
                        Call<List<PhoneData>> dataCall = retroGet.getPhoneData("PHONENUMBERS",API_KEY,query);
                        dataCall.enqueue(new Callback<List<PhoneData>>() {
                            @Override
                            public void onResponse(@NonNull Call<List<PhoneData>> call, @NonNull Response<List<PhoneData>> response) {
                                assert response.body() != null;
                                if (response.body() != null &!response.body().isEmpty()) {
                                    list = response.body();
                                    if (list != null && !list.isEmpty()) {
                                        if (phoneNo.equals(list.get(0).getParentmobile())) {
                                            SharedPreferences s = getSharedPreferences("MyLogin", MODE_PRIVATE);
                                            SharedPreferences.Editor editor = s.edit();
                                            Random random = new Random();
                                            int n = random.nextInt(7000) + 1000;
                                            String otp = "Your one time password is \n" + n;
                                            editor.putString("username", list.get(0).getRegno());
                                            editor.putString("phone", phoneNo);
                                            editor.putInt("otp",n);
                                            editor.apply();
                                            progressBar.setVisibility(View.INVISIBLE);
                                            retroGet = RetrofitOPTServer.getRetrofitOTP().create(RetroGet.class);
                                            String un = "sasicollege", pass = "SITE2002", from = "INSITE", to = phoneNo, type = "1";
                                            Call<String> retro = retroGet.getOTP(un, pass, from, to, otp, type);
                                            retro.enqueue(new Callback<String>() {
                                                @Override
                                                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {


                                                }

                                                @Override
                                                public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {

                                                }
                                            });
                                            Intent i = new Intent(MainActivity.this, OTPActivity.class);
                                            startActivity(i);
                                            finish();
                                        }
                                    }
                                }
                                else{
                                    fun();
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<List<PhoneData>> call, @NonNull Throwable t) {

                            }
                        });
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<PhoneData>> call, @NonNull Throwable t) {
                        login.setEnabled(true);
                        phone.setEnabled(true);
                        //pass.setEnabled(true);
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(MainActivity.this, "Failure, please connect to active network", Toast.LENGTH_LONG).show();

                    }

                });

            }
            }
        });


    }
    void fun(){
        login.setEnabled(true);
        phone.setEnabled(true);
        //pass.setEnabled(true);
        progressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(MainActivity.this, "LoginFailure", Toast.LENGTH_LONG).show();
    }

}
