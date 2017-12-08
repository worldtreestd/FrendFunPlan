package com.legend.ffplan.common.util;

import android.graphics.Bitmap;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

/**
 * @author Legend
 * @data by on 2017/12/8.
 * @description
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
}
