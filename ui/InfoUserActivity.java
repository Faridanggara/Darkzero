package com.darkzero.ui;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.darkzero.R;
import com.darkzero.services.PterodactylService;

public class InfoUserActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_user);

        PterodactylService service = new PterodactylService();
        ((TextView) findViewById(R.id.tvInfoUser)).setText("👤 User: " + service.getUser(this));
        ((TextView) findViewById(R.id.tvInfoExpired)).setText("⏳ Expired: " + service.getExpired(this));
        ((TextView) findViewById(R.id.tvInfoRole)).setText("🎖️ Role: " + service.getRole(this));
        ((TextView) findViewById(R.id.tvInfoLogo)).setText("🖼️ Logo: DARK ZERO");
        ((TextView) findViewById(R.id.tvInfoWa)).setText("📱 WA: 08819633945 (klik)");
    }
}