package net.oschina.app.improve.main.synthesize.detail;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import net.oschina.app.R;
import net.oschina.app.improve.account.AccountHelper;
import net.oschina.app.improve.account.activity.LoginActivity;
import net.oschina.app.improve.base.activities.BackActivity;
import net.oschina.app.improve.bean.Article;
import net.oschina.app.improve.behavior.CommentBar;
import net.oschina.app.improve.user.activities.UserSelectFriendsActivity;
import net.oschina.app.improve.widget.adapter.OnKeyArrivedListenerAdapterV2;
import net.oschina.app.ui.empty.EmptyLayout;

/**
 * 头条详情
 * Created by huanghaibin on 2017/10/23.
 */

public class ArticleDetailActivity extends BackActivity {
    private CommentBar mDelegation;
    private String mCommentHint;
    private Article mArticle;
    protected long mCommentId;
    protected long mCommentAuthorId;
    protected boolean mInputDoubleEmpty = false;

    protected EmptyLayout mEmptyLayout;

    public static void show(Context context, Article article) {
        if(article == null)
            return;
        Intent intent = new Intent(context, ArticleDetailActivity.class);
        intent.putExtra("article", article);
        context.startActivity(intent);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_article_detail;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void initWidget() {
        super.initWidget();
        setStatusBarDarkMode();
        setDarkToolBar();
        mArticle= (Article)getIntent().getSerializableExtra("article");
        addFragment(R.id.fl_content, ArticleFragment.newInstance(mArticle));

        LinearLayout layComment = (LinearLayout) findViewById(R.id.ll_comment);
        if (TextUtils.isEmpty(mCommentHint))
            mCommentHint = getString(R.string.pub_comment_hint);
        mDelegation = CommentBar.delegation(this, layComment);
        mDelegation.setCommentHint(mCommentHint);
        mDelegation.getBottomSheet().getEditText().setHint(mCommentHint);
        //mDelegation.setFavDrawable(mBean.isFavorite() ? R.drawable.ic_faved : R.drawable.ic_fav);

        mEmptyLayout = (EmptyLayout) findViewById(R.id.lay_error);
        mEmptyLayout.setOnLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEmptyLayout.getErrorState() != EmptyLayout.NETWORK_LOADING) {
                    mEmptyLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
                    //mPresenter.getDetail();
                }
            }
        });

        mEmptyLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
        mDelegation.setFavListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AccountHelper.isLogin()) {
                    LoginActivity.show(ArticleDetailActivity.this);
                    return;
                }
                //mPresenter.favReverse();
            }
        });

        mDelegation.getBottomSheet().setMentionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((AccountHelper.isLogin())) {
                    UserSelectFriendsActivity.show(ArticleDetailActivity.this, mDelegation.getBottomSheet().getEditText());
                } else {
                    LoginActivity.show(ArticleDetailActivity.this, 1);
                }
            }
        });

//        mDelegation.setCommentCountListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //CommentsActivity.show(ArticleDetailActivity.this, mBean.getId(), mBean.getType(), OSChinaApi.COMMENT_NEW_ORDER, mBean.getTitle());
//            }
//        });

        mDelegation.getBottomSheet().getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    handleKeyDel();
                }
                return false;
            }
        });
        mDelegation.getBottomSheet().setCommitListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // showDialog("正在提交评论...");
                if (mDelegation == null) return;
                mDelegation.getBottomSheet().dismiss();
                mDelegation.setCommitButtonEnable(false);
//                mPresenter.addComment(mBean.getId(),
//                        mBean.getType(),
//                        mDelegation.getBottomSheet().getCommentText(),
//                        0,
//                        mCommentId,
//                        mCommentAuthorId);
            }
        });
        mDelegation.getBottomSheet().getEditText().setOnKeyArrivedListener(new OnKeyArrivedListenerAdapterV2(this));
    }

    protected void handleKeyDel() {
//        if (mCommentId != mArticle.getId()) {
//            if (TextUtils.isEmpty(mDelegation.getBottomSheet().getCommentText())) {
//                if (mInputDoubleEmpty) {
//                    mCommentId = mBean.getId();
//                    mCommentAuthorId = 0;
//                    mDelegation.setCommentHint(mCommentHint);
//                    mDelegation.getBottomSheet().getEditText().setHint(mCommentHint);
//                } else {
//                    mInputDoubleEmpty = true;
//                }
//            } else {
//                mInputDoubleEmpty = false;
//            }
//        }
    }

}
