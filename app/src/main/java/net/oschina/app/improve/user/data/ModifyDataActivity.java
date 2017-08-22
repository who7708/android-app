package net.oschina.app.improve.user.data;

import android.content.Context;
import android.content.Intent;

import net.oschina.app.R;
import net.oschina.app.improve.base.activities.BaseBackActivity;
import net.oschina.app.improve.bean.User;

/**
 * 修改界面
 * Created by huanghaibin on 2017/8/17.
 */

public class ModifyDataActivity extends BaseBackActivity {

    public static void show(Context context, User info) {
        Intent intent = new Intent(context, ModifyDataActivity.class);
        intent.putExtra("user_info", info);
        context.startActivity(intent);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_modify_data;
    }
}
