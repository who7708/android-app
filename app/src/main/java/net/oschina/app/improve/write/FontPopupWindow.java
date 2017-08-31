package net.oschina.app.improve.write;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import net.oschina.app.R;

/**
 * 弹出H对话框
 * Created by huanghaibin on 2017/8/31.
 */

public class FontPopupWindow extends PopupWindow{
    @SuppressLint("InflateParams")
    FontPopupWindow(Context context) {
        super(LayoutInflater.from(context).inflate(R.layout.popup_window_font, null),
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setAnimationStyle(R.style.popup_anim_style_alpha);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setOutsideTouchable(true);
        setFocusable(true);

        View content = getContentView();

    }
}
