package net.oschina.app.improve.search.v2;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import net.oschina.app.R;
import net.oschina.app.improve.base.activities.BackActivity;
import net.oschina.app.improve.widget.SimplexToast;

/**
 * 新版搜索界面
 * Created by huanghaibin on 2018/1/4.
 */

public class SearchActivity extends BackActivity implements SearchContract.View ,View.OnClickListener{

    private SearchPresenter mPresenter;

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
        mPresenter = new SearchPresenter(this);
    }

    @Override
    public void onClick(View view) {

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

    }

    @Override
    public void showSearchFailure(int strId) {
        if (isDestroyed()) {
            return;
        }
        SimplexToast.show(this, strId);
    }

    @Override
    public void setPresenter(SearchContract.Presenter presenter) {
        // TODO: 2018/1/4
    }

}
