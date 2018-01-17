package net.oschina.app.improve.main.synthesize.web;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import net.oschina.app.R;
import net.oschina.app.improve.base.activities.BackActivity;
import net.oschina.app.improve.main.MainActivity;
import net.oschina.app.improve.share.ShareDialog;
import net.oschina.app.improve.widget.OSCWebView;
import net.oschina.app.util.TDevice;

import butterknife.Bind;

/**
 * WebView
 * Created by huanghaibin on 2017/10/27.
 */

public class WebActivity extends BackActivity implements OSCWebView.OnFinishListener {

    protected OSCWebView mWebView;
    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;
    @Bind(R.id.ll_root)
    LinearLayout mLinearRoot;
    private String mTitle;
    protected ShareDialog mShareDialog;
    private String mUrl;
    private boolean isShowAd;

    public static void show(Context context, String url) {
        if (!TDevice.hasWebView(context))
            return;
        if (TextUtils.isEmpty(url))
            return;
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    public static void show(Context context, String url, boolean isShowAd) {
        if (TextUtils.isEmpty(url))
            return;
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("isShowAd", isShowAd);
        context.startActivity(intent);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_web;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void initWidget() {
        super.initWidget();

        mWebView = new OSCWebView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
        params.weight = 1;
        mWebView.setLayoutParams(params);
        mLinearRoot.addView(mWebView);
        mWebView.setOnFinishFinish(this);

        mUrl = getIntent().getStringExtra("url");
        isShowAd = getIntent().getBooleanExtra("isShowAd", false);
        setStatusBarDarkMode();
        setSwipeBackEnable(true);
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolBar != null) {
            setSupportActionBar(mToolBar);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setHomeButtonEnabled(false);
            }
            mToolBar.setTitleTextColor(Color.BLACK);
            DrawableCompat.setTint(mToolBar.getNavigationIcon(), Color.BLACK);
        }
        mShareDialog = new ShareDialog(this);

        if (!TextUtils.isEmpty(mUrl))
            mWebView.loadUrl(mUrl);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_web, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_share:
                if (!TextUtils.isEmpty(mTitle) && !TextUtils.isEmpty(mUrl)) {
                    mShareDialog.show();
                }
                break;
        }
        return false;
    }

    @Override
    public void onReceivedTitle(String title) {
        if (isDestroyed())
            return;
        mShareDialog.setTitle(title);
        mTitle = title;
        mShareDialog.init(this, title, null, mUrl);
        mToolBar.setTitle("返回");
    }

    @Override
    public void onProgressChange(int progress) {
        if (isDestroyed())
            return;
        mProgressBar.setProgress(progress);
    }

    @Override
    public void onError() {

    }

    @Override
    public void onFinish() {
        if (isDestroyed())
            return;
        mProgressBar.setVisibility(View.INVISIBLE);
    }


    @Override
    public void finish() {
        super.finish();
        if (isShowAd) {
            MainActivity.show(this);
        }
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.onDestroy();
        }
    }
}
