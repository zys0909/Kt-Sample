package com.dev.zhaoys.widget.webview;

import android.content.Context;
import android.webkit.WebView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

final class BridgeUtil {

    static final String YY_OVERRIDE_SCHEMA = "yy://";
    static final String YY_RETURN_DATA = YY_OVERRIDE_SCHEMA + "return/";//格式为   yy://return/{function}/returncontent
    private static final String YY_FETCH_QUEUE = YY_RETURN_DATA + "_fetchQueue/";
    private static final String EMPTY_STR = "";
    private static final String SPLIT_MARK = "/";

    static final String JS_HANDLE_MESSAGE_FROM_JAVA = "javascript:WebViewJavascriptBridge._handleMessageFromNative('%s');";
    static final String JS_FETCH_QUEUE_FROM_JAVA = "javascript:WebViewJavascriptBridge._fetchQueue();";

    private BridgeUtil(){
        //Utility Class
    }

    // 例子 javascript:WebViewJavascriptBridge._fetchQueue(); --> _fetchQueue
    static String parseFunctionName(String jsUrl) {
        return jsUrl.replace("javascript:WebViewJavascriptBridge.", "").replaceAll("\\(.*\\);", "");
    }

    // 获取到传递信息的body值
    // url = yy://return/_fetchQueue/[{"responseId":"JAVA_CB_2_3957","responseData":"Javascript Says Right back aka!"}]
    static String getDataFromReturnUrl(String url) {
        if (url.startsWith(YY_FETCH_QUEUE)) {
            // return = [{"responseId":"JAVA_CB_2_3957","responseData":"Javascript Says Right back aka!"}]
            return url.replace(YY_FETCH_QUEUE, EMPTY_STR);
        }

        // temp = _fetchQueue/[{"responseId":"JAVA_CB_2_3957","responseData":"Javascript Says Right back aka!"}]
        String temp = url.replace(YY_RETURN_DATA, EMPTY_STR);
        String[] functionAndData = temp.split(SPLIT_MARK);

        if (functionAndData.length >= 2) {
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < functionAndData.length; i++) {
                sb.append(functionAndData[i]);
            }
            // return = [{"responseId":"JAVA_CB_2_3957","responseData":"Javascript Says Right back aka!"}]
            return sb.toString();
        }
        return null;
    }

    // 获取到传递信息的方法
    // url = yy://return/_fetchQueue/[{"responseId":"JAVA_CB_1_360","responseData":"Javascript Says Right back aka!"}]
    static String getFunctionFromReturnUrl(String url) {
        // temp = _fetchQueue/[{"responseId":"JAVA_CB_1_360","responseData":"Javascript Says Right back aka!"}]
        String temp = url.replace(YY_RETURN_DATA, EMPTY_STR);
        String[] functionAndData = temp.split(SPLIT_MARK);
        if (functionAndData.length >= 1) {
            // functionAndData[0] = _fetchQueue
            return functionAndData[0];
        }
        return null;
    }


    /**
     * js 文件将注入为第一个script引用
     *
     * @param view WebView
     * @param url  url
     */
    @SuppressWarnings("unused")
    public static void webViewLoadJs(WebView view, String url) {
        String js = "var newscript = document.createElement(\"script\");";
        js += "newscript.src=\"" + url + "\";";
        js += "document.scripts[0].parentNode.insertBefore(newscript,document.scripts[0]);";
        view.loadUrl("javascript:" + js);
    }

    /**
     * 这里只是加载lib包中assets中的 WebViewJavascriptBridge.js
     *
     * @param view webview
     * @param path 路径
     */
    @SuppressWarnings("unused")
     static void webViewLoadLocalJs(WebView view, String path) {
        String jsContent = assetFile2Str(view.getContext(), path);
        view.loadUrl("javascript:" + jsContent);
    }

    /**
     * 解析assets文件夹里面的代码,去除注释,取可执行的代码
     *
     * @param c      context
     * @param urlStr 路径
     * @return 可执行代码
     */
    private static String assetFile2Str(Context c, String urlStr) {
        InputStream in = null;
        BufferedReader bufferedReader = null;
        try {
            in = c.getAssets().open(urlStr);
            bufferedReader = new BufferedReader(new InputStreamReader(in));
            String line;
            StringBuilder sb = new StringBuilder();
            do {
                line = bufferedReader.readLine();
                if (line != null && !line.matches("^\\s*\\/\\/.*")) { // 去除注释
                    sb.append(line);
                }
            } while (line != null);

            bufferedReader.close();
            in.close();

            return sb.toString();
        } catch (Exception e) {
            //ignore
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    //ignore
                }
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    //ignore
                }
            }
        }
        return null;
    }
}
