package net.oschina.app.improve.main.synthesize.read;

import android.content.Context;
import android.content.Intent;

import net.oschina.app.improve.base.activities.BackActivity;

/**
 * 阅读记录
 * Created by huanghaibin on 2017/12/4.
 */

public class ReadHistoryActivity extends BackActivity {

    public static void show(Context context) {
        context.startActivity(new Intent(context, ReadHistoryActivity.class));
    }

    @Override
    protected int getContentView() {
        return 0;
    }
}
