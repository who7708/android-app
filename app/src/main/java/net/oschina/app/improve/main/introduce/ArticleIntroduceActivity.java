package net.oschina.app.improve.main.introduce;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.oschina.app.R;
import net.oschina.app.improve.base.activities.BaseActivity;
import net.oschina.app.improve.main.update.OSCSharedPreference;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 推荐列表首页适配
 * Created by huanghaibin on 2017/11/27.
 */

public class ArticleIntroduceActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.tv_recommend)
    TextView mTextRecommend;

    @Bind(R.id.tv_next)
    TextView mTextNext;

    @Bind(R.id.tv_sure)
    TextView mTextSure;

    @Bind(R.id.arrow_up)
    View mViewUp;

    @Bind(R.id.ll_pop)
    LinearLayout mLinearPop;

    @Bind(R.id.ll_tweet_tip)
    LinearLayout mLinearTweetTip;

    @Bind(R.id.ll_tweet_arrow)
    LinearLayout mLinearTweetArrow;

    @Bind(R.id.tv_tip_tweet)
    TextView mTextTweetTip;


    public static void show(Context context) {
        if (!OSCSharedPreference.getInstance().isFirstUsing()) {
            return;
        }
        context.startActivity(new Intent(context, ArticleIntroduceActivity.class));
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_article_introduce;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
    }

    @OnClick({R.id.tv_sure, R.id.tv_next})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_sure:
                OSCSharedPreference.getInstance().putFirstUsing();
                finish();
                break;
            case R.id.tv_next:
                mTextNext.setVisibility(View.GONE);
                mTextRecommend.setVisibility(View.GONE);
                mLinearPop.setVisibility(View.GONE);
                mViewUp.setVisibility(View.GONE);

                mTextSure.setVisibility(View.VISIBLE);
                mLinearTweetTip.setVisibility(View.VISIBLE);
                mTextTweetTip.setVisibility(View.VISIBLE);
                mLinearTweetArrow.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }
}
