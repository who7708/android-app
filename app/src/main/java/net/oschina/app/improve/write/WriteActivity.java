package net.oschina.app.improve.write;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;

import net.oschina.app.R;
import net.oschina.app.improve.base.activities.BaseBackActivity;
import net.oschina.app.improve.widget.rich.RichEditLayout;
import net.oschina.app.improve.widget.rich.Section;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 写博客界面
 * Created by huanghaibin on 2017/8/3.
 */

public class WriteActivity extends BaseBackActivity implements View.OnClickListener,FontFragment.OnFontStyleChangeListener{

    @Bind(R.id.fl_content)
    FrameLayout mFrameContent;

    @Bind(R.id.richLayout)
    RichEditLayout mEditView;

    private FontFragment mFontFragment;
    private Handler mHandler = new Handler();

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
        mEditView.setContentPanel(mFrameContent);
        mFontFragment = FontFragment.newInstance();
        addFragment(R.id.fl_content, mFontFragment);
        mEditView.setOnSectionChangeListener(mFontFragment);
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
                if (mEditView.isKeyboardOpen() && mFrameContent.getVisibility() == View.GONE) {
                    mHandler.removeCallbacksAndMessages(null);
                    mEditView.closeKeyboard();
                    mFrameContent.setVisibility(View.GONE);
                    mEditView.setFontTint(0xff111111);
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mEditView.setAdjustResize();
                        }
                    }, 150);
                } else {
                    if (mFrameContent.getVisibility() == View.VISIBLE) {
                        mEditView.setAdjustNothing();
                        mEditView.setFontTint(0xff111111);
                    } else {
                        mEditView.setAdjustResize();
                    }
                    mEditView.openKeyboard();
                }
                break;
            case R.id.btn_font:
                mEditView.setAdjustNothing();
                if (mFrameContent.getVisibility() == View.VISIBLE) {
                    if (mEditView.isKeyboardOpen()) {
                        mEditView.closeKeyboard();
                        addFragment(R.id.fl_content, mFontFragment);
                        mFrameContent.setVisibility(View.VISIBLE);
                        mEditView.setFontTint(0xff24cf5f);
                    } else {
                        addFragment(R.id.fl_content, mFontFragment);
                        mFrameContent.setVisibility(View.GONE);
                        mEditView.setFontTint(0xff111111);
                        mEditView.closeKeyboard();
                    }

                } else {
                    mEditView.closeKeyboard();
                    addFragment(R.id.fl_content, mFontFragment);
                    mFrameContent.setVisibility(View.VISIBLE);
                    mEditView.setFontTint(0xff24cf5f);
                }
                break;
            case R.id.btn_url:

                break;
            case R.id.btn_commit:
                List<Section> sections = mEditView.createSectionList();
                if (sections != null) {

                    finish();
                }
                break;

        }
    }

    @Override
    public void onBoldChange(boolean isBold) {
        mEditView.setBold(isBold);
    }

    @Override
    public void onItalicChange(boolean isItalic) {
        mEditView.setItalic(isItalic);
    }

    @Override
    public void onMidLineChange(boolean isMidLine) {
        mEditView.setMidLine(isMidLine);
    }

    @Override
    public void onAlignChange(int align) {
        mEditView.setAlignStyle(align);
    }

    @Override
    public void onTitleChange(TitleAdapter.Title title) {
        mEditView.setTextSize(title.getSize());
    }

    @Override
    public void onColorChange(String color) {
        mEditView.setColorSpan(color);
    }
}
