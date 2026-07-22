package com.darkzero.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.darkzero.R;

public class IklanDonasiActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iklan_donasi);

        TextView tvDonasi = findViewById(R.id.tvDonasi);
        Button btnDonasi = findViewById(R.id.btnDonasi);

        tvDonasi.setText("💰 Support DARK ZERO\n\n" +
                "Donasi via:\n" +
                "• Dana: 08819633945\n" +
                "• OVO: 08819633945\n" +
                "• QRIS: (scan di menu)\n\n" +
                "Support biar makin update! 😹🖕🏿");

        btnDonasi.setOnClickListener(v ->
            Toast.makeText(this, "Terima kasih bos! 😹", Toast.LENGTH_LONG).show()
        );
    }
}