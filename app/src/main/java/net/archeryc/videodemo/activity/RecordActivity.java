
package net.archeryc.videodemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import net.archeryc.videodemo.R;
import net.archeryc.videodemo.utils.FileUtils;
import net.archeryc.videodemo.view.Preview;
import net.archeryc.videodemo.view.QfVideoView;
import net.archeryc.videodemo.view.RecordManager;

/**
 * 这里实现竖屏预览是通过加遮罩实现的，在生成视频后进行相应的裁剪
 */
public class RecordActivity extends Activity implements Preview.OnRecordStateChangedListener {
    private String TAG = RecordActivity.class.getName();
    private Preview preview;
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
        preview.setOnRecordStateChangedListener(this);
        ajustBottomHeight();
        ajustVideoView();
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

    public void startRecord(View view) {
        preview.startRecord();
    }

    public void stopRecord(View view) {
        preview.stopRecord();
    }

    public void playVideo(View view) {
        if (!TextUtils.isEmpty(mPath)) {
            videoView.play(mPath);
        }
    }


    @Override
    public void onStartRecord() {

    }

    @Override
    public void onFinishRecord(String path) {
        this.mPath = path;
    }

}
