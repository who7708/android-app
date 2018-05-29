package net.oschina.app.improve.user.tags;

import android.content.Context;
import android.content.Intent;

import net.oschina.app.R;
import net.oschina.app.improve.account.AccountHelper;
import net.oschina.app.improve.base.activities.BackActivity;

/**
 * 用户标签界面
 * Created by haibin on 2018/05/22.
 */
public class UserTagsActivity extends BackActivity {

    public static void show(Context context) {
        if (!AccountHelper.isLogin()) {
            return;
        }
        context.startActivity(new Intent(context, UserTagsActivity.class));
    }


    @Override
    protected int getContentView() {
        return R.layout.activity_user_tags;
    }
}
