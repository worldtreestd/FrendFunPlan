package com.legend.ffpmvp.common.utils;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

/**
 * @author Legend
 * @data by on 2017/12/8.
 * @description 图片处理工具类
 */

public class ImageUtils {
    public static boolean saveImage(Bitmap photo,String spath) {
        try {
            BufferedOutputStream bufferedoutputStream = new BufferedOutputStream(
                        new FileOutputStream(spath,false));
            photo.compress(Bitmap.CompressFormat.JPEG,100,bufferedoutputStream);
            bufferedoutputStream.flush();
            bufferedoutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //像素单位转换
    public static int dip2px(Context context, int dip) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5);
    }
}
