package com.ase.simpre.webstudentbot2018;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
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
}
