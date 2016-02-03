package com.xipsoft.currency.app.value_objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yinka on 2/3/16.
 */
public class Currency implements Parcelable {
    private double rate;
    private String date;
    private String base;
    private String name;
    private long id;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(rate);
        dest.writeString(date);
        dest.writeString(base);
        dest.writeString(name);
        dest.writeLong(id);
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
