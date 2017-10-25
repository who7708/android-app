package net.oschina.app.improve.behavior;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.oschina.app.R;
import net.oschina.app.improve.account.AccountHelper;
import net.oschina.app.improve.account.activity.LoginActivity;
import net.oschina.app.improve.widget.BottomSheetBar;

/**
 * Created by haibin
 * on 2016/11/10.
 * Change by fei
 * on 2016/11/17
 * desc:详情页输入框
 */
@SuppressWarnings("all")
public class CommentBar {

    private Context mContext;
    private View mRootView;
    private FrameLayout mFrameLayout;
    private ViewGroup mParent;
    private ImageButton mFavView;
    private LinearLayout mLinearComment;
    private TextView mTextCommentCount;
    private TextView mCommentText;
    private BottomSheetBar mDelegation;
    private LinearLayout mCommentLayout;


    private CommentBar(Context context) {
        this.mContext = context;
    }

    public static CommentBar delegation(Context context, ViewGroup parent) {
        CommentBar bar = new CommentBar(context);
        bar.mRootView = LayoutInflater.from(context).inflate(R.layout.layout_comment_bar, parent, false);
        bar.mParent = parent;
        bar.mDelegation = BottomSheetBar.delegation(context);
        bar.mParent.addView(bar.mRootView);
        bar.initView();
        return bar;
    }

    private void initView() {
        //((CoordinatorLayout.LayoutParams) mRootView.getLayoutParams()).setBehavior(new FloatingAutoHideDownBehavior());
        mFavView = (ImageButton) mRootView.findViewById(R.id.ib_fav);
        mLinearComment = (LinearLayout) mRootView.findViewById(R.id.ll_comment_count);
        mCommentText = (TextView) mRootView.findViewById(R.id.tv_comment);
        mTextCommentCount = (TextView) mRootView.findViewById(R.id.tv_comment_count);
        mCommentLayout = (LinearLayout) mRootView.findViewById(R.id.ll_comment);
        mCommentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AccountHelper.isLogin()) {
                    mDelegation.show(mCommentText.getHint().toString());
                } else {
                    LoginActivity.show(mContext);
                }
            }
        });
    }

    /**
     * share 2 three sdk
     *
     * @param listener
     */
    public void setCommentCountListener(View.OnClickListener listener) {
        mLinearComment.setOnClickListener(listener);
    }

    /**
     * favorite the detail
     *
     * @param listener
     */
    public void setFavListener(View.OnClickListener listener) {
        mFavView.setOnClickListener(listener);
    }

    public void setCommentListener(View.OnClickListener listener) {
        mCommentText.setOnClickListener(listener);
    }

    public void setCommentHint(String text) {
        mCommentText.setHint(text);
    }

    public void setFavDrawable(int drawable) {
        mFavView.setImageResource(drawable);
    }

    public BottomSheetBar getBottomSheet() {
        return mDelegation;
    }

    public void setCommitButtonEnable(boolean enable) {
        mDelegation.getBtnCommit().setEnabled(enable);
    }

    public void hideCommentCount() {
        mLinearComment.setVisibility(View.GONE);
    }

    public void hideFav() {
        mFavView.setVisibility(View.GONE);
    }

    public TextView getCommentText() {
        return mCommentText;
    }

    public void setCommentCount(int count){
        if(mTextCommentCount!=null){
            mTextCommentCount.setText(String.valueOf(count));
        }
    }

    public void performClick() {
        mCommentLayout.performClick();
    }

}
