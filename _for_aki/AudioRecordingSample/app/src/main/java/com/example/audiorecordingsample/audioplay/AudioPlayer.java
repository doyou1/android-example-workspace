package com.example.audiorecordingsample.audioplay;

import java.io.File;

public interface AudioPlayer {
    void playFile(File file);
    void stop();
}
