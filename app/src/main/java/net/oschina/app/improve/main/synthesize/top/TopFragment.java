package net.oschina.app.improve.main.synthesize.top;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import net.oschina.app.OSCApplication;
import net.oschina.app.improve.base.BaseRecyclerFragment;
import net.oschina.app.improve.base.adapter.BaseRecyclerAdapter;
import net.oschina.app.improve.bean.Top;
import net.oschina.app.interf.OnTabReselectListener;

/**
 * 头条界面
 * Created by huanghaibin on 2017/10/23.
 */

public class TopFragment extends BaseRecyclerFragment<TopContract.Presenter, Top> implements TopContract.View, OnTabReselectListener {

    public static TopFragment newInstance() {
        getDeviceId(OSCApplication.getInstance());
        return new TopFragment();
    }

    @Override
    protected void onItemClick(Top top, int position) {

    }

    @Override
    public void onTabReselect() {
        if (mRecyclerView != null && mPresenter != null) {
            mRecyclerView.scrollToPosition(0);
            mRefreshLayout.setRefreshing(true);
            onRefreshing();
        }
    }

    @Override
    protected BaseRecyclerAdapter<Top> getAdapter() {
        return new TopAdapter(mContext);
    }

    @SuppressLint("HardwareIds")
    private static String getDeviceId(Context context) {
        String androidId = Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID);
        if (!TextUtils.isEmpty(androidId)) {
            Log.e("getDeviceId", androidId);
            return androidId;
        }
        return android.os.Build.SERIAL;
    }
}
