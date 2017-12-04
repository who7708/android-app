package net.oschina.app.improve.main.synthesize.read;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import net.oschina.app.improve.base.adapter.BaseRecyclerAdapter;
import net.oschina.app.improve.bean.Article;

/**
 * 文章阅读记录
 * Created by huanghaibin on 2017/12/4.
 */

public class ReadArticleAdapter extends BaseRecyclerAdapter<Article> {
    public ReadArticleAdapter(Context context, int mode) {
        super(context, mode);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return null;
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, Article item, int position) {

    }

    private static class ArticleHolder extends RecyclerView.ViewHolder {
        private ArticleHolder(View itemView) {
            super(itemView);
        }
    }
}
