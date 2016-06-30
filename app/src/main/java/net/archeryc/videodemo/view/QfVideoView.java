package net.archeryc.videodemo.view;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.io.File;
import java.io.IOException;

/**
 * Created by 24706 on 2016/6/30.
 * 用来播放的View
 */
public class QfVideoView extends SurfaceView implements SurfaceHolder.Callback{
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
        getHolder().addCallback(this);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setVisibility(GONE);
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
            }
        });
    }

    public void play(String path){
        try {
            setVisibility(VISIBLE);
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mediaPlayer=new MediaPlayer();
        mediaPlayer.setDisplay(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }


}
