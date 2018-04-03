package didikee.github.helper.promot;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by didikee on 2018/3/2.
 *
 */

public class ShareManager {
    private static final String TAG = "ShareManager";

    public ArrayList<String> removePackages() {
        ArrayList<String> removePackages = new ArrayList<>();
        removePackages.add("com.android.bluetooth");
        removePackages.add("com.android.nfc");
        return removePackages;
    }

    public ArrayList<String> popularPackages() {
        ArrayList<String> popularPackages = new ArrayList<>();
        popularPackages.add("com.tencent.mm");
        popularPackages.add("com.tencent.mobileqq");
        popularPackages.add("com.instagram.android");
        popularPackages.add("com.facebook.katana");//facebook
        popularPackages.add("com.facebook.orca");//facebook message
        popularPackages.add("com.facebook.lite");//facebook lite
        popularPackages.add("com.whatsapp");
        popularPackages.add("com.twitter.android");
        return popularPackages;
    }

    public Pair<List<ResolveInfo>, List<ResolveInfo>> getShareActivities(Context context, String type) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> orderActivityList = new ArrayList<>();
        List<ResolveInfo> allActivityList = packageManager.queryIntentActivities(sharingIntent, 0);
        List<ResolveInfo> popularActivityList = new ArrayList<>();
        List<ResolveInfo> normalActivityList = new ArrayList<>();

        ArrayList<String> popularPackages = popularPackages();

        for (ResolveInfo resolveInfo : allActivityList) {
            String targetPackageName = resolveInfo.activityInfo.packageName;
            Log.d(TAG, "包名: " + targetPackageName);
            //过滤出facebook google+  whatapp  twitter 分享app单独处理
            boolean popular = false;
            for (String packageName : popularPackages) {
                if (packageName.equalsIgnoreCase(targetPackageName) || packageName.contains(targetPackageName)) {
                    popular = true;
                    break;
                }
            }

            if (popular) {
                popularActivityList.add(resolveInfo);
            } else {
                normalActivityList.add(resolveInfo);
            }
        }
        orderActivityList.addAll(popularActivityList);
        orderActivityList.addAll(normalActivityList);

        Pair<List<ResolveInfo>, List<ResolveInfo>> data = new Pair<>(
                popularActivityList,
                orderActivityList
        );
        return data;
    }
}
