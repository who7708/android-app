package net.oschina.app.improve.write;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import net.oschina.app.R;
import net.oschina.app.improve.widget.rich.TextSection;

/**
 * 弹出H对话框
 * Created by huanghaibin on 2017/8/31.
 */

class HPopupWindow extends PopupWindow implements View.OnClickListener {
    private OnHeaderChangeListener mlistener;
    private TextView mTextH1, mTextH2, mTextH3, mTextH4;

    @SuppressLint("InflateParams")
    HPopupWindow(Context context, OnHeaderChangeListener listener) {
        super(LayoutInflater.from(context).inflate(R.layout.popup_window_h, null),
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setAnimationStyle(R.style.popup_anim_style_alpha);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setOutsideTouchable(true);
        setFocusable(false);
        this.mlistener = listener;
        View content = getContentView();
        mTextH1 = (TextView) content.findViewById(R.id.tv_h1);
        mTextH2 = (TextView) content.findViewById(R.id.tv_h2);
        mTextH3 = (TextView) content.findViewById(R.id.tv_h3);
        mTextH4 = (TextView) content.findViewById(R.id.tv_h4);

        mTextH1.setOnClickListener(this);
        mTextH2.setOnClickListener(this);
        mTextH3.setOnClickListener(this);
        mTextH4.setOnClickListener(this);
    }

    void setStyle(TextSection section) {
        mTextH1.setTextColor(0xFFFFFFFF);
        mTextH2.setTextColor(0xFFFFFFFF);
        mTextH3.setTextColor(0xFFFFFFFF);
        mTextH4.setTextColor(0xFFFFFFFF);
        switch (section.getTextSize()) {
            case 28:
                mTextH1.setTextColor(0xff24cf5f);
                break;
            case 24:
                mTextH2.setTextColor(0xff24cf5f);
                break;
            case 20:
                mTextH3.setTextColor(0xff24cf5f);
                break;
            case 16:
                mTextH4.setTextColor(0xff24cf5f);
                break;
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_h1:
                mlistener.onTitleChange(28);
                mTextH1.setTextColor(0xff24cf5f);
                break;
            case R.id.tv_h2:
                mlistener.onTitleChange(24);
                mTextH2.setTextColor(0xff24cf5f);
                break;
            case R.id.tv_h3:
                mlistener.onTitleChange(20);
                mTextH3.setTextColor(0xff24cf5f);
                break;
            case R.id.tv_h4:
                mlistener.onTitleChange(16);
                mTextH4.setTextColor(0xff24cf5f);
                break;
        }
    }

    void show(View v) {
        showAsDropDown(v, 0, -2 * v.getMeasuredHeight() + 10);
    }

    interface OnHeaderChangeListener {
        void onTitleChange(int size);
    }
}
