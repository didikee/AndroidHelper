package didikee.github.helper.helper;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import didikee.github.helper.utils.IntentUtil;


/**
 * Created by didikee on 07/07/2017.
 */

public class AppSupprotHelper {
    private static final String PACKAGE_COOLAPK = "com.coolapk.market";
    private static final String PACKAGE_GOOGLE_PLAY = "com.coolapk.market";
    public static final int COOLAPK = 0;
    public static final int TENCENT = 1;
    public static final int GOOGLE_PLAY = 2;


    public static void shareDownloadUrl(Activity activity, int market) {
        String marketPrefixUrl = getMarketPrefixUrl(market);
        IntentUtil.shareText(activity, "推荐给好友", marketPrefixUrl + activity.getPackageName());
    }

    private static String getMarketPrefixUrl(int market) {
        String prefixUrl = "http://www.coolapk.com/apk/";
        switch (market) {
            case COOLAPK:
                prefixUrl = "http://www.coolapk.com/apk/";
                break;
            default:
                break;
        }
        return prefixUrl;
    }

    public static void shareAppViaCoolapk(Activity activity, String appName) {
        IntentUtil.shareText(activity, "推荐给好友", "酷市场: " + appName + "\n" + "http://www.coolapk.com/apk/" + activity.getPackageName());
    }

    // http://sj.qq.com/myapp/detail.htm?apkName=com.didikee.gifparser
    public static void shareAppViaTencent(Activity activity, String appName) {
        IntentUtil.shareText(activity, "推荐给好友", "我正在使用 Gif助手,你也来试试吧! " + appName + "\n" + "http://sj.qq.com/myapp/detail.htm?apkName=" + activity
                .getPackageName());
    }


    public static void shareAppViaGooglePlay(Activity activity, String title, String msg) {
        IntentUtil.shareText(activity, title, msg + "\n" + "https://play.google.com/store/apps/details?id=" + activity.getPackageName());
    }

    public static void rateAppViaGooglePlay(Activity activity, String packageName) {
        try {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
        }
    }

    public static void shareText(Activity activity, String appName) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        // 查询所有可以分享的Activity
        List<ResolveInfo> resInfo = activity.getPackageManager().queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        if (!resInfo.isEmpty()) {
            List<Intent> targetedShareIntents = new ArrayList<Intent>();
            for (ResolveInfo info : resInfo) {
                Intent targeted = new Intent(Intent.ACTION_SEND);
                targeted.setType("text/plain");
                ActivityInfo activityInfo = info.activityInfo;
                Log.v("logcat", "packageName: " + activityInfo.packageName + "    ActivityPackageName: " + activityInfo.name);
                // 分享出去的内容
                targeted.putExtra(Intent.EXTRA_TEXT, "这是我的分享内容" + activity.getPackageName());
                // 分享出去的标题
                targeted.putExtra(Intent.EXTRA_SUBJECT, "主题");
                targeted.setPackage(activityInfo.packageName);
                targeted.setClassName(activityInfo.packageName, info.activityInfo.name);
                PackageManager pm = activity.getApplication().getPackageManager();
                // 微信有2个怎么区分-。- 朋友圈还有微信
//                if (info.activityInfo.applicationInfo.loadLabel(pm).toString().equals("微信")) {
//                    targetedShareIntents.add(targeted);
//                }
                targetedShareIntents.add(targeted);
            }
            // 选择分享时的标题
            Intent chooserIntent = Intent.createChooser(targetedShareIntents.remove(0), "选择分享");
            if (chooserIntent == null) {
                return;
            }
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[]{}));
            try {
                activity.startActivity(chooserIntent);
            } catch (android.content.ActivityNotFoundException ex) {

                Toast.makeText(activity, "找不到该分享应用组件", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private static String getPackageName(int market) {
        String packageName = "com.coolapk.market";
        switch (market) {
            case COOLAPK:
                packageName = "com.coolapk.market";
                break;
            default:
                break;
        }
        return packageName;
    }

    public static void rateApp(Activity activity) {
        rateApp(activity, null);
    }

    public static void rateApp(Activity activity, int market) {
        try {
            String packageName = getPackageName(market);
            Uri uri = Uri.parse("market://details?id=" + packageName);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(activity, "您没有安装应用市场", Toast.LENGTH_SHORT).show();
        }
    }

    public static void rateApp(Activity activity, int... markets) {
        try {
            Uri uri = Uri.parse("market://details?id=" + activity.getPackageName());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(activity, "您没有安装应用市场", Toast.LENGTH_SHORT).show();
        }
    }

    private static void getFilterApps(Activity activity, Intent typeIntent, int... markets) {
        List<ResolveInfo> resInfo = activity.getPackageManager().queryIntentActivities(typeIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if (resInfo.isEmpty()) {
            Toast.makeText(activity, "没有匹配到任何程序", Toast.LENGTH_SHORT).show();
            return;
        }
        List<Intent> targetedShareIntents = new ArrayList<Intent>();
        for (ResolveInfo info : resInfo) {
            Intent targeted = (Intent) typeIntent.clone();
            ActivityInfo activityInfo = info.activityInfo;
            Log.v("logcat", "packageName: " + activityInfo.packageName + "    ActivityPackageName: " + activityInfo.name);
            // 分享出去的标题
//            targeted.putExtra(Intent.EXTRA_SUBJECT, "主题");
            targeted.setPackage(activityInfo.packageName);
            targeted.setClassName(activityInfo.packageName, info.activityInfo.name);
            PackageManager pm = activity.getApplication().getPackageManager();
            // 微信有2个怎么区分-。- 朋友圈还有微信
//                if (info.activityInfo.applicationInfo.loadLabel(pm).toString().equals("微信")) {
//                    targetedShareIntents.add(targeted);
//                }
            targetedShareIntents.add(targeted);
        }
        // 选择分享时的标题
//        Intent chooserIntent = Intent.createChooser(targetedShareIntents.remove(0), "选择分享");
//        if (chooserIntent == null) {
//            return;
////        }
//        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[]{}));
//        try {
//            activity.startActivity(chooserIntent);
//        } catch (android.content.ActivityNotFoundException ex) {
//
//            Toast.makeText(activity, "找不到该分享应用组件", Toast.LENGTH_SHORT).show();
//        }
    }

    /**
     * 获取应用在谷歌play的地址url
     * @param packageName
     * @return
     */
    public static String getGoogleAPPWebUrl(String packageName) {
        return "https://play.google.com/store/apps/details?id=" + packageName;
    }
}
