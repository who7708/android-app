package net.oschina.app.improve.detail.share;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v4.widget.NestedScrollView;
import android.view.View;

import net.oschina.app.R;
import net.oschina.app.improve.base.fragments.BaseFragment;
import net.oschina.app.improve.bean.SubBean;
import net.oschina.app.improve.dialog.ShareDialog;
import net.oschina.app.improve.utils.DialogHelper;
import net.oschina.app.improve.widget.OWebView;

/**
 * 文章详情分享界面
 * Created by huanghaibin on 2017/9/25.
 */

public abstract class ShareFragment extends BaseFragment implements Runnable{
    protected OWebView mWebView;
    protected SubBean mBean;
    private ShareDialog mShareDialog;
    private ProgressDialog mDialog;
    private Bitmap mBitmap;
    private boolean isShare;
    protected NestedScrollView mViewScroller;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_share;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mWebView = (OWebView) mRoot.findViewById(R.id.webView);
        mBean = (SubBean) getArguments().getSerializable("bean");
        mViewScroller = (NestedScrollView) mRoot.findViewById(R.id.lay_nsv);
    }

    @Override
    protected void initData() {
        super.initData();
        mWebView.loadDetailDataAsync(mBean.getBody(), null);
        mShareDialog = new ShareDialog(getActivity(),-1);
        mDialog = DialogHelper.getProgressDialog(mContext);
        mDialog.setMessage("请稍候...");
        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });
    }

    public void share() {

    }

    @Override
    public void run() {
        if (mDialog == null)
            return;
        mDialog.dismiss();
        mBitmap = create(mViewScroller.getChildAt(0));
        mShareDialog.bitmap(mBitmap);
        mShareDialog.show();
    }

    private static Bitmap create(View v) {
        try {
            int w = v.getWidth();
            int h = v.getHeight();
            Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
            Canvas c = new Canvas(bmp);
            c.drawColor(Color.WHITE);
            v.layout(0, 0, w, h);
            v.draw(c);
            return bmp;
        } catch (OutOfMemoryError error) {
            error.printStackTrace();
            return null;
        }
    }
}
