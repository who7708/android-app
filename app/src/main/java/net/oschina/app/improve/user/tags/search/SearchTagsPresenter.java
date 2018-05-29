package net.oschina.app.improve.user.tags.search;

/**
 * 用户搜索标签界面
 * Created by haibin on 2018/05/28.
 */
class SearchTagsPresenter implements SearchTagsContract.Presenter {
    private final SearchTagsContract.View mView;

    SearchTagsPresenter(SearchTagsContract.View mView) {
        this.mView = mView;
        this.mView.setPresenter(this);
    }
}
