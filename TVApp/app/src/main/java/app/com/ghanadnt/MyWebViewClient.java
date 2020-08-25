package app.com.ghanadnt;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Admin on 20-Dec-16.
 */
public class MyWebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return false;
    }
}
