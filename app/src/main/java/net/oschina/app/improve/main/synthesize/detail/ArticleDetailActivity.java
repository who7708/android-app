package net.oschina.app.improve.main.synthesize.detail;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import net.oschina.app.AppContext;
import net.oschina.app.R;
import net.oschina.app.improve.account.AccountHelper;
import net.oschina.app.improve.account.activity.LoginActivity;
import net.oschina.app.improve.base.activities.BackActivity;
import net.oschina.app.improve.base.adapter.BaseRecyclerAdapter;
import net.oschina.app.improve.bean.Article;
import net.oschina.app.improve.bean.comment.Comment;
import net.oschina.app.improve.behavior.CommentBar;
import net.oschina.app.improve.main.synthesize.comment.ArticleCommentActivity;
import net.oschina.app.improve.user.activities.UserSelectFriendsActivity;
import net.oschina.app.improve.utils.DialogHelper;
import net.oschina.app.improve.widget.CommentShareView;
import net.oschina.app.improve.widget.SimplexToast;
import net.oschina.app.improve.widget.adapter.OnKeyArrivedListenerAdapterV2;
import net.oschina.app.ui.empty.EmptyLayout;
import net.oschina.app.util.HTMLUtil;
import net.oschina.app.util.TDevice;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 头条详情
 * Created by huanghaibin on 2017/10/23.
 */

public class ArticleDetailActivity extends BackActivity implements
        CommentView.OnCommentClickListener,
        ArticleDetailContract.EmptyView,
        EasyPermissions.PermissionCallbacks{
    private CommentBar mDelegation;
    private String mCommentHint;
    private Article mArticle;
    protected long mCommentId;
    protected long mCommentAuthorId;
    protected boolean mInputDoubleEmpty = false;
    private ArticleDetailPresenter mPresenter;
    protected EmptyLayout mEmptyLayout;
    protected CommentShareView mShareView;
    private AlertDialog mShareCommentDialog;
    protected Comment mComment;
    public static void show(Context context, Article article) {
        if (article == null)
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
        mArticle = (Article) getIntent().getSerializableExtra("article");
        ArticleFragment fragment = ArticleFragment.newInstance(mArticle);
        mPresenter = new ArticleDetailPresenter(fragment, this, mArticle);
        addFragment(R.id.fl_content, fragment);

        LinearLayout layComment = (LinearLayout) findViewById(R.id.ll_comment);
        if (TextUtils.isEmpty(mCommentHint))
            mCommentHint = getString(R.string.pub_comment_hint);
        mDelegation = CommentBar.delegation(this, layComment);
        mDelegation.setCommentHint(mCommentHint);
        mDelegation.getBottomSheet().getEditText().setHint(mCommentHint);
        //mDelegation.setFavDrawable(mBean.isFavorite() ? R.drawable.ic_faved : R.drawable.ic_fav);
        mShareView = (CommentShareView) findViewById(R.id.shareView);
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
        mDelegation.hideFav();

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


        mDelegation.setCommentCountListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArticleCommentActivity.show(ArticleDetailActivity.this, mArticle);
            }
        });

        mDelegation.getBottomSheet().getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    handleKeyDel();
                }
                return false;
            }
        });
        mDelegation.getBottomSheet().hideSyncAction();
        mDelegation.getBottomSheet().setCommitListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingDialog("正在提交评论...");
                if (mDelegation == null) return;
                mDelegation.getBottomSheet().dismiss();
                mDelegation.setCommitButtonEnable(false);
                mPresenter.putArticleComment(mDelegation.getBottomSheet().getCommentText(),
                        mCommentId,
                        mCommentAuthorId
                );
            }
        });
        mDelegation.getBottomSheet().getEditText().setOnKeyArrivedListener(new OnKeyArrivedListenerAdapterV2(this));

        if (mShareView != null) {
            mShareCommentDialog = DialogHelper.getRecyclerViewDialog(this, new BaseRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position, long itemId) {
                    switch (position) {
                        case 0:
                            TDevice.copyTextToBoard(HTMLUtil.delHTMLTag(mComment.getContent()));
                            break;
                        case 1:
                            if (!AccountHelper.isLogin()) {
                                LoginActivity.show(ArticleDetailActivity.this, 1);
                                return;
                            }
                            if (mComment.getAuthor() == null || mComment.getAuthor().getId() == 0) {
                                SimplexToast.show(ArticleDetailActivity.this, "该用户不存在");
                                return;
                            }
                            mCommentId = mComment.getId();
                            mCommentAuthorId = mComment.getAuthor().getId();
                            mDelegation.getCommentText().setHint(String.format("%s %s", getResources().getString(R.string.reply_hint), mComment.getAuthor().getName()));
                            mDelegation.getBottomSheet().show(String.format("%s %s", getResources().getString(R.string.reply_hint), mComment.getAuthor().getName()));
                            break;
                        case 2:
                            mShareView.init(mArticle.getTitle(), mComment);
                            saveToFileByPermission();
                            break;
                    }
                    mShareCommentDialog.dismiss();
                }
            }).create();
        }
    }

    protected void handleKeyDel() {
        if (mCommentId != 0) {
            if (TextUtils.isEmpty(mDelegation.getBottomSheet().getCommentText())) {
                if (mInputDoubleEmpty) {
                    mCommentId = 0;
                    mCommentAuthorId = 0;
                    mDelegation.setCommentHint(mCommentHint);
                    mDelegation.getBottomSheet().getEditText().setHint(mCommentHint);
                } else {
                    mInputDoubleEmpty = true;
                }
            } else {
                mInputDoubleEmpty = false;
            }
        }
    }

    @Override
    public void onClick(View view, Comment comment) {
        this.mComment = comment;
        if (mShareCommentDialog != null) {
            mShareCommentDialog.show();
        }
    }

    @Override
    public void showCommentSuccess(Comment comment) {
        if (isDestroy())
            return;
        if (mDelegation == null)
            return;
//        if (mDelegation.getBottomSheet().isSyncToTweet()) {
////            TweetPublishService.startActionPublish(this,
////                    mDelegation.getBottomSheet().getCommentText(), null,
////                    About.buildShare(mBean.getId(), mBean.getType()));
//        }
        mCommentId = 0;
        mCommentAuthorId = 0;
        mDelegation.getBottomSheet().dismiss();
        mDelegation.setCommitButtonEnable(true);
        AppContext.showToastShort(R.string.pub_comment_success);
        mDelegation.getCommentText().setHint(mCommentHint);
        mDelegation.getBottomSheet().getEditText().setText("");
        mDelegation.getBottomSheet().getEditText().setHint(mCommentHint);
        mDelegation.getBottomSheet().dismiss();
        dismissLoadingDialog();
        SimplexToast.show(this, "评论成功");
    }

    @Override
    public void showCommentError(String message) {
        if (isDestroy())
            return;
        dismissLoadingDialog();
        SimplexToast.show(this, "评论失败");
    }

    private static final int PERMISSION_ID = 0x0001;

    @SuppressWarnings("unused")
    @AfterPermissionGranted(PERMISSION_ID)
    public void saveToFileByPermission() {
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, permissions)) {
            mShareView.share();
        } else {
            EasyPermissions.requestPermissions(this, "请授予文件读写权限", PERMISSION_ID, permissions);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        DialogHelper.getConfirmDialog(this, "", "没有权限, 你需要去设置中开启读取手机存储权限.", "去设置", "取消", false, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_APPLICATION_SETTINGS));
                //finish();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //finish();
            }
        }).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

}
