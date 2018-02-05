package net.oschina.app.improve.main.synthesize.pub;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import net.oschina.app.R;
import net.oschina.app.api.remote.OSChinaApi;
import net.oschina.app.improve.bean.base.ResultBean;

import java.lang.reflect.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;

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
        if (!checkUrl(url)) {
            mView.showPubFailure("请填写正确的url");
            return;
        }
        OSChinaApi.putArticle(url,
                new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        mView.showPubFailure("投递失败");
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        try {
                            Type type = new TypeToken<ResultBean>() {
                            }.getType();
                            ResultBean bean = new Gson().fromJson(responseString, type);
                            if (bean != null) {
                                if (bean.getCode() == 1) {
                                    mView.showPubSuccess(R.string.pub_article_success);
                                } else {
                                    mView.showPubFailure(bean.getMessage());
                                }
                            } else {
                                mView.showPubFailure("投递失败");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private static final AsyncHttpClient mClient = new AsyncHttpClient();

//    @Override
//    public void getTitle(String url) {
//        if (!checkUrl(url)) {
//            return;
//        }
//        try {
//            mClient.get(url, new TextHttpResponseHandler() {
//                @Override
//                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                    mView.getTitleError();
//                }
//
//                @Override
//                public void onSuccess(int statusCode, Header[] headers, String responseString) {
//                    try {
//                        Pattern pattern = Pattern.compile("<title[^>]*>([^<>]+)</title>");
//                        Matcher matcher = pattern.matcher(responseString);
//                        if (matcher.find()) {
//                            mView.onGetTitleSuccess(matcher.group(1));
//                        } else {
//                            mView.getTitleError();
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        mView.getTitleError();
//                    }
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//            mView.showPubFailure("发布失败");
//        }
//    }

    static boolean checkUrl(String email) {
        Pattern pattern = Pattern.compile("^https?://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]");
        return pattern.matcher(email).find();
    }

    static String findUrl(String text) {
        Pattern pattern = Pattern.compile("https?://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }
}
