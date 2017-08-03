package net.oschina.app.improve.widget.rich;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import java.util.List;

/**
 * 富文本编辑器入口,包含底部工具栏
 * Created by huanghaibin on 2017/8/3.
 */

@SuppressWarnings("unused")
public class RichEditLayout extends LinearLayout {
    private RichScrollView mScrollView;
    private RichBar mRichBar;
    View mContentPanel;//底部内容布局
    public RichEditLayout(Context context) {
        this(context, null);
    }
    public RichEditLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        mScrollView = new RichScrollView(context);
        LinearLayout.LayoutParams params =
                new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1f);
        mScrollView.setLayoutParams(params);
        addView(mScrollView);
        mRichBar = new RichBar(getContext());
        LinearLayout.LayoutParams richParams =
                new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mRichBar.setLayoutParams(richParams);
        addView(mRichBar);
    }
    public void insertImagePanel(String image) {
        mScrollView.addImagePanel(image);
    }
    public void setContentPanel(View view) {
        this.mContentPanel = view;
    }
    public void setFontStyle(boolean isBold) {
        mScrollView.mRichLinearLayout.setFontStyle(isBold);
    }
    public void setAlignStyle(int align) {
        mScrollView.mRichLinearLayout.setAlignStyle(align);
    }
    public void init(List<Section> sections) {
        mScrollView.mRichLinearLayout.init(sections);
    }
    public void setColorSpan(String color) {
        mScrollView.mRichLinearLayout.setColorSpan(color.replace("#", ""));
    }
    public void setTextSizeSpan(boolean isIncrease) {
        mScrollView.mRichLinearLayout.setTextSizeSpan(isIncrease);
    }
    public boolean isKeyboardOpen() {
        return mRichBar.getBottom() < UI.getScreenHeight(getContext()) - UI.dipToPx(getContext(), 80)
                && mContentPanel.getVisibility() == GONE;
    }
    public List<Section> createSectionList() {
        return mScrollView.createSectionList();
    }
    public int getImageCount() {
        return mScrollView.mRichLinearLayout.getImageCount();
    }
    public void openKeyboard() {
        mScrollView.mRichLinearLayout.mFocusView.setFocusable(true);
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
    public void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.hideSoftInputFromWindow(mScrollView.mRichLinearLayout.mFocusView.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }
    public void setOnSectionChangeListener(RichEditText.OnSectionChangeListener listener) {
        mScrollView.mRichLinearLayout.mListener = listener;
        mScrollView.mRichLinearLayout.mFocusView.mListener = listener;
    }
    public void setAdjustResize() {
        Window window = ((Activity) getContext()).getWindow();
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }
    public void setAdjustNothing() {
        Window window = ((Activity) getContext()).getWindow();
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
    }
}