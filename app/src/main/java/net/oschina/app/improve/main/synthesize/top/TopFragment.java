package net.oschina.app.improve.main.synthesize.top;

import android.text.TextUtils;
import android.view.View;

import net.oschina.app.improve.base.BaseRecyclerFragment;
import net.oschina.app.improve.base.adapter.BaseRecyclerAdapter;
import net.oschina.app.improve.bean.Article;
import net.oschina.app.improve.bean.News;
import net.oschina.app.improve.main.banner.HeaderView;
import net.oschina.app.improve.main.banner.NewsHeaderView;
import net.oschina.app.improve.main.synthesize.detail.ArticleDetailActivity;
import net.oschina.app.interf.OnTabReselectListener;
import net.oschina.app.util.UIHelper;

import java.util.List;

/**
 * 头条界面
 * Created by huanghaibin on 2017/10/23.
 */

public class TopFragment extends BaseRecyclerFragment<TopContract.Presenter, Article> implements TopContract.View, OnTabReselectListener {

    private HeaderView mHeaderView;

    public static TopFragment newInstance() {
        return new TopFragment();
    }

    @Override
    protected void initWidget(View root) {
        new TopPresenter(this);
        super.initWidget(root);
    }

    @Override
    protected void initData() {
        if (mPresenter != null) {
            mPresenter.loadCache();
        }
        mHeaderView = new NewsHeaderView(mContext, getImgLoader(),
                "https://www.oschina.net/action/apiv2/banner?catalog=1",
                "d6112fa662bc4bf21084670a857fbd20banner1");
        super.initData();
        mAdapter.setHeaderView(mHeaderView);
    }

    @Override
    protected void onItemClick(Article top, int position) {
        if (top.getType() == 0) {
            ArticleDetailActivity.show(mContext, top);
        } else {
            String key = top.getKey();
            if (TextUtils.isEmpty(key)) {
                ArticleDetailActivity.show(mContext, top);
                return;
            }
            String[] kv = key.split("_");
            if (kv.length != 3) {
                ArticleDetailActivity.show(mContext, top);
                return;
            }
            try {
                int type = Integer.parseInt(kv[1]);
                int id = Integer.parseInt(kv[2]);
                switch (type) {
                    case News.TYPE_SOFTWARE:
                        //SoftwareDetailActivity.show(mContext, sub.getId());
                        net.oschina.app.improve.detail.general.SoftwareDetailActivity.show(mContext, id);
                        break;
                    case News.TYPE_QUESTION:
                        //QuestionDetailActivity.show(mContext, sub.getId());
                        net.oschina.app.improve.detail.general.QuestionDetailActivity.show(mContext, id);
                        break;
                    case News.TYPE_BLOG:
                        //BlogDetailActivity.show(mContext, sub.getId());
                        net.oschina.app.improve.detail.general.BlogDetailActivity.show(mContext, id);
                        break;
                    case News.TYPE_TRANSLATE:
                        //TranslateDetailActivity.show(mContext, sub.getId());
                        net.oschina.app.improve.detail.general.NewsDetailActivity.show(mContext, id);
                        break;
                    case News.TYPE_EVENT:
                        //EventDetailActivity.show(mContext, sub.getId());
                        net.oschina.app.improve.detail.general.EventDetailActivity.show(mContext, id);
                        break;
                    case News.TYPE_NEWS:
                        //NewsDetailActivity.show(mContext, sub.getId());
                        net.oschina.app.improve.detail.general.NewsDetailActivity.show(mContext, id);
                        break;
                    default:
                        UIHelper.showUrlRedirect(mContext, top.getUrl());
                        break;
                }

            } catch (Exception e) {
                e.printStackTrace();
                ArticleDetailActivity.show(mContext, top);
            }
        }
    }

    @Override
    public void onRefreshSuccess(List<Article> data) {
        super.onRefreshSuccess(data);
        if (mHeaderView != null) {
            mHeaderView.requestBanner();
        }
    }

    @Override
    public void onTabReselect() {
        if (mRecyclerView != null && mPresenter != null) {
            mRecyclerView.scrollToPosition(0);
            mRefreshLayout.setRefreshing(true);
            onRefreshing();
        }
    }

    @Override
    protected BaseRecyclerAdapter<Article> getAdapter() {
        return new TopAdapter(mContext, BaseRecyclerAdapter.BOTH_HEADER_FOOTER);
    }
}
