package com.example.mymusic;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    MediaPlayer music;
    AudioManager audioManager;

    public void play(View view){
        music.start();
    }

    public void pause(View view){
        music.pause();
    }

    public void stop(View view){
        music.stop();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        music = MediaPlayer.create(this, R.raw.music);

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        int volmax = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int volcur = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        SeekBar volseek = findViewById(R.id.seekvol);
        volseek.setMax(volmax);
        volseek.setProgress(volcur);

        volseek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        SeekBar songprogseek = findViewById(R.id.seeksongprog);
        songprogseek.setMax(music.getDuration());

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                songprogseek.setProgress(music.getCurrentPosition());
            }
        }, 0, 100);

        songprogseek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                music.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}