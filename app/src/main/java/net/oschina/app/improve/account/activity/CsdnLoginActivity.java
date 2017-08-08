package net.oschina.app.improve.account.activity;

import android.app.Activity;
import android.content.Intent;

import net.oschina.app.R;
import net.oschina.app.improve.base.activities.BaseBackActivity;

/**
 * CSDN第三方登陆界面，非Oauth
 * Created by huanghaibin on 2017/8/8.
 */

public class CsdnLoginActivity extends BaseBackActivity {

    public static void show(Activity activity) {
        Intent intent = new Intent(activity, CsdnLoginActivity.class);
        activity.startActivityForResult(intent, 1);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_csdn_login;
    }
}
