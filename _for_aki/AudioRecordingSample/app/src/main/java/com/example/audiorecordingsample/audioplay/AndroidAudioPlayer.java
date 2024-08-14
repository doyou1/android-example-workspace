package com.example.audiorecordingsample.audioplay;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;

import java.io.File;

public class AndroidAudioPlayer implements AudioPlayer {

    private Context context;
    private MediaPlayer player;

    public AndroidAudioPlayer(Context context) {
        this.context = context;
    }

    @Override
    public void playFile(File file) {
        MediaPlayer play = MediaPlayer.create(context, Uri.fromFile(file));
        player = play;
        play.start();
    }

    @Override
    public void stop() {
        player.stop();
        player.release();
        player = null;
    }
}
