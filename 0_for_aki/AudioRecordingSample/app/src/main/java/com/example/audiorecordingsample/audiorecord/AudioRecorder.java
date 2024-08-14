package com.example.audiorecordingsample.audiorecord;

import java.io.File;

public interface AudioRecorder {
    void start(File outputFile);
    void stop();
}
