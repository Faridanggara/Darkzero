package com.darkzero.ui;

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

public class SenderActivity extends AppCompatActivity {

    private EditText etNomor, etKode;
    private Button btnMintaKode, btnVerifikasi, btnTautkan;
    private TextView tvStatus;
    private OkHttpClient client;
    private String sessionId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sender);

        etNomor = findViewById(R.id.etNomor);
        etKode = findViewById(R.id.etKode);
        btnMintaKode = findViewById(R.id.btnMintaKode);
        btnVerifikasi = findViewById(R.id.btnVerifikasi);
        btnTautkan = findViewById(R.id.btnTautkan);
        tvStatus = findViewById(R.id.tvStatus);
        client = new OkHttpClient();

        btnMintaKode.setOnClickListener(v -> {
            String nomor = etNomor.getText().toString();
            if (nomor.isEmpty()) {
                Toast.makeText(this, "Isi nomor dulu bos!", Toast.LENGTH_SHORT).show();
                return;
            }
            mintaKode(nomor);
        });

        btnVerifikasi.setOnClickListener(v -> {
            String kode = etKode.getText().toString();
            if (kode.isEmpty()) {
                Toast.makeText(this, "Isi kode bos!", Toast.LENGTH_SHORT).show();
                return;
            }
            verifikasiKode(kode);
        });

        btnTautkan.setOnClickListener(v -> {
            if (sessionId.isEmpty()) {
                Toast.makeText(this, "Verifikasi dulu bos!", Toast.LENGTH_SHORT).show();
                return;
            }
            tautkanPerangkat();
        });
    }

    private void mintaKode(String nomor) {
        try {
            JSONObject json = new JSONObject();
            json.put("nomor", nomor);
            json.put("action", "request_code");

            RequestBody body = RequestBody.create(
                    json.toString(),
                    MediaType.parse("application/json; charset=utf-8")
            );

            Request request = new Request.Builder()
                    .url(PterodactylService.PANEL_URL + "/sender/request")
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(() -> {
                        tvStatus.setText("❌ Gagal minta kode: " + e.getMessage());
                        Toast.makeText(SenderActivity.this, "Cek koneksi!", Toast.LENGTH_SHORT).show();
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String res = response.body().string();
                    runOnUiThread(() -> {
                        try {
                            JSONObject obj = new JSONObject(res);
                            if (obj.getBoolean("success")) {
                                sessionId = obj.getString("session_id");
                                tvStatus.setText("📲 Kode dikirim ke " + nomor + ". Cek WA!");
                                Toast.makeText(SenderActivity.this, "Cek WA bos!", Toast.LENGTH_LONG).show();
                            } else {
                                tvStatus.setText("❌ " + obj.getString("message"));
                            }
                        } catch (Exception e) {
                            tvStatus.setText("❌ Error parse: " + e.getMessage());
                        }
                    });
                }
            });
        } catch (Exception e) {
            tvStatus.setText("❌ Error: " + e.getMessage());
        }
    }

    private void verifikasiKode(String kode) {
        try {
            JSONObject json = new JSONObject();
            json.put("session_id", sessionId);
            json.put("kode", kode);

            RequestBody body = RequestBody.create(
                    json.toString(),
                    MediaType.parse("application/json; charset=utf-8")
            );

            Request request = new Request.Builder()
                    .url(PterodactylService.PANEL_URL + "/sender/verify")
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(() -> tvStatus.setText("❌ Gagal verifikasi: " + e.getMessage()));
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String res = response.body().string();
                    runOnUiThread(() -> {
                        try {
                            JSONObject obj = new JSONObject(res);
                            if (obj.getBoolean("success")) {
                                tvStatus.setText("✅ Kode valid! Klik TAUTKAN PERANGKAT.");
                                Toast.makeText(SenderActivity.this, "Kode OK!", Toast.LENGTH_SHORT).show();
                            } else {
                                tvStatus.setText("❌ Kode salah bos!");
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

    private void tautkanPerangkat() {
        try {
            JSONObject json = new JSONObject();
            json.put("session_id", sessionId);
            json.put("action", "pair");

            RequestBody body = RequestBody.create(
                    json.toString(),
                    MediaType.parse("application/json; charset=utf-8")
            );

            Request request = new Request.Builder()
                    .url(PterodactylService.PANEL_URL + "/sender/pair")
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(() -> tvStatus.setText("❌ Gagal tautkan: " + e.getMessage()));
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String res = response.body().string();
                    runOnUiThread(() -> {
                        try {
                            JSONObject obj = new JSONObject(res);
                            if (obj.getBoolean("success")) {
                                tvStatus.setText("✅ Perangkat tertaut! Siap kirim bug.");
                                Toast.makeText(SenderActivity.this, "Berhasil bos! 😹", Toast.LENGTH_LONG).show();
                            } else {
                                tvStatus.setText("❌ Gagal tautkan: " + obj.getString("message"));
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