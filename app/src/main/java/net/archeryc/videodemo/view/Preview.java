package net.archeryc.videodemo.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import org.ffmpeg.android.*;

import net.archeryc.videodemo.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by 24706 on 2016/6/28.
 */
public class Preview extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = Preview.class.getName();
    private SurfaceHolder mSurfaceHolder;
    private Camera mCamera;
    private MediaRecorder mRecorder;
    private File file1;
    private File file2;
    private CamcorderProfile profile;
    private int transY;


    public Preview(Context context) {
        super(context);
        init();
    }

    public Preview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Preview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mRecorder = new MediaRecorder();
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.e(TAG, "surfaceCreated");
        mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.e(TAG, "surfaceChanged");

        initCamera();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.e(TAG, "surfaceDestroyed");
        if (null != mCamera) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    private void initCamera() {
        if (null != mCamera) {
            Camera.Parameters parameters = mCamera.getParameters();
            parameters.setPictureFormat(PixelFormat.JPEG);
            parameters.setPreviewFormat(PixelFormat.YCbCr_420_SP);
            List<Camera.Size> sizes = parameters.getSupportedPreviewSizes();
            float rate = 640f/480f;
//            for (Camera.Size size : sizes) {
//                Log.e("size", "size:" + "-width-" + size.width + "-height-" + size.height);
//                if (size.width>=400&&size.width<=800){
//                    rate=(float)size.width/(float)size.height;
            parameters.setPreviewSize(640, 480);
//                    break;
//                }
//            }
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            int width = getResources().getDisplayMetrics().widthPixels;
            Log.e(TAG, "rate:" + rate);
            int height = (int) (width);
            Log.e(TAG, "width:" + width + "height:" + height);
            layoutParams.width = width;
            layoutParams.height = (int)(width*rate);
            setLayoutParams(layoutParams);

            transY=(int)(((float)width*rate-(float)width/rate)/2f);
            setTranslationY(-transY);//视频高度-展示高度的一半，使裁剪的的中间位置，下面裁剪的位置也要注意
            Log.e("trans", "transy:" + transY);
//            if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            parameters.set("orientation", "portrait"); //
            parameters.set("rotation", 90); // 镜头角度转90度（默认摄像头是横拍）
            mCamera.setDisplayOrientation(90); // 在2.2以上可以使用
//            } else// 如果是横屏
//            {
//                parameters.set("orientation", "landscape"); //
//                mCamera.setDisplayOrientation(90); // 在2.2以上可以使用
//            }
            mCamera.setParameters(parameters);
            mCamera.startPreview();
            try {
                mCamera.setPreviewDisplay(mSurfaceHolder);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void initRecorder() {
        mCamera.unlock();
        FileUtils.createSDCardDir("/VideoDemo");

        if (mRecorder == null) {
            mRecorder = new MediaRecorder();
        } else {
            mRecorder.reset();
        }
        mRecorder.setCamera(mCamera);

        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

//        mRecorder.setOrientationHint(90);
//        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
//        mRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        file1 = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/VideoDemo" + "/" + System.currentTimeMillis() + ".mp4");

        mRecorder.setOutputFile(file1.getAbsolutePath());

//        mRecorder.setAudioChannels(2);

        if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_480P))

        {
            profile = CamcorderProfile.get(CamcorderProfile.QUALITY_480P);
        } else if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_720P))

        {
            profile = CamcorderProfile.get(CamcorderProfile.QUALITY_720P);
        } else if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_1080P))

        {
            profile = CamcorderProfile.get(CamcorderProfile.QUALITY_1080P);
        } else if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_HIGH))

        {
            profile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
        } else if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_LOW))

        {
            profile = CamcorderProfile.get(CamcorderProfile.QUALITY_LOW);
        }

        if (profile != null)

        {
            profile.audioCodec = MediaRecorder.AudioEncoder.AAC;
            profile.audioSampleRate = 16000;

            profile.videoCodec = MediaRecorder.VideoEncoder.H264;
            mRecorder.setProfile(profile);
        }


        mRecorder.setOrientationHint(90);
        mRecorder.setMaxDuration(30000);

        mRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());
    }

    private void compressThread(final String FileName) {

        new Thread() {

            @Override
            public void run() {
                compressSize();
            }

            /**
             */
            private void compressSize() {

                file2 = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                        + "/VideoDemo", "2222"+FileName);
//
                try {
                    File fileAppRoot = new File(
                            getContext().getApplicationInfo().dataDir);
                    FfmpegController fc = new FfmpegController(
                            getContext(), fileAppRoot);
//
                    fc.compress_clipVideo(file1.getCanonicalPath(),
                            file2.getCanonicalPath(),80, 0,
                            new ShellUtils.ShellCallback() {
                                private int flag = 0;

                                @Override
                                public void shellOut(String shellLine) {
                                }

                                @Override
                                public void processComplete(int exitValue) {
                                    Log.e("hahaha", "压缩裁剪:"+exitValue);
                                }
                            });

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }.start();
    }

    public void startRecord() {
        try {
            initRecorder();
            mRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mRecorder.start();
    }

    public void stopRecord() {
        mRecorder.stop();
        mRecorder.release();
        compressThread(file1.getName());
    }
}
