package com.darkzero.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.darkzero.R;
import com.darkzero.services.PterodactylService;
import okhttp3.*;
import org.json.JSONObject;
import java.io.IOException;

public class AdminPanelActivity extends AppCompatActivity {

    private EditText etTargetUser, etExpiredDays;
    private Spinner spinnerRole;
    private Button btnBan, btnUnban, btnNonaktif, btnSetRole, btnSetExpired, btnRefresh;
    private TextView tvLog;
    private OkHttpClient client;
    private String selectedRole = "Free";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        etTargetUser = findViewById(R.id.etTargetUser);
        etExpiredDays = findViewById(R.id.etExpiredDays);
        spinnerRole = findViewById(R.id.spinnerRole);
        btnBan = findViewById(R.id.btnBan);
        btnUnban = findViewById(R.id.btnUnban);
        btnNonaktif = findViewById(R.id.btnNonaktif);
        btnSetRole = findViewById(R.id.btnSetRole);
        btnSetExpired = findViewById(R.id.btnSetExpired);
        btnRefresh = findViewById(R.id.btnRefresh);
        tvLog = findViewById(R.id.tvLog);
        client = new OkHttpClient();

        String[] roles = {"Free", "Premium", "Reseller", "Admin"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRole.setAdapter(adapter);

        spinnerRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedRole = roles[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        btnBan.setOnClickListener(v -> {
            String user = etTargetUser.getText().toString();
            if (user.isEmpty()) {
                Toast.makeText(this, "Isi target user!", Toast.LENGTH_SHORT).show();
                return;
            }
            executeAdminAction("ban", user, "");
        });

        btnUnban.setOnClickListener(v -> {
            String user = etTargetUser.getText().toString();
            if (user.isEmpty()) {
                Toast.makeText(this, "Isi target user!", Toast.LENGTH_SHORT).show();
                return;
            }
            executeAdminAction("unban", user, "");
        });

        btnNonaktif.setOnClickListener(v -> {
            String user = etTargetUser.getText().toString();
            if (user.isEmpty()) {
                Toast.makeText(this, "Isi target user!", Toast.LENGTH_SHORT).show();
                return;
            }
            executeAdminAction("nonaktif", user, "");
        });

        btnSetRole.setOnClickListener(v -> {
            String user = etTargetUser.getText().toString();
            if (user.isEmpty()) {
                Toast.makeText(this, "Isi target user!", Toast.LENGTH_SHORT).show();
                return;
            }
            executeAdminAction("set_role", user, selectedRole);
        });

        btnSetExpired.setOnClickListener(v -> {
            String user = etTargetUser.getText().toString();
            String days = etExpiredDays.getText().toString();
            if (user.isEmpty() || days.isEmpty()) {
                Toast.makeText(this, "Isi user dan expired days!", Toast.LENGTH_SHORT).show();
                return;
            }
            executeAdminAction("set_expired", user, days);
        });

        btnRefresh.setOnClickListener(v -> tvLog.setText("> Log cleared."));
    }

    private void executeAdminAction(String action, String target, String value) {
        try {
            JSONObject json = new JSONObject();
            json.put("action", action);
            json.put("target", target);
            json.put("value", value);
            json.put("admin_key", PterodactylService.getAdminKey(this));

            RequestBody body = RequestBody.create(
                    json.toString(),
                    MediaType.parse("application/json; charset=utf-8")
            );

            Request request = new Request.Builder()
                    .url(PterodactylService.PANEL_URL + "/admin/action")
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(() -> {
                        tvLog.append("\n> ❌ Gagal: " + e.getMessage());
                        Toast.makeText(AdminPanelActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String res = response.body().string();
                    runOnUiThread(() -> {
                        try {
                            JSONObject obj = new JSONObject(res);
                            String msg = obj.getString("message");
                            tvLog.append("\n> ✅ " + action.toUpperCase() + ": " + target + " -> " + msg);
                            Toast.makeText(AdminPanelActivity.this, msg, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            tvLog.append("\n> ❌ Parse error: " + e.getMessage());
                        }
                    });
                }
            });
        } catch (Exception e) {
            tvLog.append("\n> ❌ Error: " + e.getMessage());
        }
    }
}