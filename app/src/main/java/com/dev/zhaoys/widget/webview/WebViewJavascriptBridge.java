package com.dev.zhaoys.widget.webview;


/**
 * WebViewJavascriptBridge
 */
public interface WebViewJavascriptBridge {

    @SuppressWarnings("unused")
    void send(String data);

    void send(String data, CallBackFunction responseCallback);
}
