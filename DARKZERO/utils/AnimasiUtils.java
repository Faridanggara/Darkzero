package com.darkzero.utils;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.content.Context;
import com.darkzero.R;

public class AnimasiUtils {
    public static void startWaveAnimation(Context context, ImageView imageView) {
        Animation waveAnim = AnimationUtils.loadAnimation(context, R.anim.wave_anim);
        imageView.startAnimation(waveAnim);
    }
}