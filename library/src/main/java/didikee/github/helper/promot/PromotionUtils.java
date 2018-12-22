package didikee.github.helper.promot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import didikee.github.helper.utils.ClipboardUtil;
import didikee.github.helper.utils.NetworkStateUtil;


/**
 * Created by didikee on 2018/2/7.
 * 促进产品与用户之间的关系，用用户来驱动产品
 */

public class PromotionUtils {
    /**
     * 反馈的时候希望用户能带回来一些基本的设备信息
     * @param context 上下文
     * @return
     */
    public static String getFeedbackDeviceInfo(Context context) {
        String breakLine = "\n";
        StringBuilder sb = new StringBuilder();
        sb.append("Android: ").append(Build.VERSION.RELEASE).append(breakLine);
        sb.append("Android version: ").append(Build.VERSION.SDK_INT).append(breakLine);
        sb.append("Brand: ").append(Build.BRAND).append("-").append(Build.MODEL).append(breakLine);
        sb.append("Network state: ").append(NetworkStateUtil.getNetworkType(context)).append(breakLine);
        return sb.toString();
    }


    /****************
     *
     * 发起添加群流程。群号：EVD视频下载交流群(686833824) 的 key 为： Vm9SScj1NyUMxwLsY-OGtnf9Jl7Lb8Bc
     * 调用 joinQQGroup(Vm9SScj1NyUMxwLsY-OGtnf9Jl7Lb8Bc) 即可发起手Q客户端申请加群 EVD视频下载交流群(686833824)
     *
     * @param key 由官网生成的key
     * @return 返回true表示呼起手Q成功，返回fals表示呼起失败
     ******************/
    public static boolean joinQQGroup(Activity activity, String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            activity.startActivity(intent);
            return true;
        } catch (Exception e) {
            // 未安装手Q或安装的版本不支持
            Toast.makeText(activity, "未安装手Q或安装的版本不支持", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public static boolean joinQQGroup(Activity activity, String key, String qqGroupNumber) {
        ClipboardUtil.putString(activity, qqGroupNumber);
        Toast.makeText(activity, "群号已复制", Toast.LENGTH_SHORT).show();
        return joinQQGroup(activity, key);
    }

}
