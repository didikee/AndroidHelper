package didikee.github.helper.webview;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by didikee on 2018/3/28.
 */

public class DefaultWebViewSettingsImpl {

    public static final String UA_CHROME = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36";
    public static final String UA_IPHONE6 = "Mozilla/5.0 (iPhone; CPU iPhone OS 6_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10A5376e Safari/8536.25";
    public static final String UA_WINDOWS_PHONE = "Mozilla/5.0 (compatible; MSIE 10.0; Windows Phone 8.0; Trident/6.0; IEMobile/10.0; ARM; Touch; NOKIA; Lumia 920)";
    public static final String UA_SAFARI = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) AppleWebKit/537.75.14 (KHTML, like Gecko) Version/7.0.3 Safari/7046A194A";


    @SuppressLint("SetJavaScriptEnabled")
    public WebSettings get(@NonNull WebView webView) {
        WebSettings webSettings = webView.getSettings();

        webSettings.setJavaScriptEnabled(true);

        webSettings.setSavePassword(false);
//        if (AgentWebUtils.checkNetwork(webView.getContext())) {
//            //根据cache-control获取数据。
//            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
//        } else {
//            //没网，则从本地获取，即离线加载
//            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //适配5.0不允许http和https混合使用情况
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        webSettings.setTextZoom(100);
        webSettings.setDatabaseEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setSupportMultipleWindows(false);
        // 是否阻塞加载网络图片  协议http or https
        webSettings.setBlockNetworkImage(false);
        // 允许加载本地文件html  file协议
        webSettings.setAllowFileAccess(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            // 通过 file url 加载的 Javascript 读取其他的本地文件 .建议关闭
            webSettings.setAllowFileAccessFromFileURLs(false);
            // 允许通过 file url 加载的 Javascript 可以访问其他的源，包括其他的文件和 http，https 等其他的源
            webSettings.setAllowUniversalAccessFromFileURLs(false);
        }
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        } else {
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }
//        webSettings.setLoadWithOverviewMode(false);
//        webSettings.setUseWideViewPort(false);
        webSettings.setDomStorageEnabled(true);
        webSettings.setNeedInitialFocus(true);
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        webSettings.setDefaultFontSize(16);
        webSettings.setMinimumFontSize(12);//设置 WebView 支持的最小字体大小，默认为 8
        webSettings.setGeolocationEnabled(true);
//        webSettings.setSupportZoom(true);
//        webSettings.setBuiltInZoomControls(false);

        boolean desktopMode = true;
        // 自适应pc & 缩放
        webSettings.setUseWideViewPort(desktopMode);
        webSettings.setLoadWithOverviewMode(desktopMode);
        webSettings.setSupportZoom(desktopMode);
        webSettings.setBuiltInZoomControls(desktopMode);
        //
//        String dir = AgentWebConfig.getCachePath(webView.getContext());

//        Log.i("webSettings", "dir:" + dir + "   appcache:" + AgentWebConfig.getCachePath(webView.getContext()));
        //设置数据库路径  api19 已经废弃,这里只针对 webkit 起作用
//        webSettings.setGeolocationDatabasePath(dir);
//        webSettings.setDatabasePath(dir);
//        webSettings.setAppCachePath(dir);

        //缓存文件最大值
        webSettings.setAppCacheMaxSize(Long.MAX_VALUE);

//        webSettings.setUserAgentString(getWebSettings()
//                .getUserAgentString()
//                .concat(USERAGENT_AGENTWEB)
//                .concat(USERAGENT_UC)
//        );


        Log.i("webSettings", "UserAgentString : " + webSettings.getUserAgentString());
        return webSettings;
    }
}
