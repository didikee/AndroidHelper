package didikee.github.helper.utils;

import java.text.DecimalFormat;

import didikee.github.helper.file.FileUtil;

/**
 * Created by didikee on 21/06/2017.
 * {@link FileUtil#formatSize(long)}
 */
@Deprecated
public final class SizeUtil {

    public static String getSuitableSizeFormat(long size) {
        if (size <= 0) return "0KB";
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }
}
