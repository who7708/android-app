package net.oschina.app.improve.main.synthesize.top;

/**
 * 头条界面
 * Created by huanghaibin on 2017/10/23.
 */

 class TopPresenter implements TopContract.Presenter{
    private final TopContract.View nView;

     TopPresenter(TopContract.View nView) {
        this.nView = nView;
        this.nView.setPresenter(this);
    }

    @Override
    public void onRefreshing() {

    }

    @Override
    public void onLoadMore() {

    }
}
