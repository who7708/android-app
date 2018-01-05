package net.oschina.app.improve.search.software;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import net.oschina.app.improve.base.adapter.BaseRecyclerAdapter;
import net.oschina.app.improve.bean.Article;

/**
 * 只搜索软件
 * Created by huanghaibin on 2018/1/5.
 */

class SoftwareAdapter extends BaseRecyclerAdapter<Article> {
     SoftwareAdapter(Context context) {
        super(context, ONLY_FOOTER);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return null;
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, Article item, int position) {

    }
}
