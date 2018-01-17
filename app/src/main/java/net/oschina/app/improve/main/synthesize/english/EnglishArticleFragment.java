package net.oschina.app.improve.main.synthesize.english;

import android.view.View;

import net.oschina.app.OSCApplication;
import net.oschina.app.improve.base.BaseRecyclerFragment;
import net.oschina.app.improve.base.adapter.BaseRecyclerAdapter;
import net.oschina.app.improve.bean.Article;
import net.oschina.app.improve.main.introduce.ArticleIntroduceActivity;
import net.oschina.app.improve.main.synthesize.english.detail.EnglishArticleDetailActivity;
import net.oschina.app.interf.OnTabReselectListener;
import net.oschina.app.util.TDevice;

import java.util.List;

/**
 * 英文推荐界面
 * Created by huanghaibin on 2017/10/23.
 */

public class EnglishArticleFragment extends BaseRecyclerFragment<EnglishArticleContract.Presenter, Article> implements EnglishArticleContract.View, OnTabReselectListener {

    private OSCApplication.ReadState mReadState;

    public static EnglishArticleFragment newInstance() {
        return new EnglishArticleFragment();
    }

    @Override
    protected void initWidget(View root) {
        new EnglishArticlePresenter(this);
        super.initWidget(root);
    }

    @Override
    protected void initData() {
        ArticleIntroduceActivity.show(mContext);
        mReadState = OSCApplication.getReadState("sub_list");
        if (mPresenter != null) {
            mPresenter.loadCache();
        }
        super.initData();
        mRefreshLayout.setBottomCount(2);
    }

    @Override
    protected void onItemClick(Article top, int position) {
        if (!TDevice.hasWebView(mContext))
            return;
        EnglishArticleDetailActivity.show(mContext, top);
        mReadState.put(top.getKey());
        mAdapter.updateItem(position);
    }

    @Override
    public void onScrollToBottom() {
        if (mPresenter != null) {
            mAdapter.setState(BaseRecyclerAdapter.STATE_LOADING, true);
            mPresenter.onLoadMore();
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
    public void onRefreshSuccess(List<Article> data) {
        super.onRefreshSuccess(data);
        if (data.size() < 8) {
            mRefreshLayout.setOnLoading(true);
            onLoadMore();
        }
    }

    @Override
    protected BaseRecyclerAdapter<Article> getAdapter() {
        return new EnglishArticleAdapter(mContext, BaseRecyclerAdapter.ONLY_FOOTER);
    }
}
