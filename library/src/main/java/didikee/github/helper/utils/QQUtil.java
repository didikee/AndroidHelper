package didikee.github.helper.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

/**
 * Created by didikee on 2017/9/1.
 */

public class QQUtil {
    /****************
     *
     * 发起添加群流程。群号：开源阅读交流反馈群(536734485) 的 key 为： JOfAbQLuD7szw-SZqLmFLrjc4aKb3ylh
     * 调用 joinQQGroup(JOfAbQLuD7szw-SZqLmFLrjc4aKb3ylh) 即可发起手Q客户端申请加群 开源阅读交流反馈群(536734485)
     *
     * key的申请地址: http://qun.qq.com/join.html
     * @param key 由官网生成的key
     * @param activity 跳转
     * @return 返回true表示呼起手Q成功，返回fals表示呼起失败
     ******************/
    public static boolean joinQQGroup(@NonNull Activity activity, String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D"
                + key));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            activity.startActivity(intent);
            return true;
        } catch (Exception e) {
            // 未安装手Q或安装的版本不支持
            return false;
        }
    }

}
