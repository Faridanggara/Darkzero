package com.darkzero.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SwitchCompat;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.darkzero.R;

public class SettingsActivity extends AppCompatActivity {

    private SwitchCompat swTheme, swAnimasi, swNotif;
    private EditText etPanelUrl, etApiKey;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        swTheme = findViewById(R.id.swTheme);
        swAnimasi = findViewById(R.id.swAnimasi);
        swNotif = findViewById(R.id.swNotif);
        etPanelUrl = findViewById(R.id.etPanelUrl);
        etApiKey = findViewById(R.id.etApiKey);
        btnSave = findViewById(R.id.btnSave);

        loadSettings();

        btnSave.setOnClickListener(v -> {
            SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);
            prefs.edit()
                    .putBoolean("theme_dark", swTheme.isChecked())
                    .putBoolean("animasi", swAnimasi.isChecked())
                    .putBoolean("notif", swNotif.isChecked())
                    .putString("panel_url", etPanelUrl.getText().toString())
                    .putString("api_key", etApiKey.getText().toString())
                    .apply();
            Toast.makeText(this, "Settings saved!", Toast.LENGTH_SHORT).show();
        });
    }

    private void loadSettings() {
        SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);
        swTheme.setChecked(prefs.getBoolean("theme_dark", true));
        swAnimasi.setChecked(prefs.getBoolean("animasi", true));
        swNotif.setChecked(prefs.getBoolean("notif", true));
        etPanelUrl.setText(prefs.getString("panel_url", "https://panelmu.com/api"));
        etApiKey.setText(prefs.getString("api_key", "ptla_xxxxx"));
    }
}