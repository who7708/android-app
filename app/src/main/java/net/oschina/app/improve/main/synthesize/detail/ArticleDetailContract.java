package net.oschina.app.improve.main.synthesize.detail;

import net.oschina.app.improve.base.BaseListPresenter;
import net.oschina.app.improve.base.BaseListView;
import net.oschina.app.improve.bean.Article;
import net.oschina.app.improve.bean.SubBean;
import net.oschina.app.improve.bean.comment.Comment;

/**
 * 头条详情
 * Created by huanghaibin on 2017/10/23.
 */

interface ArticleDetailContract {

    interface EmptyView {

        void showCommentSuccess(Comment comment);

        void showCommentError(String message);

    }

    interface View extends BaseListView<Presenter,Article>{
        void showCommentSuccess(Comment comment);

        void showCommentError(String message);
    }

    interface Presenter extends BaseListPresenter {
        void putArticleComment(String content, long referId, long reAuthorId);
    }
}
