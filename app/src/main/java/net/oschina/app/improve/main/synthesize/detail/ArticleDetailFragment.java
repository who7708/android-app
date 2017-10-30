package net.oschina.app.improve.main.synthesize.detail;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import net.oschina.app.R;
import net.oschina.app.improve.base.BaseRecyclerFragment;
import net.oschina.app.improve.base.adapter.BaseRecyclerAdapter;
import net.oschina.app.improve.bean.Article;
import net.oschina.app.improve.bean.News;
import net.oschina.app.improve.bean.comment.Comment;
import net.oschina.app.improve.main.synthesize.top.TopAdapter;
import net.oschina.app.improve.main.synthesize.web.ArticleWebActivity;
import net.oschina.app.improve.widget.PortraitView;
import net.oschina.app.util.UIHelper;

/**
 * 文章详情
 * Created by huanghaibin on 2017/10/27.
 */

public class ArticleDetailFragment extends BaseRecyclerFragment<ArticleDetailContract.Presenter, Article>
        implements ArticleDetailContract.View,
        View.OnClickListener {

    protected CommentView mCommentView;
    private Article mArticle;

    public static ArticleDetailFragment newInstance(Article article) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("article", article);
        ArticleDetailFragment fragment = new ArticleDetailFragment();
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
        mArticle = (Article) bundle.getSerializable("article");
    }

    @SuppressLint("InflateParams,CutPasteId")
    @Override
    protected void initData() {
        View view = mInflater.inflate(R.layout.layou_article_header, null);
        mAdapter.setHeaderView(view);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
        TextView tv_pub_date = (TextView) view.findViewById(R.id.tv_pub_date);
        TextView tv_detail_abstract = (TextView) view.findViewById(R.id.tv_detail_abstract);
        tv_title.setText(mArticle.getTitle());
        tv_name.setText(mArticle.getSourceName());
        tv_pub_date.setText(ArticleDetailPresenter.parsePubDate(mArticle.getPubDate()));
        tv_detail_abstract.setText(mArticle.getDesc());
        PortraitView portraitView = (PortraitView) view.findViewById(R.id.iv_avatar);
        getImgLoader().load(mArticle.getSourceImg()).asBitmap().into(portraitView);
        mCommentView = (CommentView) view.findViewById(R.id.commentView);
        mCommentView.setTitle("热门评论");
        mCommentView.init(mArticle, mArticle.getKey(), 1, (CommentView.OnCommentClickListener) mContext);
        view.findViewById(R.id.btn_read_all).setOnClickListener(this);
        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(false);
                if (mPresenter == null)
                    return;
                mPresenter.onRefreshing();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_read_all:
                ArticleWebActivity.show(mContext, mArticle);
                break;
        }
    }

    @Override
    protected void onItemClick(Article top, int position) {
        if (top.getType() == 0) {
            ArticleDetailActivity.show(mContext, top);
        } else {
            String key = top.getKey();
            if (TextUtils.isEmpty(key)) {
                ArticleDetailActivity.show(mContext, top);
                return;
            }
            String[] kv = key.split("_");
            if (kv.length != 3) {
                ArticleDetailActivity.show(mContext, top);
                return;
            }
            try {
                int type = Integer.parseInt(kv[1]);
                int id = Integer.parseInt(kv[2]);
                switch (type) {
                    case News.TYPE_SOFTWARE:
                        //SoftwareDetailActivity.show(mContext, sub.getId());
                        net.oschina.app.improve.detail.general.SoftwareDetailActivity.show(mContext, id);
                        break;
                    case News.TYPE_QUESTION:
                        //QuestionDetailActivity.show(mContext, sub.getId());
                        net.oschina.app.improve.detail.general.QuestionDetailActivity.show(mContext, id);
                        break;
                    case News.TYPE_BLOG:
                        //BlogDetailActivity.show(mContext, sub.getId());
                        net.oschina.app.improve.detail.general.BlogDetailActivity.show(mContext, id);
                        break;
                    case News.TYPE_TRANSLATE:
                        //TranslateDetailActivity.show(mContext, sub.getId());
                        net.oschina.app.improve.detail.general.NewsDetailActivity.show(mContext, id);
                        break;
                    case News.TYPE_EVENT:
                        //EventDetailActivity.show(mContext, sub.getId());
                        net.oschina.app.improve.detail.general.EventDetailActivity.show(mContext, id);
                        break;
                    case News.TYPE_NEWS:
                        //NewsDetailActivity.show(mContext, sub.getId());
                        net.oschina.app.improve.detail.general.NewsDetailActivity.show(mContext, id);
                        break;
                    default:
                        UIHelper.showUrlRedirect(mContext, top.getUrl());
                        break;
                }

            } catch (Exception e) {
                e.printStackTrace();
                ArticleDetailActivity.show(mContext, top);
            }
        }
    }

    @Override
    public void showCommentSuccess(Comment comment) {

    }

    @Override
    public void showCommentError(String message) {

    }

    @Override
    protected BaseRecyclerAdapter<Article> getAdapter() {
        return new TopAdapter(mContext, BaseRecyclerAdapter.BOTH_HEADER_FOOTER);
    }
}
