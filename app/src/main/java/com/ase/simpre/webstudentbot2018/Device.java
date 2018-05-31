package com.ase.simpre.webstudentbot2018;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Device implements Parcelable{
    @JsonProperty("androidId")
    private String androidId;
    @JsonProperty("id")
    private String userId;

    public Device(String androidId, String userId) {
        this.androidId = androidId;
        this.userId = userId;
    }

    public Device() {

    }

    public Device(String androidId) {
        this.androidId = androidId;
    }

    public String getAndroidId() {
        return androidId;
    }

    public void setAndroidId(String androidId) {
        this.androidId = androidId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(androidId);
        out.writeString(userId);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Device> CREATOR = new Parcelable.Creator<Device>() {
        public Device createFromParcel(Parcel in) {
            return new Device(in);
        }

        public Device[] newArray(int size) {
            return new Device[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Device(Parcel in) {
        androidId = in.readString();
        userId = in.readString();
    }
}
