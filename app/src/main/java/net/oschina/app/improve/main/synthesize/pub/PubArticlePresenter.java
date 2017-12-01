package net.oschina.app.improve.main.synthesize.pub;

/**
 * 发布分享文章
 * Created by huanghaibin on 2017/12/1.
 */

class PubArticlePresenter implements PubArticleContract.Presenter {
    private final PubArticleContract.View mView;

    PubArticlePresenter(PubArticleContract.View mView) {
        this.mView = mView;
        this.mView.setPresenter(this);
    }

    @Override
    public void putArticle(String url, String title) {

    }
}
