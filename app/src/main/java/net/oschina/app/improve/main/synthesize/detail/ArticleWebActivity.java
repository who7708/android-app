package net.oschina.app.improve.main.synthesize.detail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import net.oschina.app.R;
import net.oschina.app.bean.Report;
import net.oschina.app.improve.bean.Article;
import net.oschina.app.improve.detail.v2.ReportDialog;

/**
 * 头条浏览器
 * Created by huanghaibin on 2017/10/28.
 */

public class ArticleWebActivity extends WebActivity {
    private Article mArticle;

    public static void show(Context context, Article article) {
        if (article == null)
            return;
        Intent intent = new Intent(context, ArticleWebActivity.class);
        intent.putExtra("article", article);
        context.startActivity(intent);
    }

    @Override
    protected void initData() {
        super.initData();
        mArticle = (Article)getIntent().getSerializableExtra("article");
        mShareDialog.setTitle(mArticle.getTitle());
        mShareDialog.init(this, mArticle.getTitle(), null, mArticle.getUrl());
        mToolBar.setTitle(mArticle.getTitle());
        mWebView.loadUrl(mArticle.getUrl());
    }

    @Override
    public void onReceivedTitle(String title) {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_web_artivle, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_share:
                mShareDialog.show();
                break;
            case R.id.menu_report:
                if (mArticle != null) {
                    ReportDialog.create(this, 0, mArticle.getUrl(), Report.TYPE_BLOG).show();
                }
                break;
        }
        return false;
    }

}
