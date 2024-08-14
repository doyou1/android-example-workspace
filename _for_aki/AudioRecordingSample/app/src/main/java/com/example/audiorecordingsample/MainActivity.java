package com.example.audiorecordingsample;

import static android.Manifest.permission.RECORD_AUDIO;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.audiorecordingsample.audioplay.AndroidAudioPlayer;
import com.example.audiorecordingsample.audiorecord.AndroidAudioRecorder;

import java.io.File;
import java.sql.Array;

public class MainActivity extends AppCompatActivity {


    private Button btn_start_record;
    private Button btn_stop_record;
    private Button btn_play;
    private Button btn_stop_play;

    private AndroidAudioRecorder recorder;
    private AndroidAudioPlayer player;

    private File audioFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        connectLayout();
        setClickEvent();
        setAudioUtil();
        requestAudioPermission();
    }

    private void connectLayout() {
        btn_start_record = findViewById(R.id.btn_start_record);
        btn_stop_record = findViewById(R.id.btn_stop_record);
        btn_play = findViewById(R.id.btn_play);
        btn_stop_play = findViewById(R.id.btn_stop_play);
    }

    private void setClickEvent() {
        // 녹화시작
        btn_start_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                audioFile = new File(getCacheDir(), "audio.mp3");
                recorder.start(audioFile);
                Toast.makeText(getApplicationContext(), "녹화시작", Toast.LENGTH_SHORT).show();
            }
        });

        // 녹화종료
        btn_stop_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (audioFile != null) {
                    recorder.stop();
                    Toast.makeText(getApplicationContext(), "녹화종료", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 듣기시작
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (audioFile != null) {
                    player.playFile(audioFile);
                    Toast.makeText(getApplicationContext(), "듣기시작", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 듣기종료
        btn_stop_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.stop();
                Toast.makeText(getApplicationContext(), "듣기종료", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAudioUtil() {
        recorder = new AndroidAudioRecorder(getApplicationContext());
        player = new AndroidAudioPlayer(getApplicationContext());
    }

    private void requestAudioPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 0);
    }
}