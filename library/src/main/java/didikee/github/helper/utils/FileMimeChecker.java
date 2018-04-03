package didikee.github.helper.utils;

import android.support.annotation.NonNull;

import java.io.File;

/**
 * Created by didikee on 2017/9/5.
 */

public final class FileMimeChecker {

    public static final String VIDEO_MP4 = ".mp4";
    public static final String VIDEO_3GP = ".3gp";
    public static final String IMAGE_JPG = ".jpg";
    public static final String IMAGE_JPEG = ".jpeg";
    public static final String IMAGE_PNG = ".png";

    /**
     * 是否是视频
     * @param file
     * @return
     */
    public static boolean isVideo(@NonNull File file) {
        String fileAbsolutePath = file.getAbsolutePath().toLowerCase();
        return fileAbsolutePath.endsWith(VIDEO_MP4) || fileAbsolutePath.endsWith(VIDEO_3GP);
    }

    public static boolean isImage(@NonNull File file) {
        String fileAbsolutePath = file.getAbsolutePath().toLowerCase();
        return fileAbsolutePath.endsWith(IMAGE_JPEG)
                || fileAbsolutePath.endsWith(IMAGE_JPG)
                || fileAbsolutePath.endsWith(IMAGE_PNG);
    }
}
