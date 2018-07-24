package com.example.udaysaikumar.clgattendance;
import com.google.gson.annotations.SerializedName;


public class UserDetails {
    @SerializedName("field10")
    public String name;
    @SerializedName("field17")
    public String ssc;
    @SerializedName("field18")
    public String inter;
    @SerializedName("field72")
    public String fys1;
    @SerializedName("field109")
    public String fys2;
    @SerializedName("field150")
    public String sys1;
    @SerializedName("field188")
    public String sys2;
    @SerializedName("field229")
    public String tys1;

    public String getName() {
        return name;
    }

    public String getSsc() {
        return ssc;
    }

    public String getInter() {
        return inter;
    }

    public String getFys1() {
        return fys1;
    }

    public String getFys2() {
        return fys2;
    }

    public String getSys1() {
        return sys1;
    }

    public String getSys2() {
        return sys2;
    }

    public String getTys1() {
        return tys1;
    }
}
