package net.oschina.app.improve.main.synthesize.detail;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;

import net.oschina.app.R;
import net.oschina.app.api.remote.OSChinaApi;
import net.oschina.app.improve.app.AppOperator;
import net.oschina.app.improve.bean.Article;
import net.oschina.app.improve.bean.base.PageBean;
import net.oschina.app.improve.bean.base.ResultBean;
import net.oschina.app.improve.bean.comment.Comment;
import net.oschina.app.improve.main.update.OSCSharedPreference;

import java.lang.reflect.Type;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * 头条详情
 * Created by huanghaibin on 2017/10/23.
 */

class ArticleDetailPresenter implements ArticleDetailContract.Presenter {
    private final ArticleDetailContract.View mView;
    private final ArticleDetailContract.EmptyView mEmptyView;
    private String mNextToken;
    private final Article mArticle;

    ArticleDetailPresenter(ArticleDetailContract.View mView,ArticleDetailContract.EmptyView mEmptyView, Article article) {
        this.mView = mView;
        this.mArticle = article;
        this.mEmptyView = mEmptyView;
        this.mView.setPresenter(this);
    }

    @Override
    public void onRefreshing() {
        OSChinaApi.getArticleRecommends(
                mArticle.getKey(),
                OSCSharedPreference.getInstance().getDeviceUUID(),
                "",
                new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        try {
                            mView.showMoreMore();
                            mView.onComplete();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        try {
                            Type type = new TypeToken<ResultBean<PageBean<Article>>>() {
                            }.getType();
                            ResultBean<PageBean<Article>> bean = new Gson().fromJson(responseString, type);
                            if (bean != null && bean.isSuccess()) {
                                PageBean<Article> pageBean = bean.getResult();
                                mNextToken = pageBean.getNextPageToken();
                                List<Article> list = pageBean.getItems();
                                mView.onRefreshSuccess(list);
                                if (list.size() < 20) {
                                    mView.showMoreMore();
                                }
                            } else {
                                mView.showMoreMore();
                            }
                            mView.onComplete();
                        } catch (Exception e) {
                            e.printStackTrace();
                            mView.showMoreMore();
                            mView.onComplete();
                        }
                    }
                });
    }

    @Override
    public void onLoadMore() {
        OSChinaApi.getArticleRecommends(
                mArticle.getKey(),
                OSCSharedPreference.getInstance().getDeviceUUID(),
                mNextToken,
                new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        try {
                            mView.showMoreMore();
                            mView.onComplete();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        try {
                            Type type = new TypeToken<ResultBean<PageBean<Article>>>() {
                            }.getType();
                            ResultBean<PageBean<Article>> bean = new Gson().fromJson(responseString, type);
                            if (bean != null && bean.isSuccess()) {
                                PageBean<Article> pageBean = bean.getResult();
                                mNextToken = pageBean.getNextPageToken();
                                List<Article> list = pageBean.getItems();
                                mView.onLoadMoreSuccess(list);
                                if (list.size() < 20) {
                                    mView.showMoreMore();
                                }
                            } else {
                                mView.showMoreMore();
                            }
                            mView.onComplete();
                        } catch (Exception e) {
                            e.printStackTrace();
                            mView.showMoreMore();
                            mView.onComplete();
                        }
                    }
                });
    }

    @Override
    public void putArticleComment(String content, long referId, long reAuthorId) {
        OSChinaApi.pubArticleComment(mArticle.getKey(),
                content,
                referId,
                reAuthorId,
                new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        mView.showNetworkError(R.string.tip_network_error);
                        Log.e("onFailure","" + responseString);
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        Log.e("onSuccess","" + responseString);
                        try {
                            Type type = new TypeToken<ResultBean<Comment>>() {
                            }.getType();

                            ResultBean<Comment> resultBean = AppOperator.createGson().fromJson(responseString, type);
                            if (resultBean.isSuccess()) {
                                Comment respComment = resultBean.getResult();
                                if (respComment != null) {
                                    mView.showCommentSuccess(respComment);
                                    mEmptyView.showCommentSuccess(respComment);
                                }
                            } else {
                                mView.showCommentError(resultBean.getMessage());
                                mEmptyView.showCommentError(resultBean.getMessage());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            onFailure(statusCode, headers, responseString, e);
                            mView.showCommentError("评论失败");
                            mEmptyView.showCommentError("评论失败");
                        }
                    }
                });
    }


    static String parsePubDate(String pubDate) {
        if (TextUtils.isEmpty(pubDate) || pubDate.length() < 8)
            return pubDate;
        return String.format("%s-%s-%s", pubDate.substring(0, 4),
                pubDate.substring(4, 6),
                pubDate.substring(6, 8));
    }
}
