package net.oschina.app.improve.search.v2;

import net.oschina.app.improve.base.BasePresenter;
import net.oschina.app.improve.base.BaseView;

/**
 * 新版搜索界面
 * Created by huanghaibin on 2018/1/4.
 */
interface SearchContract {

    interface View extends BaseView<Presenter> {
        void showSearchSuccess(SearchBean searchBean);

        void showSearchFailure(int strId);
    }

    interface Presenter extends BasePresenter {
        void search(int type, int order, String keyword, String token);
    }
}
