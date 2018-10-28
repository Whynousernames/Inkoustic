package com.mott.inkoustic

import android.animation.ObjectAnimator
import android.content.Intent
import android.content.res.AssetFileDescriptor
import android.graphics.drawable.BitmapDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.animation.LinearInterpolator
import android.widget.Button
import kotlinx.android.synthetic.main.activity_another.view.*
import rm.com.audiowave.AudioWaveView
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Environment
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_wavedraw.view.*
import kotlinx.android.synthetic.main.item_audio.*
import java.io.File
import java.io.FileOutputStream
import java.util.*

var songCounter = 0


class WaveDrawActivity : AppCompatActivity() {

  private val wave by lazy { findViewById<AudioWaveView>(R.id.wave) }
  private val play by lazy { findViewById<Button>(R.id.play) }
  private val list by lazy { findViewById<Button>(R.id.list_java) }
  private val simple by lazy { findViewById<Button>(R.id.simple_java) }






  private val progressAnim: ObjectAnimator by lazy {
    ObjectAnimator.ofFloat(wave, "progress", 0F, 100F).apply {
      interpolator = LinearInterpolator()
      duration = 1000
    }
  }





  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_wavedraw)

    val songList = assets.list("songs")
    val help = findViewById<Button>(R.id.buttonHelp)


    


    play.setOnClickListener {
      inflateWave()

    }

    list.setOnClickListener{


        if(songCounter<5) {
          songCounter++
          Toast.makeText(this, "Next song selected please click play", Toast.LENGTH_LONG).show()
        }
      else
        {
          Toast.makeText(this, "No more songs available", Toast.LENGTH_LONG).show()
        }

    }
    help.setOnClickListener{

      Toast.makeText(this, "Click play to play the audio and display the waveform, or click next or previous to select another song", Toast.LENGTH_LONG).show()
    }






   simple.setOnClickListener {

     if (songCounter >0)
     {
       songCounter--
       Toast.makeText(this, "Previous song selected please click play", Toast.LENGTH_LONG).show()
     }
     else
     {
       Toast.makeText(this, "You are at the first song, cant go back anymore", Toast.LENGTH_LONG).show()
     }
    }

    wave.onProgressChanged = { progress, byUser ->
      Log.e("wave", "Progress set: $progress, and it's $byUser that user did this")

      if (progress == 100F && !byUser) {
        wave.waveColor = ContextCompat.getColor(this, R.color.colorBlack)
        wave.isTouchable = true
      }
    }

    wave.onStartTracking = {
      Log.e("wave", "Started tracking from: $it")
    }

    wave.onStopTracking = {
      Log.e("wave", "Progress set: $it")
    }

//    wave.onProgressListener = object : OnProgressListener {
//      override fun onProgressChanged(progress: Float, byUser: Boolean) {
//        Log.e("wave", "Progress changed: $progress, and it's $byUser that user did this")
//      }
//
//      override fun onStartTracking(progress: Float) {
//        Log.e("wave", "Started tracking from: $progress")
//      }
//
//      override fun onStopTracking(progress: Float) {
//        Log.e("wave", "Stopped tracking at: $progress")
//      }
//    }
  }

  private fun inflateWave() {


    val songStringList = arrayOf("Gummy.mp3", "sample.wav", "testsong.wav")
    wave.setRawData(assets.open("songs/" +songStringList[songCounter] ).readBytes()) { progressAnim.start() }

  }





}






