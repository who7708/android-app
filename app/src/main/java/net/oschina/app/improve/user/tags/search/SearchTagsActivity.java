package net.oschina.app.improve.user.tags.search;

import android.content.Context;
import android.content.Intent;

import net.oschina.app.R;
import net.oschina.app.improve.account.AccountHelper;
import net.oschina.app.improve.base.activities.BackActivity;

/**
 * 用户搜索标签界面
 * Created by haibin on 2018/05/28.
 */
public class SearchTagsActivity extends BackActivity{

    public static void show(Context context) {
        if (!AccountHelper.isLogin()) {
            return;
        }
        context.startActivity(new Intent(context, SearchTagsActivity.class));
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_user_tags_search;
    }
}
