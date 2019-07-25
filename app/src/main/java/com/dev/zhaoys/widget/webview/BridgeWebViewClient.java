package com.dev.zhaoys.widget.webview;

import android.os.Build;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * BridgeWebViewClient
 */
public class BridgeWebViewClient extends WebViewClient {

    private final BridgeWebView mBridgeWebView;

    public BridgeWebViewClient(BridgeWebView bridgeWebView) {
        mBridgeWebView = bridgeWebView;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {
        try {
            url = URLDecoder.decode(url, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (url.startsWith(BridgeUtil.YY_RETURN_DATA)) {
            mBridgeWebView.handlerReturnData(url);
            return true;
        } else if (url.startsWith(BridgeUtil.YY_OVERRIDE_SCHEMA)) {
            mBridgeWebView.flushMessageQueue();
            return true;
        } else {
            return super.shouldOverrideUrlLoading(webView, url);
        }
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String url = webResourceRequest.getUrl().toString();
            try {
                url = URLDecoder.decode(url, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (url.startsWith(BridgeUtil.YY_RETURN_DATA)) {
                mBridgeWebView.handlerReturnData(url);
                return true;
            } else if (url.startsWith(BridgeUtil.YY_OVERRIDE_SCHEMA)) {
                mBridgeWebView.flushMessageQueue();
                return true;
            } else {
                return super.shouldOverrideUrlLoading(webView, webResourceRequest);
            }
        } else {
            return super.shouldOverrideUrlLoading(webView, webResourceRequest);
        }
    }

    @Override
    public void onPageFinished(WebView webView, String url) {
        super.onPageFinished(webView, url);
        BridgeUtil.webViewLoadLocalJs(webView, BridgeWebView.TO_LOAD_JS);
        BridgeWebView bridgeWebView = (BridgeWebView) webView;
        if (bridgeWebView.getStartupMessage() != null) {
            for (Message message : bridgeWebView.getStartupMessage()) {
                bridgeWebView.dispatchMessage(message);
            }
            bridgeWebView.setStartupMessage(null);
        }
    }
}
