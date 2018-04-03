package didikee.github.helper.helper;

/**
 * Created by didikee on 2017/8/31.
 */

public class UpdateUtil {
    public static boolean needShowUpdateChangeLog(int savedVersion, int runtimeVersion) {
        return runtimeVersion > savedVersion;
    }
}
