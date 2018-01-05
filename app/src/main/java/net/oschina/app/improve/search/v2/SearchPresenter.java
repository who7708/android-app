package net.oschina.app.improve.search.v2;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;

import net.oschina.app.R;
import net.oschina.app.api.remote.OSChinaApi;
import net.oschina.app.improve.bean.base.ResultBean;

import java.lang.reflect.Type;

import cz.msebera.android.httpclient.Header;

/**
 * 新版搜索界面
 * Created by huanghaibin on 2018/1/4.
 */

class SearchPresenter implements SearchContract.Presenter {
    private final SearchContract.View mView;
    private String mToken;
    private int mOrder;
    private int mType;
    private String mKeyword;

    SearchPresenter(SearchContract.View mView) {
        this.mView = mView;
        this.mView.setPresenter(this);
    }

    @Override
    public void search(int type, int order, String keyword) {
        if (TextUtils.isEmpty(keyword)) {
            mView.showSearchFailure(R.string.search_keyword_empty_error);
            return;
        }
        mKeyword = keyword;
        OSChinaApi.search(type, order, keyword, null,
                new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        mView.showSearchFailure(R.string.network_timeout_hint);
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        try {
                            ResultBean<SearchBean> bean = new Gson().fromJson(responseString, getType());
                            if (bean != null) {
                                if (bean.isSuccess() && bean.getResult() != null) {
                                    mView.showSearchSuccess(bean.getResult());
                                } else {
                                    mView.showSearchFailure(bean.getMessage());
                                }
                            } else {
                                mView.showSearchFailure(R.string.search_error);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


    @Override
    public void searchMore(int type, int order, String keyword) {
        if (TextUtils.isEmpty(keyword)) {
            mView.showSearchFailure(R.string.search_keyword_empty_error);
            return;
        }
        OSChinaApi.search(type, order, keyword, null,
                new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        mView.showSearchFailure(R.string.network_timeout_hint);
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        try {
                            ResultBean<SearchBean> bean = new Gson().fromJson(responseString, getType());
                            if (bean != null) {
                                if (bean.isSuccess() && bean.getResult() != null) {
                                    mView.showSearchSuccess(bean.getResult());
                                } else {
                                    mView.showSearchFailure(bean.getMessage());
                                }
                            } else {
                                mView.showSearchFailure(R.string.search_error);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private static Type getType() {
        return new TypeToken<ResultBean<SearchBean>>() {
        }.getType();
    }
}
