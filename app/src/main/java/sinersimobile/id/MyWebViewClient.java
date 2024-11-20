package sinersimobile.id;

import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;

public interface MyWebViewClient {
    boolean ShouldOverrideUrlLoading(WebView view, String url);

    void onRecivedError(WebView view, int errorCode, String description, String failingUrl);

    void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error);
}
