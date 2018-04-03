package didikee.github.helper.file;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;

import didikee.github.helper.R;

/**
 * Created by didikee on 2017/12/16.
 * fix android 7.0 Uri.fromFile(file); crash
 */

public class UriFixUtil {

    public static Uri getUriFrom(Context applicationContext, File file) {
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(applicationContext, applicationContext.getString(R.string.path_authorities), file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }
}
