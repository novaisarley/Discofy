package com.br.arley.spotifyclone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.br.arley.spotifyclone.Services.NotificationActionService;
import com.br.arley.spotifyclone.Services.OnClearFromRecentService;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements Playable {

    ImageView coverImage, discoImage;
    MediaPlayer mediaPlayer;
    TextView tvPlaylist, tvName, tvAuthor, tvPosition;
    ImageButton btPlay, btNext, btPrevious;
    SeekBar sbVolume;

    NotificationManager notificationManager;

    List<Song> songList;
    int numSong = 0;

    AudioManager audioManager;
    ObjectAnimator anim;
    ObjectAnimator anim1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = new MediaPlayer();

        discoImage = findViewById(R.id.iv_disco);

        songList = new ArrayList<>();


        populateSongs();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
            registerReceiver(broadcastReceiver, new IntentFilter("TRACKS_TRACKS"));
            startService(new Intent(getBaseContext(), OnClearFromRecentService.class));
        }


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

        setAnimation();
        startSong(songList.get(0));
        Glide.with(this).load(songList.get(numSong).getImgUrl()).placeholder(getResources().getDrawable(R.drawable.carregando)).into(coverImage);
        play(btPlay);

    }

    private void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CreateNotification.CHANNEL_ID,
                    "KOD Dev", NotificationManager.IMPORTANCE_LOW);

            notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    private void populateSongs() {
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

        for (final Song s : songList) {
            Glide.with(getApplicationContext())
                    .asBitmap()
                    .load(s.getImgUrl())
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            s.setCover(resource);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                        }
                    });
        }
    }

    public void setAnimation() {
        anim = new ObjectAnimator();
        anim1 = new ObjectAnimator();

        anim = ObjectAnimator.ofFloat(discoImage, "rotation", 0, 360);
        anim1 = ObjectAnimator.ofFloat(coverImage, "rotation", 0, 360);
        anim.setDuration(5000);
        anim1.setDuration(5000);
        anim.setRepeatMode(ObjectAnimator.RESTART);
        anim1.setRepeatMode(ObjectAnimator.RESTART);
        anim.setInterpolator(new LinearInterpolator());
        anim1.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(ObjectAnimator.INFINITE);
        anim1.setRepeatCount(ObjectAnimator.INFINITE);
        anim.start();
        anim1.start();
    }

    public void resumeAnimation() {
        anim.resume();
        anim1.resume();
    }

    public void pauseAnimation() {
        anim.pause();
        anim1.pause();
    }


    public void startSong(Song song) {
        Glide.with(this).load(song.getCover()).placeholder(getResources().getDrawable(R.drawable.carregando)).into(coverImage);

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
        play(btPlay);
    }

    public void play(View view) {
        if (mediaPlayer == null) {
            return;
        }
        if (mediaPlayer.isPlaying()) {
            onSongPause();

        } else {
            onSongPlay();
        }
    }

    public void nextSong(View view) {
        onSongNext();

    }

    public void previousSong(View view) {
        onSongPrevious();
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
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sbVolume.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
    }


    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getExtras().getString("actionname");

            switch (action) {
                case CreateNotification.ACTION_PREVIUOS:
                    onSongPrevious();
                    break;

                case CreateNotification.ACTION_PLAY:
                    if (mediaPlayer.isPlaying()) {
                        onSongPause();
                    } else {
                        onSongPlay();
                    }
                    break;

                case CreateNotification.ACTION_NEXT:
                    onSongNext();

            }
        }
    };

    @Override
    public void onSongPrevious() {
        if (numSong <= 0) {
            numSong = 0;
        } else {
            numSong--;
        }
        mediaPlayer.stop();
        mediaPlayer.reset();
        mediaPlayer.release();
        startSong(songList.get(numSong));
        CreateNotification.createNotification(MainActivity.this, songList.get(numSong),
                R.drawable.ic_pause_18dp, numSong, songList.size() - 1, songList.get(numSong).getCover());
        btPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
    }

    @Override
    public void onSongPlay() {
        Glide.with(this).load(songList.get(numSong).getCover()).placeholder(getResources().getDrawable(R.drawable.carregando)).into(coverImage);
        mediaPlayer.start();
        resumeAnimation();
        CreateNotification.createNotification(MainActivity.this, songList.get(numSong),
                R.drawable.ic_pause_18dp, numSong, songList.size() - 1, songList.get(numSong).getCover());
        btPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
    }

    @Override
    public void onSongPause() {
        mediaPlayer.pause();
        pauseAnimation();
        CreateNotification.createNotification(MainActivity.this, songList.get(numSong),
                R.drawable.ic_play_18dp, numSong, songList.size() - 1, songList.get(numSong).getCover());
        btPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_play));
    }

    @Override
    public void onSongNext() {
        if (numSong >= songList.size() - 1) {
            numSong = 0;
        } else {
            numSong++;
        }
        mediaPlayer.stop();
        mediaPlayer.reset();
        mediaPlayer.release();
        startSong(songList.get(numSong));
        CreateNotification.createNotification(MainActivity.this, songList.get(numSong),
                R.drawable.ic_pause_18dp, numSong, songList.size() - 1, songList.get(numSong).getCover());
        btPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));
        resumeAnimation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.cancelAll();
        }
        unregisterReceiver(broadcastReceiver);
    }
}