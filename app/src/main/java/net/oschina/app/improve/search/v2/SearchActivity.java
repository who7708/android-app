package net.oschina.app.improve.search.v2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioGroup;

import net.oschina.app.R;
import net.oschina.app.improve.base.activities.BackActivity;
import net.oschina.app.improve.base.adapter.BaseRecyclerAdapter;
import net.oschina.app.improve.bean.Article;
import net.oschina.app.improve.search.software.SearchSoftwareActivity;
import net.oschina.app.improve.widget.RecyclerRefreshLayout;
import net.oschina.app.improve.widget.SimplexToast;

import java.util.List;

import butterknife.Bind;

/**
 * 新版搜索界面
 * Created by huanghaibin on 2018/1/4.
 */

public class SearchActivity extends BackActivity implements
        SearchContract.View, View.OnClickListener,

        BaseRecyclerAdapter.OnItemClickListener {

    private SearchPresenter mPresenter;
    @Bind(R.id.refreshLayout)
    RecyclerRefreshLayout mRefreshLayout;
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private SearchAdapter mAdapter;
    private SearchHeaderView mHeaderView;

    public static void show(Context context) {
        context.startActivity(new Intent(context, SearchActivity.class));
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_search;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setStatusBarDarkMode();
        setDarkToolBar();
        mHeaderView = new SearchHeaderView(this);
        mAdapter = new SearchAdapter(this);
        mAdapter.setHeaderView(mHeaderView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mRefreshLayout.setSuperRefreshLayoutListener(new RecyclerRefreshLayout.SuperRefreshLayoutListener() {
            @Override
            public void onRefreshing() {

            }

            @Override
            public void onLoadMore() {

            }

            @Override
            public void onScrollToBottom() {
                // TODO: 2018/1/5
            }
        });

        mHeaderView.setOrderChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_relate_order:
                        break;
                    case R.id.rb_hot_order:
                        break;
                    case R.id.rb_time_order:
                        break;
                }
            }
        });
        mHeaderView.setSearchSoftwareListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchSoftwareActivity.show(SearchActivity.this, "");
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter = new SearchPresenter(this);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(int position, long itemId) {

    }

    @Override
    public void showNetworkError(int strId) {
        if (isDestroyed()) {
            return;
        }
        SimplexToast.show(this, strId);
    }

    @Override
    public void showSearchSuccess(SearchBean searchBean) {
        if (isDestroyed())
            return;
        mHeaderView.setData(searchBean.getSoftwares());
        mAdapter.resetItem(searchBean.getArticles());
    }

    @Override
    public void showSearchFailure(int strId) {
        if (isDestroyed()) {
            return;
        }
        SimplexToast.show(this, strId);
    }

    @Override
    public void showNotMore() {
        if (isDestroyed()) {
            return;
        }
        mAdapter.setState(BaseRecyclerAdapter.STATE_NO_MORE, true);
    }

    @Override
    public void showLoadMoreSuccess(List<Article> articles) {
        if (isDestroyed()) {
            return;
        }
        mAdapter.addAll(articles);
    }

    @Override
    public void showSearchFailure(String str) {
        if (isDestroyed()) {
            return;
        }
        SimplexToast.show(this, str);
    }

    @Override
    public void setPresenter(SearchContract.Presenter presenter) {
        // TODO: 2018/1/4
    }

}
