package net.oschina.app.improve.main.synthesize.pub;

import android.content.Context;
import android.content.Intent;

import net.oschina.app.R;
import net.oschina.app.improve.base.activities.BackActivity;

/**
 * 发布分享文章
 * Created by huanghaibin on 2017/12/1.
 */

public class PubArticleActivity extends BackActivity {

    public static void show(Context context) {
        context.startActivity(new Intent(context, PubArticleActivity.class));
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_pub_article;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setDarkToolBar();
        setStatusBarDarkMode();
    }
}
