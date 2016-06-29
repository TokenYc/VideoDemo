
package net.archeryc.videodemo.activity;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import net.archeryc.videodemo.R;
import net.archeryc.videodemo.utils.FileUtils;
import net.archeryc.videodemo.view.Preview;

import java.io.IOException;

public class RecordActivity extends Activity {
    private String TAG = RecordActivity.class.getName();
    private Preview preview;
    private RelativeLayout rlBottom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        FileUtils.createSDCardDir("/VideoDemo");

        preview = (Preview) findViewById(R.id.preView);
        rlBottom = (RelativeLayout) findViewById(R.id.rl_bottom);
        ViewGroup root=(ViewGroup)findViewById(android.R.id.content);
//        ViewGroup.LayoutParams lp=rlBottom.getLayoutParams();
////        lp.width= ViewGroup.LayoutParams.MATCH_PARENT;
////        lp.height=(getResources().getDisplayMetrics().widthPixels);
//        rlBottom.setLayoutParams(lp);
    }

    public void startRecord(View view){
        preview.startRecord();
    }

    public void stopRecord(View view){
        preview.stopRecord();
    }
}
