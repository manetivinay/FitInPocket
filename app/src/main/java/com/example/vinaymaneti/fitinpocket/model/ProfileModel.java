package com.example.vinaymaneti.fitinpocket.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by VinayManeti on 25/04/17.
 */

public class ProfileModel implements Parcelable {
    private String name;
    private String gender;
    private int age_years;
    private int age_months;
    private int current_weight;
    private int target_weight;
    private String date_time_created;
    private int id;


    protected ProfileModel(Parcel in) {
        name = in.readString();
        gender = in.readString();
        age_years = in.readInt();
        age_months = in.readInt();
        current_weight = in.readInt();
        target_weight = in.readInt();
        date_time_created = in.readString();
    }

    public static final Creator<ProfileModel> CREATOR = new Creator<ProfileModel>() {
        @Override
        public ProfileModel createFromParcel(Parcel in) {
            return new ProfileModel(in);
        }

        @Override
        public ProfileModel[] newArray(int size) {
            return new ProfileModel[size];
        }
    };

    public ProfileModel(String date_time_created, String name, String gender, int age_years, int age_months, int current_weight, int target_weight) {
        this.date_time_created = date_time_created;
        this.name = name;
        this.gender = gender;
        this.age_years = age_years;
        this.age_months = age_months;
        this.current_weight = current_weight;
        this.target_weight = target_weight;

    }

    public ProfileModel() {

    }

    public ProfileModel(int id, String name, String gender, int age_years, int age_months, int current_weight, int target_weight, String date_time_created) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age_years = age_years;
        this.age_months = age_months;
        this.current_weight = current_weight;
        this.target_weight = target_weight;
        this.date_time_created = date_time_created;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(gender);
        dest.writeInt(age_years);
        dest.writeInt(age_months);
        dest.writeInt(current_weight);
        dest.writeInt(target_weight);
        dest.writeString(date_time_created);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge_years() {
        return age_years;
    }

    public void setAge_years(int age_years) {
        this.age_years = age_years;
    }

    public int getAge_months() {
        return age_months;
    }

    public void setAge_months(int age_months) {
        this.age_months = age_months;
    }

    public int getCurrent_weight() {
        return current_weight;
    }

    public void setCurrent_weight(int current_weight) {
        this.current_weight = current_weight;
    }

    public int getTarget_weight() {
        return target_weight;
    }

    public void setTarget_weight(int target_weight) {
        this.target_weight = target_weight;
    }

    public String getDate_time_created() {
        return date_time_created;
    }

    public void setDate_time_created(String date_time_created) {
        this.date_time_created = date_time_created;
    }
}
