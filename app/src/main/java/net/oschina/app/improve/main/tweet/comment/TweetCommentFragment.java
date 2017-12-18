package net.oschina.app.improve.main.tweet.comment;

import android.os.Bundle;

import net.oschina.app.improve.base.BaseRecyclerFragment;
import net.oschina.app.improve.base.adapter.BaseRecyclerAdapter;
import net.oschina.app.improve.bean.Tweet;
import net.oschina.app.improve.bean.simple.TweetComment;
import net.oschina.app.improve.tweet.adapter.TweetCommentAdapter;

/**
 * 动弹评论列表
 * Created by huanghaibin on 2017/12/18.
 */

public class TweetCommentFragment extends BaseRecyclerFragment<TweetCommentContract.Presenter, TweetComment>
        implements TweetCommentContract.View {
    private Tweet mTweet;

    public static TweetCommentFragment newInstance(Tweet tweet) {
        TweetCommentFragment fragment = new TweetCommentFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("tweet", tweet);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initBundle(Bundle bundle) {
        super.initBundle(bundle);
        mTweet = (Tweet) bundle.getSerializable("tweet");
    }

    @Override
    protected void initData() {
        new TweetCommentPresenter(this, mTweet);
        super.initData();
    }

    @Override
    protected void onItemClick(TweetComment tweetComment, int position) {

    }

    @Override
    protected BaseRecyclerAdapter<TweetComment> getAdapter() {
        return new TweetCommentAdapter(mContext);
    }
}
