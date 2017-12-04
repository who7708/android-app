package net.oschina.app.improve.main.synthesize.detail;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import net.oschina.app.OSCApplication;
import net.oschina.app.R;
import net.oschina.app.improve.base.BaseRecyclerFragment;
import net.oschina.app.improve.base.adapter.BaseRecyclerAdapter;
import net.oschina.app.improve.bean.Article;
import net.oschina.app.improve.bean.News;
import net.oschina.app.improve.bean.Tag;
import net.oschina.app.improve.bean.comment.Comment;
import net.oschina.app.improve.bean.simple.Author;
import net.oschina.app.improve.detail.general.BlogDetailActivity;
import net.oschina.app.improve.detail.general.EventDetailActivity;
import net.oschina.app.improve.detail.general.NewsDetailActivity;
import net.oschina.app.improve.detail.general.QuestionDetailActivity;
import net.oschina.app.improve.detail.general.SoftwareDetailActivity;
import net.oschina.app.improve.main.synthesize.DataFormat;
import net.oschina.app.improve.main.synthesize.article.ArticleAdapter;
import net.oschina.app.improve.main.synthesize.web.ArticleWebActivity;
import net.oschina.app.improve.media.ImageGalleryActivity;
import net.oschina.app.improve.widget.PortraitView;
import net.oschina.app.util.UIHelper;

import java.util.List;

/**
 * 文章详情
 * Created by huanghaibin on 2017/10/27.
 */

