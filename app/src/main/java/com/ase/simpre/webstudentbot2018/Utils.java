package com.ase.simpre.webstudentbot2018;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Utils {
    OkHttpClient client = new OkHttpClient();
    ObjectMapper objectMapper = new ObjectMapper();
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private Response run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response;
    }

    private Response run(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }

    public String saveUser(User user) {
        try {
            Response response = run("http://192.168.100.2:8082/save", objectMapper.writeValueAsString(user));
            if(response.isSuccessful()) {
                return "OK";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "NOK";
    }

    public User getUser(String id) {
        try {
            Response response = run("http://192.168.100.2:8082/getUser/" + id);
            return objectMapper.readValue(response.body().byteStream(), User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getUsers() {
        try {
            Response response = run("http://192.168.100.2:8082/getUsers");
            List<User> users = objectMapper.readValue(response.body().byteStream(), objectMapper.getTypeFactory().constructCollectionType(List.class, User.class));
            return users;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }
}
