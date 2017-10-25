package net.oschina.app.improve.detail.general;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import net.oschina.app.R;
import net.oschina.app.improve.bean.News;
import net.oschina.app.improve.bean.SubBean;
import net.oschina.app.improve.detail.v2.DetailActivity;
import net.oschina.app.improve.detail.v2.DetailFragment;

/**
 * Created by haibin
 * on 2016/11/30.
 */

public class NewsDetailActivity extends DetailActivity {
    public static void show(Context context, SubBean bean) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("sub_bean", bean);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void show(Context context, long id, int type) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        Bundle bundle = new Bundle();
        SubBean bean = new SubBean();
        bean.setType(type);
        bean.setId(id);
        bundle.putSerializable("sub_bean", bean);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void show(Context context, long id) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        Bundle bundle = new Bundle();
        SubBean bean = new SubBean();
        bean.setType(News.TYPE_NEWS);
        bean.setId(id);
        bundle.putSerializable("sub_bean", bean);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static void show(Context context, long id, boolean isFav) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        Bundle bundle = new Bundle();
        SubBean bean = new SubBean();
        bean.setType(News.TYPE_NEWS);
        bean.setId(id);
        bean.setFavorite(isFav);
        bundle.putSerializable("sub_bean", bean);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void initWidget() {
        super.initWidget();
        setStatusBarDarkMode();
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
    }


    @Override
    protected DetailFragment getDetailFragment() {
        return NewsDetailFragment.newInstance();
    }
}
