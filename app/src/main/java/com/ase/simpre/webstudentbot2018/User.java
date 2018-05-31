package com.ase.simpre.webstudentbot2018;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class User implements Parcelable{
    @JsonProperty("id")
    private String id;
    @JsonProperty("email")
    private String email;
    @JsonProperty("deviceList")
    private List<Device> deviceList;

    public User() {
    }

    public User(String id, String email, List<Device> deviceList) {
        this.id = id;
        this.email = email;
        this.deviceList = deviceList;
    }

    public User(String email, List<Device> deviceList) {
        this.email = email;
        this.deviceList = deviceList;
    }

    public User(String email, String deviceId) {
        this.email = email;
        if (this.deviceList == null) {
            this.deviceList = new ArrayList<>();
        }
        Device device = new Device(deviceId);
        this.deviceList.add(device);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Device> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<Device> deviceList) {
        this.deviceList = deviceList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(id);
        out.writeString(email);
        out.writeList(deviceList);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private User(Parcel in) {
        id = in.readString();
        email = in.readString();
        deviceList = new ArrayList<>();
        in.readList(deviceList, Device.class.getClassLoader());
    }
}
