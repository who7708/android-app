package net.oschina.app.improve.main.introduce;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.WindowManager;

import net.oschina.app.R;
import net.oschina.app.improve.base.activities.BaseActivity;
import net.oschina.app.improve.main.update.OSCSharedPreference;

import butterknife.OnClick;

/**
 * 推荐列表首页适配
 * Created by huanghaibin on 2017/11/27.
 */

public class ArticleIntroduceActivity extends BaseActivity implements View.OnClickListener {
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

    @OnClick(R.id.tv_sure)
    @Override
    public void onClick(View v) {
        OSCSharedPreference.getInstance().putFirstUsing();
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }
}
