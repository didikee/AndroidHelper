package didikee.github.helper.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

/**
 * Created by didikee on 02/07/2017.
 */

public class PackageUtil {

    /**
     * get App versionCode
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        int versionCode = 0;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * get App versionName
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        String versionName = "";
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    public static String getApplicationPlaceHolderInfo(Context context, String placeHolder) {
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (applicationInfo == null || applicationInfo.metaData == null) return "";
        return applicationInfo.metaData.getString(placeHolder);
    }

    public static String getActivityPlaceHolderInfo(Activity context, String placeHolder) {
        ActivityInfo activityInfo = null;
        try {
            activityInfo = context.getPackageManager().getActivityInfo(context.getComponentName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (activityInfo == null || activityInfo.metaData == null) return "";
        return activityInfo.metaData.getString(placeHolder);
    }

    public static Intent checkAndGetLaunchIntent(Context context, String packageName) {
        if (context == null || TextUtils.isEmpty(packageName)) {
            return null;
        }
        try {
            return context.getPackageManager().getLaunchIntentForPackage(packageName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isAppInstalled(Context context, String packageName) {
        return checkAndGetLaunchIntent(context, packageName) != null;
    }
}
