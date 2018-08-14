package com.mott.inkoustic;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.Toast;

import rm.com.audiowave.AudioWaveView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import rm.com.audiowave.OnSamplingListener;


public class HomeActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

    }

    public void showScanTattooActivity()
    {
        Intent intent = new Intent(getApplicationContext(), ScanTattooActivity.class);
        startActivity(intent);
    }

    public void showWaveDrawActivity()
    {
        Intent intent = new Intent(this, WaveDrawActivity.class);

        startActivity(intent);

    }

    public void scanTattoo(View view)
    {
        showScanTattooActivity();
    }

    public void waveDraw(View view)
    {
        showWaveDrawActivity();
    }





}
