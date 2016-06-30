package net.archeryc.videodemo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import net.archeryc.videodemo.activity.RecordActivity;

public class MainActivity extends Activity {
    private static final int PERMISSION_SDCARD = 122;
    private static final int PERMISSION_RECORD = 123;
    private static final int PERMISSION_CAMERA = 124;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (checkPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, PERMISSION_SDCARD)) {
            if (checkPermission(this, Manifest.permission.RECORD_AUDIO, PERMISSION_RECORD)) {
                checkPermission(this, Manifest.permission.CAMERA, PERMISSION_CAMERA);
            }
        }
    }

    public void toRecordActivity(View view){
        startActivity(new Intent(this,RecordActivity.class));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_SDCARD) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                Toast.makeText(MainActivity.this, "请开启存储权限后进入app", Toast.LENGTH_SHORT).show();
                finish();
            }
            if (checkPermission(this, Manifest.permission.RECORD_AUDIO, PERMISSION_RECORD)) {
            }
        } else if (requestCode == PERMISSION_RECORD) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                Toast.makeText(MainActivity.this, "请开启录音权限", Toast.LENGTH_SHORT).show();
                finish();
            }
            if (checkPermission(this, Manifest.permission.CAMERA, PERMISSION_CAMERA)) {
            }
        } else {
            if (requestCode == PERMISSION_CAMERA) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(MainActivity.this, "请开启摄像头权限", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }

    public  boolean checkPermission(Activity activity, String permission, int code) {
        if (ContextCompat.checkSelfPermission(activity, permission)
                != PackageManager.PERMISSION_GRANTED) {
            //申请对应权限
            ActivityCompat.requestPermissions(activity, new String[]{permission},
                    code);
            return false;
        }else{
            return true;
        }
    }
}
