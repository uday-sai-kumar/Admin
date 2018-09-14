package com.example.udaysaikumar.clgattendance.Fragments;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.res.ResourcesCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.udaysaikumar.clgattendance.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;


public class MyDialogFragment extends DialogFragment{
    TableLayout tableAttendance;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_dialog, container, false);
        tableAttendance=v.findViewById(R.id.mydialogtable);
        String json= getArguments() != null ? getArguments().getString("dialog") : null;
        try {


            Typeface typeface = ResourcesCompat.getFont(v.getContext(), R.font.open_sans);
            JSONArray jsonArray=new JSONArray(json);
            JSONObject jsonObject=jsonArray.getJSONObject(0);
            JSONObject jsonObject1=jsonObject.getJSONObject("attend");
            System.out.println("hihihihi"+jsonObject1.toString());
            Iterator<String> iterator=jsonObject1.keys();


            while (iterator.hasNext()) {
                String key=iterator.next();

                                TableRow tableRow = new TableRow(v.getContext());
                                tableRow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                                TextView t = new TextView(v.getContext());
                                t.setText(key);
                                t.setTypeface(typeface);
                                t.setPadding(2,2,2,2);
                                t.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                               // t.setBackgroundResource(R.drawable.table_custom_text_conclusion);
                                t.setGravity(Gravity.CENTER);
                                tableRow.addView(t);
                                ImageView imageView=new ImageView(v.getContext());
                                imageView.setPadding(2,2,2,2);
                                        if(jsonObject1.get(key).toString().equals("P"))
                                        {
                                            imageView.setImageResource(R.drawable.present);
                                            }
                                            else imageView.setImageResource(R.drawable.absent);
                             /*   TextView textView1=new TextView(v.getContext());
                                textView1.setText(jsonObject1.get(key).toString());
                                textView1.setTypeface(typeface);
                                textView1.setGravity(Gravity.CENTER);
                                textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
                                textView1.setBackgroundResource(R.drawable.table_custom_text_conclusion);*/
                                tableRow.addView(imageView);
                                tableAttendance.addView(tableRow,new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }



        } catch (JSONException e) {
            TextView t=v.findViewById(R.id.mytext);
            t.setPadding(2,2,2,2);
            t.setText(getResources().getText(R.string.sorry));
            t.setTextColor(getResources().getColor(R.color.bad));
            Typeface typeface = ResourcesCompat.getFont(v.getContext(), R.font.open_sans);
            t.setTypeface(typeface);
            t.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            t.setGravity(Gravity.CENTER);

        }
        return v;
    }


}
