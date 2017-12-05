package net.oschina.app.improve.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 浏览器,视频、图片都支持
 * Created by huanghaibin on 2017/10/27.
 */
@SuppressWarnings("unused")
public class OSCWebView extends WebView {

    private OnFinishListener mOnFinishFinish;
    private OnImageClickListener mImageClickListener;
    private OnVideoClickListener mVideoClickListener;

    public OSCWebView(Context context) {
        super(context, null);
    }

    public OSCWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init() {
        setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (mOnFinishFinish != null) {
                    mOnFinishFinish.onReceivedTitle(title);
                }
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if(mOnFinishFinish!= null){
                    mOnFinishFinish.onProgressChange(newProgress);
                }
            }
        });

        setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (mOnFinishFinish != null) {
                    mOnFinishFinish.onFinish();
                }
                addJavaScript();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                if (mOnFinishFinish != null) {
                    mOnFinishFinish.onError();
                }
            }
        });

        getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        getSettings().setJavaScriptEnabled(true);
        addJavascriptInterface(new JavascriptInterface(), "mark");

        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                OSCWebView webView = (OSCWebView) v;
                HitTestResult result = webView.getHitTestResult();
                if (null == result)
                    return false;

                int type = result.getType();
                if (type == WebView.HitTestResult.UNKNOWN_TYPE)
                    return false;

                if (type == WebView.HitTestResult.EDIT_TEXT_TYPE) {
                    // let TextViewhandles context menu
                    return false;
                }


                // Setup custom handlingdepending on the type
                switch (type) {
                    case WebView.HitTestResult.PHONE_TYPE:
                        // 处理拨号
                        break;
                    case WebView.HitTestResult.EMAIL_TYPE:
                        // 处理Email
                        break;
                    case WebView.HitTestResult.GEO_TYPE:
                        // TODO
                        break;
                    case WebView.HitTestResult.SRC_ANCHOR_TYPE:
                        // 超链接
                        Log.e("link", "超链接");
                        break;
                    case WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE:
                    case WebView.HitTestResult.IMAGE_TYPE:
                        // 处理长按图片的菜单项
                        Log.e("link", "处理图片的菜单项");
                        break;
                    default:
                        break;
                }

                return false;
            }
        });
    }

    private void addJavaScript() {
        loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "    objs[i].onclick=function()  " +
                "    {  "
                + "        window.mark.openImage(this.src);  " +
                "    }  " +
                "}" +
                "})()");

        loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"video\"); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "    objs[i].onclick=function()  " +
                "    {  "
                + "        window.mark.openVideo(this.src);  " +
                "    }  " +
                "}" +
                "})()");
    }

    public void setOnImageClickListener(OnImageClickListener listener) {
        this.mImageClickListener = listener;
    }

    public void setOnVideoClickListener(OnVideoClickListener listener) {
        this.mVideoClickListener = listener;
    }

    public void setOnFinishFinish(OnFinishListener listener) {
        this.mOnFinishFinish = listener;
    }

    private class JavascriptInterface {

        @android.webkit.JavascriptInterface
        public void openImage(String img) {
            if (mImageClickListener != null)
                mImageClickListener.onClick(img);
        }

        @android.webkit.JavascriptInterface
        public void openVideo(String img) {
            if (mVideoClickListener != null)
                mVideoClickListener.onClick(img);
        }

    }


    public interface OnImageClickListener {
        void onClick(String url);
    }

    public interface OnVideoClickListener {
        void onClick(String url);
    }

    public interface OnFinishListener {
        void onReceivedTitle(String title);

        void onProgressChange(int progress);

        void onError();

        void onFinish();
    }
}
