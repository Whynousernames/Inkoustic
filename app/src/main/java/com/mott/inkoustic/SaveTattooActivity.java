package com.mott.inkoustic;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class SaveTattooActivity extends AppCompatActivity {

    Uri selectedImage, selectedAudio;
    Bitmap bitmap;
    MediaPlayer mediaPlayer = new MediaPlayer();
    int countLength;


    public void getPhoto()
    {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,1);
    }

    public void getAudio()
    {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 2);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 1)
        {
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                getPhoto();
            }
        }
        else if (requestCode == 2)
        {
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                getAudio();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_tattoo);

        List<String> fileNames = new ArrayList<>();
        File folder = new File(Environment.getExternalStorageDirectory(), "InkousticImages");
        if (!folder.exists()) folder.mkdir();
        for (File file : folder.listFiles()) {
            String filename = file.getName().toLowerCase();
            if (filename.endsWith(".jpg") || filename.endsWith("jpeg")) {
                fileNames.add(filename);
            }
        }

        countLength = fileNames.size();




    }

    public void saveMedia(View view)
    {
        saveMedia();
    }
    public void selectImage(View view)
    {

        mediaPlayer.stop();
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }else
        {
            getPhoto();

        }
    }

    public void selectAudio(View view)
    {
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
        }else
        {

            getAudio();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1  && data != null)
        {
             selectedImage = data.getData();

            try {
                 bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);

                ImageView imageView = (ImageView) findViewById(R.id.imageView);
                imageView.setImageBitmap(bitmap);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(requestCode == 2 && data !=null)
        {
            selectedAudio = data.getData();


            try {
                mediaPlayer.setDataSource(getApplicationContext(), selectedAudio);
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void help(View view)
    {
        Toast.makeText(SaveTattooActivity.this, "Please select and Image and Audio and click 'Save Media' to save your tattoo", Toast.LENGTH_LONG).show();
    }

    public void saveMedia()
    {
        File directory = new File(Environment.getExternalStorageDirectory()+ "/InkousticImages/");
        directory.mkdirs();






        String fName = "Image" +(countLength+1) + ".jpg" ;
        File file = new File(directory, fName);






        if(file.exists()) file.delete();
        try
        {
            FileOutputStream out = new FileOutputStream(file);

            bitmap.compress(Bitmap.CompressFormat.JPEG,50,out);


            out.flush();
            out.close();





        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }









}
