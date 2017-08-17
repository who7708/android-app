package net.oschina.app.improve.user.data;

import android.content.Context;
import android.content.Intent;

import net.oschina.app.R;
import net.oschina.app.improve.base.activities.BaseBackActivity;

/**
 * 用户资料+修改页面
 * Created by huanghaibin on 2017/8/14.
 */

public class UserDataActivity extends BaseBackActivity {

    public static void show(Context context) {
        context.startActivity(new Intent(context, UserDataActivity.class));
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_user_data;
    }
}
