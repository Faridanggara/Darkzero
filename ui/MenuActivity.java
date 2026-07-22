package com.darkzero.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.darkzero.R;
import com.darkzero.services.PterodactylService;

public class MenuActivity extends AppCompatActivity {

    private TextView tvUser, tvExpired, tvRole;
    private Button btnSignUp, btnIklan, btnBug, btnSender, btnInfo, btnSettings, btnAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        tvUser = findViewById(R.id.tvUser);
        tvExpired = findViewById(R.id.tvExpired);
        tvRole = findViewById(R.id.tvRole);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnIklan = findViewById(R.id.btnIklan);
        btnBug = findViewById(R.id.btnBug);
        btnSender = findViewById(R.id.btnSender);
        btnInfo = findViewById(R.id.btnInfo);
        btnSettings = findViewById(R.id.btnSettings);
        btnAdmin = findViewById(R.id.btnAdmin);

        loadUserData();

        btnSignUp.setOnClickListener(v -> startActivity(new Intent(MenuActivity.this, SignUpActivity.class)));
        btnIklan.setOnClickListener(v -> startActivity(new Intent(MenuActivity.this, IklanDonasiActivity.class)));
        btnBug.setOnClickListener(v -> startActivity(new Intent(MenuActivity.this, BugMenuActivity.class)));
        btnSender.setOnClickListener(v -> startActivity(new Intent(MenuActivity.this, SenderActivity.class)));
        btnInfo.setOnClickListener(v -> startActivity(new Intent(MenuActivity.this, InfoUserActivity.class)));
        btnSettings.setOnClickListener(v -> startActivity(new Intent(MenuActivity.this, SettingsActivity.class)));
        btnAdmin.setOnClickListener(v -> startActivity(new Intent(MenuActivity.this, AdminPanelActivity.class)));
    }

    private void loadUserData() {
        PterodactylService service = new PterodactylService();
        tvUser.setText("👤 " + service.getUser(this));
        tvExpired.setText("⏳ Expired: " + service.getExpired(this));
        tvRole.setText("🎖️ Role: " + service.getRole(this));
    }
}