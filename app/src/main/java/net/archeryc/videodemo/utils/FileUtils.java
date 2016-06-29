package net.archeryc.videodemo.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * 文件工具类
 * Created by wangnjing on 2016/06/21.
 */
public class FileUtils {
    public static final String SD_PATH= Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String TAG=FileUtils.class.getName();

    public static void createSDCardDir(String path){
        String mPath=SD_PATH+path;
        File file = new File(mPath);
        if (hasSDCard()) {
            file.mkdirs();
        }else{
            Log.d(TAG, "not find SDCard");
        }
    }

    private static boolean hasSDCard(){
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            return true;
        }else{
            return false;
        }
    }

}
