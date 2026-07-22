package com.darkzero;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.darkzero.ui.LoginActivity;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}