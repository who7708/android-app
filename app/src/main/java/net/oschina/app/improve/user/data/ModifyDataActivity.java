package net.oschina.app.improve.user.data;

import android.app.Activity;
import android.content.Intent;

import net.oschina.app.R;
import net.oschina.app.improve.base.activities.BaseBackActivity;

/**
 * 修改界面
 * Created by huanghaibin on 2017/8/17.
 */

public class ModifyDataActivity extends BaseBackActivity {

    public static void show(Activity activity, int code) {
        Intent intent = new Intent(activity, UserDataActivity.class);
        activity.startActivityForResult(intent, code);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_modify_data;
    }
}
