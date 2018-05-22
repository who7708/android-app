package net.oschina.app.improve.user.tags;

/**
 * 用户标签界面
 * Created by haibin on 2018/05/22.
 */
class UserTagsPresenter implements UserTagsContract.Presenter {
    private final UserTagsContract.View mView;

    UserTagsPresenter(UserTagsContract.View mView) {
        this.mView = mView;
        this.mView.setPresenter(this);
    }
}
