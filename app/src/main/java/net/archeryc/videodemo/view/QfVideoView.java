package net.archeryc.videodemo.view;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.io.IOException;

/**
 * Created by 24706 on 2016/6/30.
 * 用来播放的View
 */
public class QfVideoView extends SurfaceView implements SurfaceHolder.Callback {
    private MediaPlayer mediaPlayer;

    public QfVideoView(Context context) {
        super(context, null);
        init();
    }

    public QfVideoView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        init();
    }

    public QfVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        isInEditMode();
        getHolder().addCallback(this);
    }

    private void initPlayer() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDisplay(getHolder());
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisibility(GONE);
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
            }
        });
        mediaPlayer.setLooping(true);
    }

    public void play(String path) {
        try {
            setVisibility(VISIBLE);
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.setDataSource(path);
                mediaPlayer.prepare();
                mediaPlayer.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            setVisibility(GONE);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        initPlayer();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }


}
