package com.ase.simpre.webstudentbot2018;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Utils {
    OkHttpClient client = new OkHttpClient().newBuilder().followRedirects(true).followSslRedirects(true).build();
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

    public boolean canBeRegistered(String androidId) {
        try {
            Response response = run("http://webstudentbot2018.herokuapp.com/getDevice/" + androidId);
            if(response.body().string()=="false") {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String saveUser(User user) {
        try {
            Response response = run("http://webstudentbot2018.herokuapp.com/save", objectMapper.writeValueAsString(user));
            if (response.isSuccessful()) {
                return "OK";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "NOK";
    }

    public User getUser(String id) {
        try {
            Response response = run("https://webstudentbot2018.herokuapp.com/getUser/" + id);
            return objectMapper.readValue(response.body().byteStream(), User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User authorize(String deviceId) {
        try {
            Response response = run("https://webstudentbot2018.herokuapp.com/authorize/" + deviceId);

            return objectMapper.readValue(response.body().byteStream(), User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getUsers() {
        try {
            Response response = run("https://webstudentbot2018.herokuapp.com/getUsers");
            List<User> users = objectMapper.readValue(response.body().byteStream(), objectMapper.getTypeFactory().constructCollectionType(List.class, User.class));
            return users;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public int getGrade(String email, String subject) {
        try {
            Response response = run("https://mockplatform.herokuapp.com/getGrade/" + email +"/" + subject);
            if(response.isSuccessful() && response.body().contentLength() <= 2) {
                int grade = Integer.valueOf(response.body().string());
                Log.e("RESPONSE:::", String.valueOf(grade));
                return grade;
            } else {
                return 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
