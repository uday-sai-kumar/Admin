package com.example.udaysaikumar.clgattendance;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.udaysaikumar.clgattendance.Login.LoginData;
import com.example.udaysaikumar.clgattendance.MarksPack.MarksData;
import com.example.udaysaikumar.clgattendance.RetrofitPack.RetroGet;
import com.example.udaysaikumar.clgattendance.RetrofitPack.RetroServer;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Trial extends AppCompatActivity {
    String API_KEY="AKPhEaFsE8c1f98hiX1VXa0dj5_7KFq0";
RetroGet retroGet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trial);
        SharedPreferences sharedPreferences=getSharedPreferences("MyLogin",MODE_PRIVATE);
        String UNAME=sharedPreferences.getString("username","");
        String PASS=sharedPreferences.getString("password","");
        System.out.println("hellousk"+UNAME);
        String q="{\"regno\":{$eq:\""+UNAME+"\"},\"password\":{$eq:\""+PASS+"\"}}";
        retroGet = RetroServer.getRetrofit().create(RetroGet.class);
        Call<List<MarksData>> dataCall = retroGet.getMarksData("TT",API_KEY,q);
        dataCall.enqueue(new Callback<List<MarksData>>() {
            @Override
            public void onResponse(Call<List<MarksData>> call, Response<List<MarksData>> response) {
                if(response.body()!=null){
                    System.out.println("hellousk");
                    System.out.print(response.body().get(0).getRegno());
                    Toast.makeText(Trial.this,response.body().get(0).getRegno(),Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(Trial.this,"failure in onresoponse" ,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<MarksData>> call, Throwable t) {
                System.out.print("hello"+t.toString());
                Toast.makeText(Trial.this,"failure",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
