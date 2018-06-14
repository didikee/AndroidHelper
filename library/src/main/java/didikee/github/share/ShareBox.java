package didikee.github.share;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.util.List;

/**
 * ShareBox
 *
 * @author baishixian
 * @date 2018/3/29 11:00
 */

public class ShareBox {

    private static final String TAG = "ShareBox";
    private static final int NO_RESULT = Activity.RESULT_CANCELED;

    /**
     * Current activity
     */
    private Activity activity;

    /**
     * Share content type
     */
    private String contentType;

    /**
     * Share title
     */
    private String title;

    /**
     * Share file Uri
     */
    private Uri shareFileUri;

    /**
     * Share content text
     */
    private String contentText;

    /**
     * Share to special component PackageName
     */
    private String componentPackageName;

    /**
     * Share to special component ClassName
     */
    private String componentClassName;

    /**
     * Share complete onActivityResult requestCode
     */
    private int requestCode;

    /**
     * Forced Use System Chooser
     */
    private boolean forcedUseSystemChooser;

    private ShareBox() {
    }

    private ShareBox(@NonNull Builder builder) {
        this.activity = builder.activity;
        this.contentType = builder.contentType;
        this.title = builder.title;
        this.shareFileUri = builder.shareFileUri;
        this.contentText = builder.textContent;
        this.componentPackageName = builder.componentPackageName;
        this.componentClassName = builder.componentClassName;
        this.requestCode = builder.requestCode;
        this.forcedUseSystemChooser = builder.forcedUseSystemChooser;
    }

    /**
     * shareBySystem
     */
    public void shareBySystem() {
        if (checkShareParam()) {
            Intent shareIntent = createShareIntent();

            if (shareIntent == null) {
                Log.e(TAG, "shareBySystem cancel.");
                return;
            }

            if (title == null) {
                title = "";
            }

            if (forcedUseSystemChooser) {
                shareIntent = Intent.createChooser(shareIntent, title);
            }

            if (shareIntent.resolveActivity(activity.getPackageManager()) != null) {
                try {
                    if (requestCode == NO_RESULT) {
                        activity.startActivity(shareIntent);
                    } else {
                        activity.startActivityForResult(shareIntent, requestCode);
                    }
                } catch (Exception e) {
                    Log.e(TAG, Log.getStackTraceString(e));
                }
            }
        } else {
            Log.d(TAG, "Share params Not Match!");
        }
    }

    private Intent createShareIntent() {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shareIntent.addCategory("android.intent.category.DEFAULT");

        if (!TextUtils.isEmpty(this.componentPackageName) && !TextUtils.isEmpty(componentClassName)) {
            ComponentName comp = new ComponentName(componentPackageName, componentClassName);
            shareIntent.setComponent(comp);
        }

        if (contentType.equalsIgnoreCase(ShareType.TEXT)) {
            shareIntent.putExtra(Intent.EXTRA_TEXT, contentText);
            shareIntent.setType("text/plain");
        } else {
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.addCategory("android.intent.category.DEFAULT");
            shareIntent.setType(contentType);
            shareIntent.putExtra(Intent.EXTRA_STREAM, shareFileUri);
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            Log.d(TAG, "Share uri: " + shareFileUri.toString());

            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                List<ResolveInfo> resInfoList = activity.getPackageManager().queryIntentActivities(shareIntent, PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo resolveInfo : resInfoList) {
                    String packageName = resolveInfo.activityInfo.packageName;
                    activity.grantUriPermission(packageName, shareFileUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
            }
        }
        return shareIntent;
    }


    private boolean checkShareParam() {
        if (this.activity == null) {
            Log.e(TAG, "activity is null.");
            return false;
        }

        if (TextUtils.isEmpty(this.contentType)) {
            Log.e(TAG, "Share content type is empty.");
            return false;
        }

        if (ShareType.TEXT.equals(contentType)) {
            if (TextUtils.isEmpty(contentText)) {
                Log.e(TAG, "Share text context is empty.");
                return false;
            }
        } else {
            if (this.shareFileUri == null) {
                Log.e(TAG, "Share file path is null.");
                return false;
            }
        }
        return true;
    }

    public static class Builder {
        private Activity activity;
        private String contentType = ShareType.FILE;
        // 标题
        private String title;
        // 分享文件的Uri
        private Uri shareFileUri;
        // 分享文字需要的文字
        private String textContent;
        // 请求code
        private int requestCode = NO_RESULT;
        // 包名
        private String componentPackageName;
        // 类名
        private String componentClassName;
        // 是否使用系统分享
        private boolean forcedUseSystemChooser = true;

        public Builder(Activity activity, String textContent) {
            this.activity = activity;
            this.textContent = textContent;
            this.contentType = ShareType.TEXT;
        }

        public Builder(Activity activity, String contentType, File file) {
            this.activity = activity;
            this.contentType = contentType;
            this.shareFileUri = ShareFileUtil.getFileUri(activity, contentType, file);
        }

        /**
         * Set Title
         * @param title title
         * @return Builder
         */
        public Builder setTitle(@NonNull String title) {
            this.title = title;
            return this;
        }

        /**
         * Set Share To Component
         * @param componentPackageName componentPackageName
         * @param componentClassName componentPackageName
         * @return Builder
         */
        public Builder setShareToComponent(String componentPackageName, String componentClassName) {
            this.componentPackageName = componentPackageName;
            this.componentClassName = componentClassName;
            return this;
        }

        /**
         * Set onActivityResult requestCode, default value is -1
         * @param requestCode requestCode
         * @return Builder
         */
        public Builder setOnActivityResult(int requestCode) {
            this.requestCode = requestCode;
            return this;
        }

        /**
         * Forced Use System Chooser To Share
         * @param enable default is true
         * @return Builder
         */
        public Builder forcedUseSystemChooser(boolean enable) {
            this.forcedUseSystemChooser = enable;
            return this;
        }

        /**
         * build
         * @return ShareBox
         */
        public ShareBox build() {
            return new ShareBox(this);
        }

        public void shareBySystem() {
            new ShareBox(this).shareBySystem();
        }

    }
}
