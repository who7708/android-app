package net.oschina.app.improve.main.synthesize.detail;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import net.oschina.app.R;
import net.oschina.app.improve.base.BaseRecyclerFragment;
import net.oschina.app.improve.base.adapter.BaseRecyclerAdapter;
import net.oschina.app.improve.bean.Article;
import net.oschina.app.improve.main.synthesize.top.TopAdapter;

/**
 * 文章详情
 * Created by huanghaibin on 2017/10/27.
 */

public class ArticleFragment extends BaseRecyclerFragment<ArticleDetailContract.Presenter, Article> implements ArticleDetailContract.View {

    private Article mArtice;

    public static ArticleFragment newInstance(Article article) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("article", article);
        ArticleFragment fragment = new ArticleFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_article;
    }


    @Override
    protected void initBundle(Bundle bundle) {
        super.initBundle(bundle);
        mArtice = (Article) bundle.getSerializable("article");
    }

    @SuppressLint("InflateParams,CutPasteId")
    @Override
    protected void initData() {
        View view = mInflater.inflate(R.layout.layou_article_header, null);
        super.initData();
        mAdapter.setHeaderView(view);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
        TextView tv_pub_date = (TextView) view.findViewById(R.id.tv_pub_date);
        TextView tv_detail_abstract = (TextView) view.findViewById(R.id.tv_detail_abstract);
        tv_title.setText(mArtice.getTitle());
        tv_name.setText(mArtice.getAuthorName());
        tv_pub_date.setText(mArtice.getPubDate());
        tv_detail_abstract.setText(mArtice.getDesc());
    }

    @Override
    protected void onItemClick(Article article, int position) {
        WebActivity.show(mContext, article);
    }

    @Override
    protected BaseRecyclerAdapter<Article> getAdapter() {
        return new TopAdapter(mContext, BaseRecyclerAdapter.BOTH_HEADER_FOOTER);
    }
}
