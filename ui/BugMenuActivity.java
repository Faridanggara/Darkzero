package com.darkzero.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.darkzero.R;
import com.darkzero.services.BugService;

public class BugMenuActivity extends AppCompatActivity {

    private EditText etSpamText, etTarget;
    private Button btnCrash, btnFreeze, btnSpamText, btnSpamOtp, btnBannedGroup, btnBannedChannel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bug_menu);

        etSpamText = findViewById(R.id.etSpamText);
        etTarget = findViewById(R.id.etTarget);
        btnCrash = findViewById(R.id.btnCrash);
        btnFreeze = findViewById(R.id.btnFreeze);
        btnSpamText = findViewById(R.id.btnSpamText);
        btnSpamOtp = findViewById(R.id.btnSpamOtp);
        btnBannedGroup = findViewById(R.id.btnBannedGroup);
        btnBannedChannel = findViewById(R.id.btnBannedChannel);

        BugService bugService = new BugService(this);

        btnCrash.setOnClickListener(v -> {
            String target = etTarget.getText().toString();
            if (target.isEmpty()) {
                Toast.makeText(this, "Isi target dulu bos!", Toast.LENGTH_SHORT).show();
                return;
            }
            bugService.executeCrash(target);
            Toast.makeText(this, "🚀 Crash payload dikirim!", Toast.LENGTH_SHORT).show();
        });

        btnFreeze.setOnClickListener(v -> {
            String target = etTarget.getText().toString();
            if (target.isEmpty()) {
                Toast.makeText(this, "Isi target dulu bos!", Toast.LENGTH_SHORT).show();
                return;
            }
            bugService.executeFreeze(target);
            Toast.makeText(this, "❄️ Freeze payload dikirim!", Toast.LENGTH_SHORT).show();
        });

        btnSpamText.setOnClickListener(v -> {
            String target = etTarget.getText().toString();
            String text = etSpamText.getText().toString();
            if (target.isEmpty() || text.isEmpty()) {
                Toast.makeText(this, "Isi target dan teks spam!", Toast.LENGTH_SHORT).show();
                return;
            }
            bugService.executeSpamText(target, text);
            Toast.makeText(this, "📨 Spam text dikirim!", Toast.LENGTH_SHORT).show();
        });

        btnSpamOtp.setOnClickListener(v -> {
            String target = etTarget.getText().toString();
            if (target.isEmpty()) {
                Toast.makeText(this, "Isi target dulu bos!", Toast.LENGTH_SHORT).show();
                return;
            }
            bugService.executeSpamOtp(target);
            Toast.makeText(this, "🔐 Spam OTP dikirim!", Toast.LENGTH_SHORT).show();
        });

        btnBannedGroup.setOnClickListener(v -> {
            String target = etTarget.getText().toString();
            if (target.isEmpty()) {
                Toast.makeText(this, "Isi target group!", Toast.LENGTH_SHORT).show();
                return;
            }
            bugService.executeBannedGroup(target);
            Toast.makeText(this, "🚫 Banned group executed!", Toast.LENGTH_SHORT).show();
        });

        btnBannedChannel.setOnClickListener(v -> {
            String target = etTarget.getText().toString();
            if (target.isEmpty()) {
                Toast.makeText(this, "Isi target channel!", Toast.LENGTH_SHORT).show();
                return;
            }
            bugService.executeBannedChannel(target);
            Toast.makeText(this, "📢 Banned channel executed!", Toast.LENGTH_SHORT).show();
        });
    }
}