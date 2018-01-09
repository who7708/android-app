package net.oschina.app.improve.search.software;

import android.content.Context;
import android.content.Intent;

import net.oschina.app.R;
import net.oschina.app.improve.base.activities.BackActivity;

/**
 * 只搜索软件
 * Created by huanghaibin on 2018/1/5.
 */

public class SearchSoftwareActivity extends BackActivity {

    public static void show(Context context, String keyword) {
        Intent intent = new Intent(context, SearchSoftwareActivity.class);
        intent.putExtra("keyword", keyword);
        context.startActivity(intent);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_search_software;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setStatusBarDarkMode();
        setDarkToolBar();
    }
}
