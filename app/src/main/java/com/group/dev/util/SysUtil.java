package com.group.dev.util;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Locale;

/**
 * 描述:
 * <p>
 * author zys
 * create by 2021/1/20
 */
public class SysUtil {
    private SysUtil() {

    }

    /**
     * 文件大小
     */
    public static long fileSize(File fileDir) {
        long size = 0;
        try {
            File[] files = fileDir.listFiles();
            if (files == null) {
                return size;
            }
            for (File file : files) {
                if (file.isDirectory()) {
                    size = size + fileSize(file);
                } else {
                    size = size + file.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return size;
        }
        return size;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void write(Context context, String fileName, String value) {

        if (value == null) {
            return;
        }
        try {
            File file = new File(context.getExternalCacheDir(), fileName);
            file.deleteOnExit();
            file.createNewFile();
            FileOutputStream os = new FileOutputStream(file);
            OutputStreamWriter writer = new OutputStreamWriter(os);
            writer.write(value);
            os.flush();
            os.close();
            writer.close();
        } catch (Exception e) {
            Log.i("测试TAG", String.format(Locale.CHINA, "write -> %s", e.getMessage()));
        }
    }
}
