package net.oschina.app.improve.main.synthesize.pub;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import net.oschina.app.R;
import net.oschina.app.improve.base.activities.BackActivity;
import net.oschina.app.improve.widget.SimplexToast;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 发布分享文章
 * Created by huanghaibin on 2017/12/1.
 */

public class PubArticleActivity extends BackActivity implements PubArticleContract.View,
        View.OnClickListener {

    @Bind(R.id.et_url)
    EditText mTextUrl;

    @Bind(R.id.tv_title)
    TextView mTextTitle;


    private PubArticlePresenter mPresenter;

    public static void show(Context context, String url) {
        Intent intent = new Intent(context, PubArticleActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_pub_article;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setDarkToolBar();
        setStatusBarDarkMode();
    }

    @Override
    protected void initData() {
        super.initData();
        String mUrl = getIntent().getStringExtra("url");
        mTextUrl.setText(mUrl);
        mPresenter = new PubArticlePresenter(this);
        mTextUrl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mPresenter != null) {
                    mPresenter.getTitle(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mPresenter.getTitle(mUrl);
    }

    @OnClick({R.id.btn_commit})
    @Override
    public void onClick(View v) {
        mPresenter.putArticle(mTextUrl.getText().toString().trim(), "");
    }

    @Override
    public void onGetTitleSuccess(String title) {
        if (isDestroyed()) {
            return;
        }
        mTextTitle.setText(title);
    }

    @Override
    public void getTitleError() {
        if (isDestroyed()) {
            return;
        }
        mTextTitle.setText("获取标题失败");
    }


    @Override
    public void showPubSuccess(int strId) {
        if (isDestroyed()) {
            return;
        }
        SimplexToast.show(this, strId);
        finish();
    }

    @Override
    public void showPubFailure(String message) {
        if (isDestroyed()) {
            return;
        }
        SimplexToast.show(this, message);
    }


    @Override
    public void setPresenter(PubArticleContract.Presenter presenter) {

    }

    @Override
    public void showNetworkError(int strId) {

    }
}
