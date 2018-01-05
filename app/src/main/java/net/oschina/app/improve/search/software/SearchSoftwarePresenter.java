package net.oschina.app.improve.search.software;

/**
 * 搜索软件
 * Created by huanghaibin on 2018/1/5.
 */

class SearchSoftwarePresenter implements SearchSoftwareContract.Presenter {
    private final SearchSoftwareContract.View mView;

    SearchSoftwarePresenter(SearchSoftwareContract.View mView) {
        this.mView = mView;
        this.mView.setPresenter(this);
    }

    @Override
    public void onRefreshing() {

    }

    @Override
    public void onLoadMore() {

    }
}
