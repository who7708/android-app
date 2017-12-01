package net.oschina.app.improve.main;

import android.content.Context;

/**
 * 剪切版监听
 * Created by huanghaibin on 2017/12/1.
 */

public final class ClipManager {

    private OnClipChangeListener mListener;

    public void register(Context context) {

    }

    interface OnClipChangeListener {
        void onClipChange();
    }
}
