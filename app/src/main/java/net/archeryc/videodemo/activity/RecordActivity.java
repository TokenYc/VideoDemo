
package net.archeryc.videodemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import net.archeryc.videodemo.R;
import net.archeryc.videodemo.utils.FileUtils;
import net.archeryc.videodemo.view.Preview;
import net.archeryc.videodemo.view.QfVideoView;
import net.archeryc.videodemo.view.RecordManager;

/**
 * 这里实现竖屏预览是通过加遮罩实现的，在生成视频后进行相应的裁剪
 */
public class RecordActivity extends AppCompatActivity implements Preview.OnRecordStateChangedListener ,View.OnClickListener{
    private String TAG = RecordActivity.class.getName();
    private Preview preview;
    private Button btnPlay;
    private Button btnRecord;
    private Button btnStopRecord;
    private RelativeLayout rlBottom;
    private QfVideoView videoView;
    private float rate = 640f / 480f;
    private String mPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        FileUtils.createSDCardDir("/VideoDemo");

        preview = (Preview) findViewById(R.id.preView);
        rlBottom = (RelativeLayout) findViewById(R.id.rl_bottom);
        videoView = (QfVideoView) findViewById(R.id.videoView);
        btnPlay = (Button) findViewById(R.id.btn_play);
        btnRecord = (Button) findViewById(R.id.btn_record);
        btnStopRecord = (Button) findViewById(R.id.btn_stop_record);

        ajustBottomHeight();
        ajustVideoView();

        btnStopRecord.setVisibility(View.GONE);
        btnPlay.setVisibility(View.GONE);
        btnRecord.setVisibility(View.VISIBLE);

        btnRecord.setOnClickListener(this);
        btnStopRecord.setOnClickListener(this);
        btnPlay.setOnClickListener(this);
        preview.setOnRecordStateChangedListener(this);

    }

    private void ajustVideoView() {
        ViewGroup.LayoutParams lp = videoView.getLayoutParams();
        lp.width = getResources().getDisplayMetrics().widthPixels;
        lp.height = (int) (getResources().getDisplayMetrics().widthPixels / rate);
    }

    private void ajustBottomHeight() {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) rlBottom.getLayoutParams();
        lp.setMargins(0, (int) (getResources().getDisplayMetrics().widthPixels / rate), 0, 0);
        rlBottom.setLayoutParams(lp);
    }

    public void startRecord() {
        preview.startRecord();
    }

    public void stopRecord() {
        preview.stopRecord();
    }

    public void playVideo() {
        if (!TextUtils.isEmpty(mPath)) {
            videoView.play(mPath);
        }
    }


    @Override
    public void onStartRecord() {
        btnPlay.setVisibility(View.GONE);
        btnRecord.setVisibility(View.GONE);
        btnStopRecord.setVisibility(View.VISIBLE);
        videoView.stop();
    }

    @Override
    public void onFinishRecord(String path) {
        this.mPath = path;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btnPlay.setVisibility(View.VISIBLE);
                btnRecord.setVisibility(View.VISIBLE);
                btnStopRecord.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_record:
                startRecord();
                break;
            case R.id.btn_stop_record:
                stopRecord();
                break;
            case R.id.btn_play:
                playVideo();
                break;
        }
    }
}
