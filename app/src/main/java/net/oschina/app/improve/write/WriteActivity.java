package net.oschina.app.improve.write;

import android.content.Context;
import android.content.Intent;

import net.oschina.app.R;
import net.oschina.app.improve.base.activities.BaseBackActivity;

/**
 * 写博客界面
 * Created by huanghaibin on 2017/8/3.
 */

public class WriteActivity extends BaseBackActivity {
    public static void show(Context context) {
        context.startActivity(new Intent(context, WriteActivity.class));
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_write;
    }
}
