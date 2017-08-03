package net.oschina.app.improve.widget.rich;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.widget.LinearLayout;

/**
 * 底部工具栏
 * Created by huanghaibin on 2017/8/3.
 */


public class RichBar extends LinearLayout {
    private ImageButton mBtnKeyboard;
    private ImageButton mBtnFont;
    private ImageButton mBtnImage;
    public RichBar(Context context) {
        this(context, null);
    }
    public RichBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
//        LayoutInflater.from(getContext()).inflate(R.layout.view_rich_bar, this, true);
//        mBtnKeyboard = (ImageButton) findViewById(R.id.btn_keyboard);
//        mBtnFont = (ImageButton) findViewById(R.id.btn_font);
//        mBtnImage = (ImageButton) findViewById(R.id.btn_image);
    }
}