package com.darkzero.services;

import android.content.Context;
import android.content.SharedPreferences;
import okhttp3.*;
import org.json.JSONObject;
import java.io.IOException;

public class PterodactylService {
    public static final String PANEL_URL = "https://panelmu.com/api";
    private static final String API_KEY = "ptla_xxxxxxxxxxxxx";
    private OkHttpClient client;

    public PterodactylService() {
        this.client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request().newBuilder()
                                .addHeader("Authorization", "Bearer " + API_KEY)
                                .addHeader("Accept", "application/json")
                                .addHeader("Content-Type", "application/json")
                                .build();
                        return chain.proceed(request);
                    }
                })
                .build();
    }

    public static String getAdminKey(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("settings", MODE_PRIVATE);
        return prefs.getString("admin_key", "admin123");
    }

    public static String getRole(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("darkzero", MODE_PRIVATE);
        return prefs.getString("role", "Free");
    }

    public static String getExpired(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("darkzero", MODE_PRIVATE);
        return prefs.getString("expired", "N/A");
    }

    public static String getStatus(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("darkzero", MODE_PRIVATE);
        return prefs.getString("status", "active");
    }

    public static boolean isAdmin(Context context) {
        return getRole(context).equals("Admin");
    }

    public String getUser(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("darkzero", MODE_PRIVATE);
        return prefs.getString("username", "Guest");
    }

    public boolean registerUser(Context context, String username, String password, String key) {
        try {
            JSONObject json = new JSONObject();
            json.put("username", username);
            json.put("password", password);
            json.put("key", key);

            RequestBody body = RequestBody.create(
                    json.toString(),
                    MediaType.parse("application/json; charset=utf-8")
            );

            Request request = new Request.Builder()
                    .url(PANEL_URL + "/users")
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                SharedPreferences prefs = context.getSharedPreferences("darkzero", MODE_PRIVATE);
                prefs.edit()
                        .putString("username", username)
                        .putString("expired", "31-12-2026")
                        .putString("role", "User")
                        .apply();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean validateLicense(String key) {
        try {
            Request request = new Request.Builder()
                    .url(PANEL_URL + "/validate?key=" + key)
                    .get()
                    .build();
            Response response = client.newCall(request).execute();
            return response.isSuccessful();
        } catch (Exception e) {
            return false;
        }
    }
}