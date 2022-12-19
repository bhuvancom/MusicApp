package com.example.musicapp;

import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.io.IOException;

/**
 * Author  : Bhuvaneshvar
 * Project : MusicApp
 * Date    : 10:21 PM
 **/

public class MusicPlayerFragment extends Fragment implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnInfoListener, MediaPlayer.OnBufferingUpdateListener, AudioManager.OnAudioFocusChangeListener, MediaPlayer.OnCompletionListener {
    private MusicDb musicDb;
    private static final String TAG = "MusicPlayerFragment";
    private ImageView albumArt;
    private ImageView btn;
    private TextView title;

    AudioManager audioManager;
    MediaPlayer mediaPlayer;

    private void resetMedia() throws IOException {
        mediaPlayer.stop();
        mediaPlayer.reset();
        mediaPlayer.setDataSource(song.url);
    }

    private void init() throws IOException {
        if (mediaPlayer != null) {
            resetMedia();
            mediaPlayer.prepareAsync();
            Toast.makeText(getContext(), "Preparing", Toast.LENGTH_SHORT).show();
            return;
        }

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setOnInfoListener(this);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnSeekCompleteListener(this);

        mediaPlayer.reset();
        AudioAttributes audioAttributes = new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).setUsage(AudioAttributes.USAGE_MEDIA).build();
        mediaPlayer.setAudioAttributes(audioAttributes);
        mediaPlayer.setDataSource(song.url);
        mediaPlayer.prepareAsync();
    }

    public MusicPlayerFragment() {
        super(R.layout.music_player_fragment);
    }

    Song song;

    private void toggle() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        } else {
            if (mediaPlayer != null) mediaPlayer.start();
        }
        togglePlayBackIcon();
    }

    private void togglePlayBackIcon() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            btn.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
        } else {
            btn.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        albumArt = view.findViewById(R.id.iv_album_art);
        btn = view.findViewById(R.id.btn_pause);
        title = view.findViewById(R.id.title);

        togglePlayBackIcon();
        Toast.makeText(getContext(), "Preparing", Toast.LENGTH_SHORT).show();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        musicDb = new MusicDb(getContext());
        if (getArguments() != null) {
            song = (Song) getArguments().getSerializable("song");
            try {
                init();
                Drawable d = getResources().getDrawable(getResources()
                        .getIdentifier(song.icon, "drawable", getContext().getPackageName()));
                title.setText(song.name);
                albumArt.setImageDrawable(d);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_main);
            Toast.makeText(getContext(), "No Song to play", Toast.LENGTH_SHORT).show();
            navController.navigateUp();
        }
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
        togglePlayBackIcon();
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {

    }

    @Override
    public boolean onInfo(MediaPlayer mediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public void onAudioFocusChange(int i) {

    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public void onSeekComplete(MediaPlayer mediaPlayer) {

    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            try {
                resetMedia();
                mediaPlayer = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
