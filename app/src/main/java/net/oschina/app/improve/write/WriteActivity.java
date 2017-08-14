package net.oschina.app.improve.write;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;

import net.oschina.app.R;
import net.oschina.app.improve.base.activities.BaseBackActivity;
import net.oschina.app.improve.widget.rich.RichEditLayout;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 写博客界面
 * Created by huanghaibin on 2017/8/3.
 */

public class WriteActivity extends BaseBackActivity implements View.OnClickListener {

    @Bind(R.id.fl_content)
    FrameLayout mFrameContent;

    @Bind(R.id.richLayout)
    RichEditLayout mRichLayout;

    public static void show(Context context) {
        context.startActivity(new Intent(context, WriteActivity.class));
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_write;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mRichLayout.setContentPanel(mFrameContent);
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @OnClick({R.id.btn_keyboard, R.id.btn_font, R.id.btn_url, R.id.btn_commit})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_keyboard:
                break;
            case R.id.btn_font:
                break;
            case R.id.btn_url:
                break;
            case R.id.btn_commit:
                break;
        }

    }
}
