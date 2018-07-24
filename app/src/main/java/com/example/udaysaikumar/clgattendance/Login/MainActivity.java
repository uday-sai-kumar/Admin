package com.example.udaysaikumar.clgattendance.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.udaysaikumar.clgattendance.BottomBarActivity;
import com.example.udaysaikumar.clgattendance.R;
import com.example.udaysaikumar.clgattendance.RetrofitPack.RetroGet;
import com.example.udaysaikumar.clgattendance.RetrofitPack.RetroServer;
import com.example.udaysaikumar.clgattendance.Trial;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
RetroGet retroGet;
EditText username,pass;
String user,passWord;
List<LoginData> list;
Button login;
String BRANCH="CSE_2015_2019";

String API_KEY="AKPhEaFsE8c1f98hiX1VXa0dj5_7KFq0";
ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();
        username=findViewById(R.id.username);
        pass=findViewById(R.id.pass);
        progressBar=findViewById(R.id.progressBar);
        login=findViewById(R.id.login);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(MainActivity.this,R.array.branch,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (username.getText().toString().equals("") || pass.getText().toString().equals("")) {
Toast.makeText(MainActivity.this,"please specify all details",Toast.LENGTH_SHORT).show();
                }
else{

                progressBar.setIndeterminate(true);
                progressBar.setVisibility(View.VISIBLE);
                login.setEnabled(false);
                username.setEnabled(false);
                pass.setEnabled(false);
                user = username.getText().toString();
                passWord = pass.getText().toString();
               // String REF="https://api.mongolab.com/api/1/databases/usk/collections/newproj?apiKey=KmTJIck8DgWEJikNyhwTNF7cy760f9iW&q={"username":"uday@sasi.ac.in","password":"sasi@123"}
                    //String send="{\"username\":\""+user+"\",\"password\":\""+passWord+"\"}";
                   // https://api.mongolab.com/api/1/databases/sasi_attendance/collections/LOGIN?apiKey=AKPhEaFsE8c1f98hiX1VXa0dj5_7KFq0&q={"regno":{$eq:"15K61A0501"},"password":{$eq:"sasi@123"}}
                  //  q={"regno":{$eq:"15K61A0501"},"password":{$eq:"sasi@123"}}
                  String q="{\"regno\":{$eq:\""+user+"\"},\"password\":{$eq:\""+passWord+"\"}}";
                retroGet = RetroServer.getRetrofit().create(RetroGet.class);
                Call<List<LoginData>> dataCall = retroGet.getMlabData("LOGIN",API_KEY,q);
                dataCall.enqueue(new Callback<List<LoginData>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<LoginData>> call, Response<List<LoginData>> response) {
                        System.out.println("HIhello");

                        if (response.body() != null) {
                            System.out.println("HIhello");
                                //compreg = list.get(0).getUsername();
                                System.out.println("HIhello FUN");
                                //assert list != null;
                                list = response.body();
                                if (user.equals(list.get(0).getRegno()) && passWord.equals(list.get(0).getPassword())) {
                                    SharedPreferences s = getSharedPreferences("MyLogin", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = s.edit();
                                    editor.putString("username", user);
                                    editor.putString("password", passWord);
                                    editor.putString("collection",list.get(0).getCollection());
                                    editor.putBoolean("logged", true);
                                    editor.apply();
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Intent i = new Intent(MainActivity.this, Trial.class);
                                    startActivity(i);
                                    finish();
                                    //Toast.makeText(MainActivity.this,"LoginSuccess",Toast.LENGTH_LONG).show();
                                }
                                else {
                                   fun();
                                }

                        }
                        else
                        {
                            fun();
                        }

                    }

                    @Override
                    public void onFailure(Call<List<LoginData>> call, Throwable t) {
                        login.setEnabled(true);
                        username.setEnabled(true);
                        pass.setEnabled(true);
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(MainActivity.this, "Failure", Toast.LENGTH_LONG).show();

                    }

                });

            }
            }
        });


    }
    void fun(){
        login.setEnabled(true);
        username.setEnabled(true);
        pass.setEnabled(true);
        progressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(MainActivity.this, "LoginFailure", Toast.LENGTH_LONG).show();
    }

}
