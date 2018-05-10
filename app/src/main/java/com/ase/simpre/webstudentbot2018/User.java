package com.ase.simpre.webstudentbot2018;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    @JsonProperty("id")
    private String id;
    @JsonProperty("email")
    private String email;

    public User(String email, String deviceId) {
        this.email = email;
        this.deviceId = deviceId;
    }

    @JsonProperty("deviceId")
    private String deviceId;


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

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
