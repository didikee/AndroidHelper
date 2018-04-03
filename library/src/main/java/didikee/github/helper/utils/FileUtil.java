package didikee.github.helper.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.StatFs;
import android.support.annotation.NonNull;
import android.text.format.Formatter;
import android.util.Log;
import android.util.Pair;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.Locale;

/**
 * Created by didikee on 06/07/2017.
 */

public final class FileUtil {

    public static boolean renameFile(File old, File newFile) {
        if (old != null && newFile != null) {
            if (newFile.exists()) {
                deleteDirectory(newFile);
            }
            return old.renameTo(newFile);
        }
        return false;
    }

    public static void deleteDirectory(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isDirectory()) {
                        deleteDirectory(files[i]);
                    } else {
                        boolean delete = files[i].delete();
                    }
                }
            }
            boolean delete = file.delete();
        }
    }

    /**
     * 格式化文件尺寸
     * @deprecated
     * {@link FileUtil#formatFileSize(Context, long)} 用这个代替
     * @param size
     * @return return a suitable size format
     */
    public static String formatSize(long size) {
        if (size <= 0) return "0KB";
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    public static String formatFileSize(Context context, long size) {
        return Formatter.formatFileSize(context, size);
    }

    public static String formatVideoTime(long timeMs) {
        StringBuilder mFormatBuilder;
        java.util.Formatter mFormatter;
        mFormatBuilder = new StringBuilder();
        mFormatter = new java.util.Formatter(mFormatBuilder, Locale.getDefault());
        long totalSeconds = timeMs / 1000;
        long seconds = totalSeconds % 60;
        long minutes = (totalSeconds / 60) % 60;
        long hours = totalSeconds / 3600;

        mFormatBuilder.setLength(0);
        return mFormatter.format("%02d:%02d:%02d", hours, minutes, seconds).toString();
    }

    public static String formatVideoTimeExact(long timeMs) {
        StringBuilder mFormatBuilder;
        java.util.Formatter mFormatter;
        mFormatBuilder = new StringBuilder();
        mFormatter = new java.util.Formatter(mFormatBuilder, Locale.getDefault());
        long totalSeconds = timeMs / 1000;
        long secondFloat = (timeMs / 100) % 10;
        long seconds = totalSeconds % 60;
        long minutes = (totalSeconds / 60) % 60;
        long hours = totalSeconds / 3600;

        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%02d:%02d:%02d.%01d", hours, minutes, seconds, secondFloat).toString();
        } else {
            return mFormatter.format("%02d:%02d.%01d", minutes, seconds, secondFloat).toString();
        }

    }

    public static Pair<Long, Long> getStorageInfo() {
        // 获得sd卡的内存状态
        File sdcardFileDir = Environment.getExternalStorageDirectory();
        // 获得手机内部存储控件的状态
//        File dataFileDir = Environment.getDataDirectory();
//        String dataMemory = getMemoryInfo(dataFileDir);
        return getMemoryInfo(sdcardFileDir);
    }

    private static Pair<Long, Long> getMemoryInfo(@NonNull File path) {
        // 获得一个磁盘状态对象
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();   // 获得一个扇区的大小
        long totalBlocks = stat.getBlockCount();    // 获得扇区的总数
        long availableBlocks = stat.getAvailableBlocks();   // 获得可用的扇区数量
        return new Pair<>(totalBlocks * blockSize, availableBlocks * blockSize);
    }

    /**
     * 保存文件到本地
     * @param bitmap 图片
     * @param saveFile 保存文件的路径（new File(path)）
     * @return 是否保存成功
     */
    public static boolean saveBitmap2File(Bitmap bitmap, File saveFile) {
        if (bitmap == null || saveFile == null) return false;
        if (saveFile.exists()) {
            saveFile.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(saveFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("test", "error: " + e.toString());
            return false;
        }
    }

    /**
     * 获取文件拓展名
     * @param filePath /root/sdcard/download/hello.png
     * @return png
     */
    public static String getExtension(String filePath) {
        try {
            return filePath.substring(filePath.lastIndexOf(".") + 1);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
