package net.oschina.app.improve.main.synthesize.detail;

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

import net.oschina.app.R;
import net.oschina.app.bean.Report;
import net.oschina.app.improve.base.activities.BackActivity;
import net.oschina.app.improve.bean.Article;
import net.oschina.app.improve.detail.v2.ReportDialog;
import net.oschina.app.improve.share.ShareDialog;
import net.oschina.app.improve.widget.OSCWebView;

import butterknife.Bind;

/**
 * WebView
 * Created by huanghaibin on 2017/10/27.
 */

public class WebActivity extends BackActivity implements OSCWebView.OnFinishListener {
    @Bind(R.id.webView)
    OSCWebView mWebView;
    private String mTitle;
    private ShareDialog mShareDialog;
    private String mUrl;
    private Article mArticle;
    private int mType;
    private static final int TYPE_URL = 1;
    private static final int TYPE_ARTICLE = 2;

    public static void show(Context context, String url) {
        if (TextUtils.isEmpty(url))
            return;
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("type", TYPE_URL);
        context.startActivity(intent);
    }

    public static void show(Context context, Article article) {
        if (article == null)
            return;
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("article", article);
        intent.putExtra("type", TYPE_ARTICLE);
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
        mType = getIntent().getIntExtra("type", 0);
        if (mType == TYPE_URL) {
            mUrl = getIntent().getStringExtra("url");
        } else if (mType == TYPE_ARTICLE) {
            mArticle = (Article) getIntent().getSerializableExtra("article");
        } else {
            finish();
            return;
        }


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
        mWebView.setOnFinishFinish(this);
        mWebView.loadUrl(mUrl);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(mType == TYPE_URL ? R.menu.menu_web : R.menu.menu_web_artivle, menu);
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
            case R.id.menu_report:
                if (mType == TYPE_ARTICLE && mArticle != null) {
                    ReportDialog.create(this, 0, mArticle.getUrl(), Report.TYPE_BLOG).show();
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
        mToolBar.setTitle(title);
    }

    @Override
    public void onFinish() {
        // TODO: 2017/10/27
    }


    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
