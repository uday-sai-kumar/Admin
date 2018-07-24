package com.example.udaysaikumar.clgattendance.MarksFrgments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.udaysaikumar.clgattendance.MarksPack.MarksData;
import com.example.udaysaikumar.clgattendance.R;
import com.example.udaysaikumar.clgattendance.RetrofitPack.RetroGet;
import com.example.udaysaikumar.clgattendance.RetrofitPack.RetroServer;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSem_11 extends Fragment {
    String BRANCH="CSE_2015_2019";
    RetroGet retroGet;
    String USER="Uni.Reg.No";
    String PA="Password";
    String API_KEY="AKPhEaFsE8c1f98hiX1VXa0dj5_7KFq0";
TextView eng,engi,engex,engtotal;
TextView ma,mai,maex,matotal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_fragment_sem_11, container, false);
        eng=v.findViewById(R.id.eng);
        engi=v.findViewById(R.id.engi);
        engex=v.findViewById(R.id.engex);
        engtotal=v.findViewById(R.id.engtotal);
        ma=v.findViewById(R.id.ma1);
        mai=v.findViewById(R.id.ma1i);
        maex=v.findViewById(R.id.ma1ex);
        matotal=v.findViewById(R.id.ma1total);
        SharedPreferences sharedPreferences=v.getContext().getSharedPreferences("MyLogin",MODE_PRIVATE);
        String UNAME=sharedPreferences.getString("username","");
        String PASS=sharedPreferences.getString("password","");
        retroGet= RetroServer.getRetrofit().create(RetroGet.class);
        String q="{\"username\":{$eq:\""+UNAME+"\"},\"password\":{$eq:\""+PASS+"\"}}";
     //   String q2="{\"field8\":{$eq:\""+USER+"\"},\"field9\":{$eq:\""+PA+"\"}}";
       // String f="{\"field42\": 1,\"field43\": 1,\"field44\": 1,\"field45\": 1,\"field46\": 1,\"field47\": 1,\"field48\": 1,\"field49\": 1,\"field50\": 1}";
      //  String f="{\"field42\": 1,\"field52\": 1,\"field62\": 1,\"field43\": 1,\"field53\": 1,\"field63\": 1,\"field44\": 1,\"field54\": 1,\"field64\": 1}";
        Call<List<MarksData>> datas = retroGet.getMarksData("TRIAL",API_KEY,q);
        datas.enqueue(new Callback<List<MarksData>>() {
            @Override
            public void onResponse(Call<List<MarksData>> call, Response<List<MarksData>> response) {
                System.out.println("Hello"+response.body().get(0).getRegno());
//eng.setText(response.body().get(0).getField42());
//ma.setText(response.body().get(0).getField43());
            }

            @Override
            public void onFailure(Call<List<MarksData>> call, Throwable t) {

            }
        });
       /* Call<List<MarksData>> dataCall = retroGet.getMarksData(BRANCH,API_KEY,q,f);

        dataCall.enqueue(new Callback<List<MarksData>>() {
            @Override
            public void onResponse(Call<List<MarksData>> call, Response<List<MarksData>> response) {
                List<MarksData> data=response.body();
engi.setText(data.get(0).getField42());
engex.setText(data.get(0).getField52());
engtotal.setText(data.get(0).getField62());
mai.setText(data.get(0).getField43());
maex.setText(data.get(0).getField53());
matotal.setText(data.get(0).getField63());

            }

            @Override
            public void onFailure(Call<List<MarksData>> call, Throwable t) {

            }
        });*/
        return v;
    }

}
