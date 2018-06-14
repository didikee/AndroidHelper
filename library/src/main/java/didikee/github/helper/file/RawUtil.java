package didikee.github.helper.file;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by didikee on 02/07/2017.
 */

public class RawUtil {

    public static String readString(Context context, int rawResId) {
        InputStream is = read(context, rawResId);
        InputStreamReader isReader = new InputStreamReader(is);
        BufferedReader r = new BufferedReader(isReader);
        StringBuilder total = new StringBuilder();
        String line;
        try {
            while ((line = r.readLine()) != null) {
                total.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            is.close();
            isReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return total.toString();
    }

    public static InputStream read(Context context, int rawResId) {
        if (context == null) return null;
        return context.getResources().openRawResource(rawResId);
    }
}
