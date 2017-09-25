package net.oschina.app.improve.detail.share;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;

import net.oschina.app.R;
import net.oschina.app.improve.base.activities.BackActivity;
import net.oschina.app.improve.bean.News;
import net.oschina.app.improve.bean.SubBean;
import net.oschina.app.improve.detail.share.blog.ShareBlogActivity;
import net.oschina.app.improve.detail.share.news.ShareNewsActivity;
import net.oschina.app.improve.utils.DialogHelper;
import net.oschina.app.improve.widget.OWebView;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 文章详情分享界面
 * Created by huanghaibin on 2017/9/25.
 */

public abstract class ShareActivity extends BackActivity implements EasyPermissions.PermissionCallbacks {

    protected ShareFragment mFragment;
    protected OWebView mWebView;
    protected SubBean mBean;

    public static void show(Context context, SubBean bean) {
        if (bean == null)
            return;
        switch (bean.getType()) {
            case News.TYPE_BLOG:
                ShareBlogActivity.show(context, bean);
                break;
            case News.TYPE_NEWS:
                ShareNewsActivity.show(context, bean);
                break;
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_share;
    }

    @Override
    protected void initData() {
        super.initData();
        mWebView = (OWebView) findViewById(R.id.webView);
        mBean = (SubBean) getIntent().getSerializableExtra("bean");
        mWebView.loadDetailDataAsync(mBean.getBody(), null);
        mFragment = getShareFragment();
        addFragment(R.id.fl_content,mFragment);
    }

    private static final int PERMISSION_ID = 0x0001;

    @SuppressWarnings("unused")
    @AfterPermissionGranted(PERMISSION_ID)
    public void saveToFileByPermission() {
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, permissions)) {
            mFragment.share();
        } else {
            EasyPermissions.requestPermissions(this, "请授予文件读写权限", PERMISSION_ID, permissions);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        DialogHelper.getConfirmDialog(this, "", "没有权限, 你需要去设置中开启读取手机存储权限.", "去设置", "取消", false, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_APPLICATION_SETTINGS));
                //finish();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //finish();
            }
        }).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    protected abstract ShareFragment getShareFragment();
}
