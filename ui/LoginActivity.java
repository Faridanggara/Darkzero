package com.darkzero.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.darkzero.R;
import com.darkzero.services.PterodactylService;
import okhttp3.*;
import org.json.JSONObject;
import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private TextView tvStatus;
    private OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvStatus = findViewById(R.id.tvStatus);
        client = new OkHttpClient();

        btnLogin.setOnClickListener(v -> {
            String user = etUsername.getText().toString();
            String pass = etPassword.getText().toString();
            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Isi semua bos!", Toast.LENGTH_SHORT).show();
                return;
            }
            loginRequest(user, pass);
        });
    }

    private void loginRequest(String username, String password) {
        try {
            JSONObject json = new JSONObject();
            json.put("username", username);
            json.put("password", password);

            RequestBody body = RequestBody.create(
                    json.toString(),
                    MediaType.parse("application/json; charset=utf-8")
            );

            Request request = new Request.Builder()
                    .url(PterodactylService.PANEL_URL + "/login")
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(() -> {
                        tvStatus.setText("❌ Gagal: " + e.getMessage());
                        Toast.makeText(LoginActivity.this, "Cek koneksi!", Toast.LENGTH_SHORT).show();
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String res = response.body().string();
                    runOnUiThread(() -> {
                        try {
                            JSONObject obj = new JSONObject(res);
                            if (obj.getBoolean("success")) {
                                String role = obj.getString("role");
                                String expired = obj.getString("expired");
                                String status = obj.getString("status");

                                if (status.equals("banned")) {
                                    tvStatus.setText("🚫 Akun dibanned bos! Kontak admin.");
                                    Toast.makeText(LoginActivity.this, "BANNED!", Toast.LENGTH_LONG).show();
                                    return;
                                }

                                if (status.equals("nonaktif")) {
                                    tvStatus.setText("⛔ Akun dinonaktifkan. Hubungi admin.");
                                    Toast.makeText(LoginActivity.this, "NONAKTIF!", Toast.LENGTH_LONG).show();
                                    return;
                                }

                                SharedPreferences prefs = getSharedPreferences("darkzero", MODE_PRIVATE);
                                prefs.edit()
                                        .putString("username", username)
                                        .putString("role", role)
                                        .putString("expired", expired)
                                        .putString("status", status)
                                        .apply();

                                tvStatus.setText("✅ Login sukses! Role: " + role);
                                Toast.makeText(LoginActivity.this, "Selamat datang " + username + "! 😹", Toast.LENGTH_LONG).show();

                                startActivity(new Intent(LoginActivity.this, MenuActivity.class));
                                finish();
                            } else {
                                tvStatus.setText("❌ " + obj.getString("message"));
                                Toast.makeText(LoginActivity.this, "Login gagal!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            tvStatus.setText("❌ Error: " + e.getMessage());
                        }
                    });
                }
            });
        } catch (Exception e) {
            tvStatus.setText("❌ Error: " + e.getMessage());
        }
    }
}