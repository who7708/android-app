package net.oschina.app.improve.main.synthesize.top;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import net.oschina.app.R;
import net.oschina.app.improve.base.adapter.BaseRecyclerAdapter;
import net.oschina.app.improve.bean.Top;

/**
 * 头条数据
 * Created by huanghaibin on 2017/10/23.
 */

public class TopAdapter extends BaseRecyclerAdapter<Top> {
    public TopAdapter(Context context) {
        super(context, ONLY_FOOTER);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new TopHolder(mInflater.inflate(R.layout.item_list_sub_news,parent,false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, Top item, int position) {

    }

    private static final class TopHolder extends RecyclerView.ViewHolder {
        public TopHolder(View itemView) {
            super(itemView);
        }
    }
}
