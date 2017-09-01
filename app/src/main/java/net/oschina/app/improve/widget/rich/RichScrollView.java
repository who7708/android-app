package net.oschina.app.improve.widget.rich;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;

import java.util.List;

/**
 * 编辑滚动布局
 * Created by huanghaibin on 2017/8/3.
 */

public class RichScrollView extends NestedScrollView {
    RichLinearLayout mRichLinearLayout;
    RichEditLayout mParent;
    public RichScrollView(Context context) {
        this(context, null);
    }
    public RichScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setVerticalScrollBarEnabled(true);
        setHorizontalScrollBarEnabled(false);
        mRichLinearLayout = new RichLinearLayout(context);
        addView(mRichLinearLayout);
    }
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mParent == null) {
            mParent = (RichEditLayout) getParent();
        }
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }
    List<TextSection> createSectionList(){
        return mRichLinearLayout.createSectionList();
    }
    void addImagePanel(String image) {
        mRichLinearLayout.insertImagePanel(image);
    }
    boolean isKeyboardOpen() {
        return mParent.isKeyboardOpen();
    }
}