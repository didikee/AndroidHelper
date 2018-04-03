package didikee.github.helper.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

import didikee.me.android.fix.UriFixUtil;

/**
 * Created by didikee on 27/06/2017.
 */

public class IntentUtil {
    private static final String TAG = IntentUtil.class.getSimpleName();

    public static Intent getAppRateIntent(String packageName) {
        Uri uri = Uri.parse("market://details?id=" + packageName);
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    public static void shareText(Activity activity, String title, String text) {
        if (activity == null) {
            return;
        }
        title = TextUtils.isEmpty(title) ? "分享" : title;
        text = TextUtils.isEmpty(text) ? " " : text;
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.setType("text/plain");
        activity.startActivity(Intent.createChooser(intent, title));
    }

    public static void shareImage(Activity activity, String title, String text, Bitmap image) {
        if (activity == null) {
            return;
        }
        title = TextUtils.isEmpty(title) ? "分享" : title;
        text = TextUtils.isEmpty(text) ? " " : text;
        Intent intent = new Intent();
        intent.putExtra(Intent.EXTRA_TEXT, text);
        String url = MediaStore.Images.Media.insertImage(activity.getContentResolver(), image, "title", "description");
        intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(url));
        intent.setType("image/*");
        activity.startActivity(Intent.createChooser(intent, title));
    }

    public static boolean shareGif(Activity activity, String title, String gifPath) {
        if (activity == null || TextUtils.isEmpty(gifPath)) {
            return false;
        }
        try {
            title = TextUtils.isEmpty(title) ? "分享" : title;
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("image/gif");
            Uri uri = UriFixUtil.getUriFrom(activity, new File(gifPath));
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            activity.startActivity(Intent.createChooser(shareIntent, title));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void shareImage(Activity activity, String title, String text, File imageFile) {
        if (imageFile == null) {
            return;
        }
        Bitmap bm = BitmapFactory.decodeFile(imageFile.getPath());
        shareImage(activity, title, text, bm);
    }

    public static void shareImage(Activity activity, String title, String text, String imagePath) {
        if (TextUtils.isEmpty(imagePath)) {
            return;
        }
        Bitmap bm = BitmapFactory.decodeFile(imagePath);
        shareImage(activity, title, text, bm);
    }


    public static Intent getWebIntent(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        Intent urlIntent = new Intent(Intent.ACTION_VIEW);
        urlIntent.setData(Uri.parse(url));
        return urlIntent;
    }

    /**
     * 写邮件
     * @param activity
     * @param targetAddress
     * @param title
     * @param content
     */
    public static void gotoEmail(Activity activity, String targetAddress, String title, String content) {
        Intent data = new Intent(Intent.ACTION_SENDTO);
        data.setData(Uri.parse("mailto:" + targetAddress));
        data.putExtra(Intent.EXTRA_SUBJECT, title);
        data.putExtra(Intent.EXTRA_TEXT, content);
        try {
            activity.startActivity(data);
        } catch (Throwable throwable) {
            Log.d(TAG, throwable.getMessage());
            Toast.makeText(activity, "Email not available", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 直接分享到对应的app，但是微信却出现问题
     * 等待复查
     * @param activity
     * @param gifPath
     * @param resolveInfo
     */
    public static void shareGIF(Activity activity, String gifPath, ResolveInfo resolveInfo) {
        File gifFile = new File(gifPath);
        if (gifFile.exists()) {
            try {
                Intent intent = new Intent();
                String packageName = resolveInfo.activityInfo.packageName;
                String activityName = resolveInfo.activityInfo.name;
                // TODO 微信这种形式的分享失败，未知
                ComponentName comp = new ComponentName(packageName, activityName);
                intent.setComponent(comp);
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("image/gif");
                Uri uri = UriFixUtil.getUriFrom(activity.getApplicationContext(), gifFile);
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                }
                activity.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(activity, "未检测到客户端", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(activity, "Oops,File not found!", Toast.LENGTH_SHORT).show();
        }

    }

}
