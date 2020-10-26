package com.br.arley.spotifyclone;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


public class MainActivity extends AppCompatActivity {

    ImageView coverImage;
    MediaPlayer mediaPlayer;
    ImageButton btPlay, btNext, btPrevious;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btNext = findViewById(R.id.bt_next);
        btPrevious = findViewById(R.id.bt_previous);

        coverImage = findViewById(R.id.iv_capa);
        Glide.with(this).load("https://img.youtube.com/vi/BJFg2W6kaZM/0.jpg").into(coverImage);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.chega_de_saudade);

        mediaPlayer.start();
    }

    public void play(View view){
        if (mediaPlayer == null){
            return;
        }
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            ((ImageButton)view).setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_play));

        }else{
            mediaPlayer.start();
            ((ImageButton)view).setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();

    }
}