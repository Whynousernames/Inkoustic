package com.mott.inkoustic;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Mp3PlayerActivity extends AppCompatActivity {

    private MediaPlayer mp;
    int mp3Index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mp3_player);

        Intent intent = getIntent();
         mp3Index = intent.getIntExtra("matchImageValue", 1);




    }



    public void play(View view)
    {
        List<String> fileNames = new ArrayList<>();
        File folder = new File(Environment.getExternalStorageDirectory(), "InkousticImages");
        if (!folder.exists()) folder.mkdir();
        for (File file : folder.listFiles()) {
            String filename = file.getName().toLowerCase();
            if (filename.endsWith(".wav") || filename.endsWith("mp3")) {
                fileNames.add(filename);
            }
        }

        File mp3File = new File(Environment.getExternalStorageDirectory(), "InkousticImages/" + fileNames.toArray()[mp3Index]);
        mp = MediaPlayer.create(Mp3PlayerActivity.this, Uri.parse(mp3File.toString()) );

        mp.start();
    }

    public void pause (View view)
    {

    }

    public void stop(View view)
    {

    }
}
