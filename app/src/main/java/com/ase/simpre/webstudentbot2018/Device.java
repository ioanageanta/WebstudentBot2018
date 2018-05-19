package com.ase.simpre.webstudentbot2018;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Device {
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
}
