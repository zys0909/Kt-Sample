package com.dev.zhaoys.widget.webview;

/**
 * DefaultHandler
 */
public class DefaultHandler implements BridgeHandler {

    @Override
    public void handler(String data, CallBackFunction function) {
        if (function != null) {
            function.onCallBack("DefaultHandler response data");
        }
    }
}
