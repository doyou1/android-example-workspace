package com.example.audiorecordingsample.audiorecord;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Build;

import java.io.File;
import java.io.FileOutputStream;

public class AndroidAudioRecorder implements AudioRecorder {
    private Context context;
    private MediaRecorder recorder;

    private MediaRecorder createRecorder() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            return new MediaRecorder(context);
        } else {
            return new MediaRecorder();
        }
    }

    public AndroidAudioRecorder(Context context) {
        this.context = context;
    }

    @Override
    public void start(File outputFile) {
        MediaRecorder record = createRecorder();
        record.setAudioSource(MediaRecorder.AudioSource.MIC);
        record.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        record.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        try {
            record.setOutputFile(new FileOutputStream(outputFile).getFD());
            record.prepare();
            record.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        recorder = record;
    }

    @Override
    public void stop() {
        recorder.stop();
        recorder.reset();
        recorder = null;
    }
}
