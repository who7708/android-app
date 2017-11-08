package net.oschina.app.improve.main.synthesize.top;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;

import net.oschina.app.OSCApplication;
import net.oschina.app.api.remote.OSChinaApi;
import net.oschina.app.improve.bean.Article;
import net.oschina.app.improve.bean.base.PageBean;
import net.oschina.app.improve.bean.base.ResultBean;
import net.oschina.app.improve.main.update.OSCSharedPreference;
import net.oschina.app.improve.utils.CacheManager;
import net.oschina.common.utils.CollectionUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * 头条界面
 * Created by huanghaibin on 2017/10/23.
 */

class TopPresenter implements TopContract.Presenter {
    private final TopContract.View mView;
    private String mNextToken;
    private static final String CACHE_NAME = "article_list";

    TopPresenter(TopContract.View nView) {
        this.mView = nView;
        this.mView.setPresenter(this);
    }

    @Override
    public void loadCache() {
        List<Article> items = CacheManager.readListJson(OSCApplication.getInstance(), CACHE_NAME, Article.class);
        if (items != null) {
            mView.onRefreshSuccess(items);
            mView.onComplete();
        }
    }

    @Override
    public void onRefreshing() {
        OSChinaApi.getArticles(
                OSCSharedPreference.getInstance().getDeviceUUID(),
                "",
                new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Log.e("onFailure", "" + responseString);
                        try {
                            mView.showMoreMore();
                            mView.onComplete();
                        } catch (Exception e) {
                            e.printStackTrace();
                            mView.onComplete();
                        }
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        Log.e("onSuccess", "" + responseString);
                        try {
                            Type type = new TypeToken<ResultBean<PageBean<Article>>>() {
                            }.getType();
                            ResultBean<PageBean<Article>> bean = new Gson().fromJson(responseString, type);
                            if (bean != null && bean.isSuccess()) {
                                PageBean<Article> pageBean = bean.getResult();
                                mNextToken = pageBean.getNextPageToken();
                                List<Article> list = pageBean.getItems();
                                for (Article article : list) {
                                    article.setImgs(removeImgs(article.getImgs()));
                                }
                                CacheManager.saveToJson(OSCApplication.getInstance(), CACHE_NAME, list);
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
        OSChinaApi.getArticles(
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
                                for (Article article : list) {
                                    article.setImgs(removeImgs(article.getImgs()));
                                }
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

    private static String[] removeImgs(String[] imgs) {
        if (imgs == null || imgs.length == 0)
            return null;
        List<String> list = new ArrayList<>();
        for (String img : imgs) {
            if (!TextUtils.isEmpty(img)) {
                if (img.startsWith("http")) {
                    list.add(img + "!/both/246x330/quality/80");
                }
            }
        }
        return CollectionUtil.toArray(list, String.class);
    }

}
