package didikee.github.helper.utils;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;

import java.util.Locale;

/**
 * Created by didikee on 2017/7/27.
 */

public class DeviceInfoUtil {
    /**
     * 获取设备的当前系统语言
     * @param context
     * @return eg: zh-CN
     */
    public static String getDeviceLanguage(@NonNull Context context) {
        Locale locale;
        if (Build.VERSION.SDK_INT >= 24) {
            locale = context.getResources().getConfiguration().getLocales().get(0);
        } else {
            //noinspection deprecation
            locale = context.getResources().getConfiguration().locale;
        }
        return locale.toString();
    }
}
