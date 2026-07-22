package com.darkzero.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.darkzero.R;
import com.darkzero.services.PterodactylService;

public class SignUpActivity extends AppCompatActivity {

    private EditText etUsername, etPassword, etKey;
    private Button btnRegister, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etKey = findViewById(R.id.etKey);
        btnRegister = findViewById(R.id.btnRegister);
        btnBack = findViewById(R.id.btnBack);

        btnRegister.setOnClickListener(v -> {
            String user = etUsername.getText().toString();
            String pass = etPassword.getText().toString();
            String key = etKey.getText().toString();

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Isi semua bos!", Toast.LENGTH_SHORT).show();
                return;
            }

            PterodactylService service = new PterodactylService();
            boolean success = service.registerUser(this, user, pass, key);
            if (success) {
                Toast.makeText(this, "Registrasi berhasil bos!", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(this, "Gagal registrasi. Cek panel.", Toast.LENGTH_SHORT).show();
            }
        });

        btnBack.setOnClickListener(v -> finish());
    }
}