package net.oschina.app.improve.main.synthesize.top;

import android.view.View;

import net.oschina.app.improve.base.BaseRecyclerFragment;
import net.oschina.app.improve.base.adapter.BaseRecyclerAdapter;
import net.oschina.app.improve.bean.Article;
import net.oschina.app.improve.main.header.HeaderView;
import net.oschina.app.improve.main.header.NewsHeaderView;
import net.oschina.app.interf.OnTabReselectListener;

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
        mHeaderView = new NewsHeaderView(mContext, "https://www.oschina.net/action/apiv2/banner?catalog=1", "d6112fa662bc4bf21084670a857fbd20banner1");
        super.initData();
        mAdapter.setHeaderView(mHeaderView);
    }

    @Override
    protected void onItemClick(Article top, int position) {

    }

    @Override
    public void onRefreshSuccess(List<Article> data) {
        super.onRefreshSuccess(data);
        if(mHeaderView!= null){
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
        return new TopAdapter(mContext);
    }
}
