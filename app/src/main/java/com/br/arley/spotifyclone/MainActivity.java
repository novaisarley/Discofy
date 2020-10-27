package com.br.arley.spotifyclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    ImageView coverImage;
    MediaPlayer mediaPlayer;
    TextView tvPlaylist, tvName, tvAuthor, tvPosition;
    ImageButton btPlay, btNext, btPrevious;
    SeekBar sbVolume;
    List<Song> songList;
    int numSong = 0;

    AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = new MediaPlayer();


        songList = new ArrayList<>();

        Song song = new Song("https://img.youtube.com/vi/DPgE7PNzXag/maxresdefault.jpg", "Non-Stop", "Original Broadway Cast of Hamilton", "Hamilton", R.raw.non_stop);
        Song song1 = new Song("https://img.youtube.com/vi/tlp8iY4g--4/maxresdefault.jpg", "Chega de Saudade", "João Gilberto", "Chega de Saudade", R.raw.chega_de_saudade);
        Song song2 = new Song("https://img.youtube.com/vi/enuYFtMHgfU/maxresdefault.jpg", "Golden", "Harry Styles", "Fine Line", R.raw.golden);
        Song song3 = new Song("https://img.youtube.com/vi/tcYodQoapMg/maxresdefault.jpg", "Positions", "Ariana Grande", "Positions", R.raw.positions);
        Song song4 = new Song("https://img.youtube.com/vi/nuYkdJaB_tg/maxresdefault.jpg", "Ramblings Of A Lunatic", "Bears in Trees", "Ramblings Of A Lunatic", R.raw.ramblings_of_a_lunatic);
        Song song5 = new Song("https://img.youtube.com/vi/loOWKm8GW6A/maxresdefault.jpg", "Level of Concern", "twenty one pilots", "Level of Concern", R.raw.level_of_concern);

        songList.add(song);
        songList.add(song1);
        songList.add(song2);
        songList.add(song3);
        songList.add(song4);
        songList.add(song5);

        btPlay = findViewById(R.id.bt_play_pause);
        btNext = findViewById(R.id.bt_next);
        btPrevious = findViewById(R.id.bt_previous);
        tvName = findViewById(R.id.tv_name);
        tvAuthor = findViewById(R.id.tv_author);
        tvPlaylist = findViewById(R.id.tv_playlist);
        tvPosition = findViewById(R.id.tv_position);
        coverImage = findViewById(R.id.iv_capa);
        sbVolume = findViewById(R.id.seekBar_volume);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        sbVolume.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        sbVolume.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
        sbVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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

        startSong(song);
        play(btPlay);

    }

    public void startSong(Song song){
        Glide.with(this).load(song.getImgUrl()).placeholder(getResources().getDrawable(R.drawable.carregando)).into(coverImage);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), song.getSong());
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                nextSong(btPlay);
            }
        });
        tvName.setText(song.getName());
        tvPlaylist.setText(song.getPlaylistName());
        tvAuthor.setText(song.getAuthor());
        tvPosition.setText(numSong + 1 + "/" + songList.size());
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

    public void nextSong(View view){
        if (numSong >= songList.size()-1){
            numSong = 0;
        }
        else{
            numSong++;
        }
        mediaPlayer.stop();
        startSong(songList.get(numSong));
        btPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));

    }

    public void previousSong(View view){
        if (numSong <= 0){
            numSong = 0;
        }
        else{
            numSong--;
        }
        mediaPlayer.stop();
        startSong(songList.get(numSong));
        btPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)) {
            audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND);
            sbVolume.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
            return true;
        } else if ((keyCode == KeyEvent.KEYCODE_VOLUME_UP)) {
            audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);
            sbVolume.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
            return true;
        } else
            return super.onKeyDown(keyCode, event);
    }

    /*@Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        sbVolume.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
    }
}