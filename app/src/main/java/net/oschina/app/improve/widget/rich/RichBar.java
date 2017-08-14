package net.oschina.app.improve.widget.rich;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import net.oschina.app.R;

/**
 * 底部工具栏
 * Created by huanghaibin on 2017/8/3.
 */


public class RichBar extends LinearLayout {
    ImageButton mBtnKeyboard;
    ImageButton mBtnFont;
    ImageButton mBtnUrl;

    public RichBar(Context context) {
        this(context, null);
    }

    public RichBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(getContext()).inflate(R.layout.view_rich_bar, this, true);
        mBtnKeyboard = (ImageButton) findViewById(R.id.btn_keyboard);
        mBtnFont = (ImageButton) findViewById(R.id.btn_font);
        mBtnUrl = (ImageButton) findViewById(R.id.btn_url);
    }
}