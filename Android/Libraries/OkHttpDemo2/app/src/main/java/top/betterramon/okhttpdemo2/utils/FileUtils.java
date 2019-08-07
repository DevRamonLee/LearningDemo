package top.betterramon.okhttpdemo2.utils;

import android.os.Environment;
import android.text.TextUtils;

import java.io.File;

import top.betterramon.okhttpdemo2.Program;
import top.betterramon.okhttpdemo2.conf.Consts;

/**
 * Created by Ramon Lee on 2019/8/7.
 */
public class FileUtils {
    public static File getImageCacheDir() {
        File imageCacheDir = new File(getCacheDir(), Consts.IAMGE_CACHE);
        if (!imageCacheDir.exists()) {
            imageCacheDir.mkdirs();
        }
        return imageCacheDir;
    }

    //获取缓存文件夹
    public static File getCacheDir() {
        File result = null;
        if (existsSdcard()) {
            File cacheDir = Program.application.getExternalCacheDir();
            if (cacheDir == null) {
                result = new File(Program.application.getCacheDir(),
                        "Android" + File.separator + "data" + File.separator + Program.application.getPackageName() + File.separator + "cache");
            } else {
                result = cacheDir;
            }
        }
        if (result.exists() || result.mkdirs()) {
            return result;
        } else {
            return null;
        }
    }

    public static Boolean existsSdcard() {
        return TextUtils.equals(Environment.getExternalStorageState(), Environment.MEDIA_MOUNTED);
    }

    public static void deleteImageCacheDir() {
        deleteDir(getImageCacheDir());
    }

    /**
     * 删除文件夹里面的所以文件
     */
    public static void deleteDir(File dir) {
        if (dir.exists()) {
            File[] files = dir.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    files[i].delete();
                } else {
                    deleteDir(files[i]);
                }
            }
        }
    }
}
