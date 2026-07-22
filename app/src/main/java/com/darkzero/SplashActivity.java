package com.darkzero;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.darkzero.ui.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private ImageView bannerImage, waveImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        bannerImage = findViewById(R.id.bannerImage);
        waveImage = findViewById(R.id.waveImage);
        progressBar = findViewById(R.id.progressBar);

        Glide.with(this)
                .load("https://cdn.phototourl.com/free/2026-07-16-3011babf-828a-490d-ba12-26d291abe6b1.png")
                .into(bannerImage);

        Animation waveAnim = AnimationUtils.loadAnimation(this, R.anim.wave_anim);
        waveImage.startAnimation(waveAnim);

        new Handler().postDelayed(() -> progressBar.setProgress(50), 1000);
        new Handler().postDelayed(() -> progressBar.setProgress(100), 2500);
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        }, 3500);
    }
}