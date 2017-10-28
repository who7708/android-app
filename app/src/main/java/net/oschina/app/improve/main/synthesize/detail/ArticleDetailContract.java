package net.oschina.app.improve.main.synthesize.detail;

import net.oschina.app.improve.base.BaseListPresenter;
import net.oschina.app.improve.base.BaseListView;
import net.oschina.app.improve.bean.Article;

/**
 * 头条详情
 * Created by huanghaibin on 2017/10/23.
 */

interface ArticleDetailContract {

    interface View extends BaseListView<Presenter,Article>{

    }

    interface Presenter extends BaseListPresenter {

    }
}
