package net.oschina.app.improve.search.v2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import net.oschina.app.improve.base.adapter.BaseRecyclerAdapter;
import net.oschina.app.improve.bean.Article;

/**
 * 搜索适配器
 * Created by huanghaibin on 2018/1/5.
 */

public class SearchAdapter extends BaseRecyclerAdapter<Article> {
    public SearchAdapter(Context context) {
        super(context, BOTH_HEADER_FOOTER);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return null;
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, Article item, int position) {

    }
}
