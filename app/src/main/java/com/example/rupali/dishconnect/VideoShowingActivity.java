package com.example.rupali.dishconnect;

import android.content.Intent;
import android.widget.MediaController;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.VideoView;

public class VideoShowingActivity extends AppCompatActivity {
    int stopposition;
    VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_showing);
        Intent j = getIntent();
        videoView = findViewById(R.id.videoview);
        if (j.hasExtra("uri")) {
            Log.d("uri", j.getStringExtra("uri"));
            Uri uri = Uri.parse(j.getStringExtra("uri"));
            Log.d("uri2", uri + "");
            MediaController mediaController = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                mediaController = new MediaController(this);
            }
            mediaController.setAnchorView(videoView);
            videoView.requestFocus();
            videoView.setMediaController(mediaController);
            videoView.seekTo(100);
            videoView.setVideoURI(uri);
        }
    }

    @Override
    protected void onStart() {
        videoView.start();
        super.onStart();
    }

    @Override
    protected void onPause() {
        videoView.pause();
        stopposition = videoView.getCurrentPosition();
        super.onPause();
    }

    @Override
    protected void onResume() {
        videoView.seekTo(stopposition);
        videoView.start();
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        Intent j = new Intent(this, MainActivity.class);
        startActivity(j);
    }
}