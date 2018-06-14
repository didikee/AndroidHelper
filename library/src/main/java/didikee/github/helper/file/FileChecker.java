package didikee.github.helper.file;

import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by didikee on 23/06/2017.
 */

public class FileChecker {
    public static final String FILE_HIDE_PREFIX = ".";

    public static void checkExtension(String extension) {
        checkExtension(new String[]{extension});
    }

    public static boolean checkExtension(String[] extensions) {
        if (extensions == null || extensions.length < 1) {
            Log.d("FileChecker", "checkExtension : extensions is unsupport.");
            return false;
        }
        return true;
    }

    /**
     * 去掉隐藏文件
     * @param files
     * @return
     */
    public static boolean removeHideFile(List<File> files) {
        if (files == null || files.size() < 1) {
            Log.d("FileChecker", "removeHideFile : file is null or size < 1.");
            return false;
        }
        List<File> removeFiles = new ArrayList<>();
        for (File file : files) {
            if (file.isHidden()) {
                removeFiles.add(file);
                continue;
            }
            // 文件名以 "." 开头的默认是隐藏的
            if (file.getName().startsWith(FILE_HIDE_PREFIX)) {
                removeFiles.add(file);
            }
        }
        if (removeFiles.size() > 0) {
            files.removeAll(removeFiles);
        }
        return true;
    }

    public static boolean getTargetSuffixFiles(List<File> files, String[] suffix, boolean containHideFile) {
        if (isErrorList(files)) {
            Log.d("FileChecker", "removeHideFile : file is null or size < 1.");
            return false;
        }
        if (suffix == null || suffix.length < 1) {
            return false;
        }
        if (!containHideFile) {
            removeHideFile(files);
        }
        List<File> leftFiles = new ArrayList<>();
        for (File file : files) {
            // 如果是文件夹,直接加入
            if (file.isDirectory()) {
                leftFiles.add(file);
                continue;
            }
            String fileName = file.getAbsolutePath().toLowerCase();
            for (String suf : suffix) {
                if (leftFiles.contains(file)) {
                    break;
                }
                if (fileName.endsWith(suf)) {
                    leftFiles.add(file);
                }
            }
        }
        if (leftFiles.size() >= 0) {
            files.clear();
            files.addAll(leftFiles);
        }
        return true;
    }

    public static int getTargetSuffixFileNum(File file, String[] suffix, boolean containHideFile) {
        if (file == null) {
            Log.d("FileChecker", "removeHideFile : file is null or size < 1.");
            return -1;
        }
        if (suffix == null || suffix.length < 1) {
            return -1;
        }
        List<File> leftFiles = new ArrayList<>();
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            List<File> realFiles = new ArrayList<>(Arrays.asList(files));
            if (!containHideFile) {
                removeHideFile(realFiles);
            }
            for (File tempFile : realFiles) {
                // 如果是文件夹,直接加入
                if (tempFile.isDirectory()) {
                    leftFiles.add(tempFile);
                    continue;
                }
                String fileName = tempFile.getAbsolutePath().toLowerCase();
                for (String suf : suffix) {
                    if (leftFiles.contains(tempFile)) {
                        break;
                    }
                    if (fileName.endsWith(suf)) {
                        leftFiles.add(tempFile);
                    }
                }
            }
        } else {
            return 1;
        }

        return leftFiles.size();
    }

    public static boolean isTargetSuffix(File file, String[] suffix) {
        if (file == null || file.isDirectory()) {
            return false;
        }
        boolean isSuffix = false;
        String fileName = file.getAbsolutePath().toLowerCase();
        for (String suf : suffix) {
            if (isSuffix) {
                break;
            }
            if (fileName.endsWith(suf)) {
                isSuffix = true;
            }
        }
        return isSuffix;
    }

    private static boolean isErrorList(List list) {
        return list == null || list.size() < 1;
    }
}
