package didikee.github.helper.utils;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by didikee on 09/07/2017.
 */

public class BitmapUtil {

    public static boolean saveBitmap2SDCard(Bitmap bitmap, String savePath) {
        if (bitmap == null) {
            return false;
        }
        File saveFile = new File(savePath);
        if (saveFile.exists()) {
            saveFile.delete();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(saveFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);

            fos.flush();
            fos.close();
//            MediaStore.Images.Media.insertImage(getContentResolver(), b, "Screen", "screen");
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }



}
