package didikee.github.helper.utils;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;

/**
 * Created by didikee on 16/05/2017.
 */

public class ClipboardUtil {

    public static void putString(Context context, String text) {
        if (context == null) return;

        if (TextUtils.isEmpty(text)) {
            text = "";
            //会直接把最近的剪贴板置空
        }
        ClipboardManager clipboardManager = getClipboardManager(context);
        clipboardManager.setPrimaryClip(ClipData.newPlainText("Text", text));
    }

    public static ClipboardManager getClipboardManager(Context context) {
        return context == null ? null : (ClipboardManager) context.getSystemService(Context
                .CLIPBOARD_SERVICE);
    }

    /**
     * 获取剪贴板中第一条String
     *
     * @return
     */
    public static String getTopClipText(Context context) {
        if (context == null) return null;

        ClipboardManager clipboardManager = getClipboardManager(context);
        if (!clipboardManager.hasPrimaryClip()) {
            return null;
        }
        ClipData data = clipboardManager.getPrimaryClip();
        if (data != null && clipboardManager.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
            return data.getItemAt(0).getText().toString();
        }
        return null;
    }

}