public class ArticleDetailFragment extends BaseRecyclerFragment<ArticleDetailContract.Presenter, Article>
        implements ArticleDetailContract.View,
        View.OnClickListener {
    private OSCApplication.ReadState mReadState;
    protected CommentView mCommentView;
    private Article mArticle;
    private View mHeaderView;
    private ProgressBar mLoadingBar;
    private TagFlowLayout mFlowLayout;

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
        mReadState = OSCApplication.getReadState("sub_list");
        mHeaderView = mInflater.inflate(R.layout.layout_article_header, null);
        mAdapter.setHeaderView(mHeaderView);
        ImageView imageView = (ImageView) mHeaderView.findViewById(R.id.iv_article);
        FrameLayout frameLayout = (FrameLayout) mHeaderView.findViewById(R.id.fl_img);
        mLoadingBar = (ProgressBar) mHeaderView.findViewById(R.id.pb_loading);
        if (mArticle.getImgs() != null && mArticle.getImgs().length != 0) {
            imageView.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.VISIBLE);
            getImgLoader().load(mArticle.getImgs()[0])
                    .centerCrop()
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            mLoadingBar.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            mLoadingBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageGalleryActivity.show(mContext, mArticle.getImgs()[0]);
                }
            });
        }
        mFlowLayout = (TagFlowLayout) mHeaderView.findViewById(R.id.flowLayout);
        TextView tv_title = (TextView) mHeaderView.findViewById(R.id.tv_title);
        TextView tv_name = (TextView) mHeaderView.findViewById(R.id.tv_name);
        TextView tv_pub_date = (TextView) mHeaderView.findViewById(R.id.tv_pub_date);
        TextView tv_origin = (TextView) mHeaderView.findViewById(R.id.tv_origin);
        TextView tv_detail_abstract = (TextView) mHeaderView.findViewById(R.id.tv_detail_abstract);
        tv_title.setText(mArticle.getTitle());
        tv_name.setText(TextUtils.isEmpty(mArticle.getAuthorName()) ? "匿名" : mArticle.getAuthorName());
        tv_pub_date.setText(DataFormat.parsePubDate(mArticle.getPubDate()));
        tv_detail_abstract.setText(mArticle.getDesc());
        PortraitView portraitView = (PortraitView) mHeaderView.findViewById(R.id.iv_avatar);
        tv_origin.setText(mArticle.getSource());
        Author author = new Author();
        author.setName(mArticle.getAuthorName());
        portraitView.setup(author);
        mCommentView = (CommentView) mHeaderView.findViewById(R.id.commentView);
        mCommentView.setTitle("热门评论");
        mCommentView.init(mArticle, mArticle.getKey(), 2, (CommentView.OnCommentClickListener) mContext);
        mHeaderView.findViewById(R.id.btn_read_all).setOnClickListener(this);
        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(false);
                if (mPresenter == null)
                    return;
                mPresenter.getArticleDetail();
                mPresenter.onRefreshing();
            }
        });
    }

    @Override
    public void onRefreshing() {
        super.onRefreshing();
        if (mCommentView != null)
            mCommentView.init(mArticle, mArticle.getKey(), 2, (CommentView.OnCommentClickListener) mContext);
    }

    @Override
    public void onScrollToBottom() {
        if (mPresenter != null) {
            mAdapter.setState(BaseRecyclerAdapter.STATE_LOADING, true);
            mPresenter.onLoadMore();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_read_all:
                ArticleWebActivity.show(mContext, mArticle);
                mPresenter.addClickCount();
                break;
        }
    }

    @Override
    protected void onItemClick(Article top, int position) {
        if (top.getType() == 0) {
            ArticleDetailActivity.show(mContext, top);
        } else {
            try {
                int type = top.getType();
                long id = top.getOscId();
                switch (type) {
                    case News.TYPE_SOFTWARE:
                        SoftwareDetailActivity.show(mContext, id);
                        break;
                    case News.TYPE_QUESTION:
                        QuestionDetailActivity.show(mContext, id);
                        break;
                    case News.TYPE_BLOG:
                        BlogDetailActivity.show(mContext, id);
                        break;
                    case News.TYPE_TRANSLATE:
                        NewsDetailActivity.show(mContext, id);
                        break;
                    case News.TYPE_EVENT:
                        EventDetailActivity.show(mContext, id);
                        break;
                    case News.TYPE_NEWS:
                        NewsDetailActivity.show(mContext, id);
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
        mReadState.put(top.getKey());
        mAdapter.updateItem(position);
    }

    @Override
    public void showCommentSuccess(Comment comment) {

    }

    @Override
    public void showCommentError(String message) {

    }

    @Override
    public void onRefreshSuccess(List<Article> data) {
        super.onRefreshSuccess(data);
        mRefreshLayout.setCanLoadMore(true);
    }

    @Override
    public void onLoadMoreSuccess(List<Article> data) {
        super.onLoadMoreSuccess(data);
        if (data != null && data.size() > 0) {
            mAdapter.setState(BaseRecyclerAdapter.STATE_LOADING, true);
        } else {
            mRefreshLayout.setCanLoadMore(false);
        }
    }

    @Override
    public void showGetDetailSuccess(final Article article) {
        if (mContext == null)
            return;
        mFlowLayout.removeAllViews();
        if (article.getiTags() == null || article.getiTags().length == 0) {
            mFlowLayout.setVisibility(View.GONE);
            return;
        }
        mFlowLayout.setVisibility(View.VISIBLE);
        mFlowLayout.setAdapter(new TagAdapter<Tag>(article.getiTags()) {
            @Override
            public View getView(FlowLayout parent, int position, final Tag tag) {
                TextView tvTag = (TextView) getActivity().getLayoutInflater().inflate(R.layout.tag_item, mFlowLayout, false);
                tvTag.setText(tag.getName());

                return tvTag;
            }
        });
        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Tag tag = article.getiTags()[position];
                if (tag.getOscId() != 0) {
                    SoftwareDetailActivity.show(mContext, tag.getOscId());
                }
                return true;
            }
        });
    }

    @Override
    public void onComplete() {
        super.onComplete();
        if (mContext == null)
            return;
        hideOrShowTitle(mAdapter.getItems().size() != 0);
    }

    private void hideOrShowTitle(boolean isShow) {
        if (isShow) {
            mHeaderView.findViewById(R.id.line1).setVisibility(View.VISIBLE);
            mHeaderView.findViewById(R.id.line2).setVisibility(View.VISIBLE);
            mHeaderView.findViewById(R.id.tv_recommend).setVisibility(View.VISIBLE);
        } else {
            mHeaderView.findViewById(R.id.line1).setVisibility(View.GONE);
            mHeaderView.findViewById(R.id.line2).setVisibility(View.GONE);
            mHeaderView.findViewById(R.id.tv_recommend).setVisibility(View.GONE);
            mAdapter.setState(BaseRecyclerAdapter.STATE_HIDE, true);
        }
    }

    @Override
    protected BaseRecyclerAdapter<Article> getAdapter() {
        return new ArticleAdapter(mContext, BaseRecyclerAdapter.BOTH_HEADER_FOOTER);
    }
}
