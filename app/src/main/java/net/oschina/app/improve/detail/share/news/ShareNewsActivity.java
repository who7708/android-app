package net.oschina.app.improve.detail.share.news;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import net.oschina.app.R;
import net.oschina.app.improve.bean.SubBean;
import net.oschina.app.improve.bean.simple.Author;
import net.oschina.app.improve.detail.share.ShareActivity;
import net.oschina.app.improve.detail.share.ShareFragment;
import net.oschina.app.improve.widget.PortraitView;
import net.oschina.app.util.StringUtils;

import butterknife.BindView;

/**
 * 资讯长图分享
 * Created by huanghaibin on 2017/9/25.
 */

public class ShareNewsActivity extends ShareActivity {

    @BindView(R.id.tv_title)
    TextView mTextTitle;

    @BindView(R.id.tv_pub_date)
    TextView mTextPubDate;

    @BindView(R.id.tv_author)
    TextView mTextAuthor;

    @BindView(R.id.iv_avatar)
    PortraitView mPortraitView;


    public static void show(Context context, SubBean bean) {
        Intent intent = new Intent(context, ShareNewsActivity.class);
        intent.putExtra("bean", bean);
        context.startActivity(intent);
    }


    @Override
    protected int getContentView() {
        return R.layout.activity_news_share;
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        super.initData();
        mTextTitle.setText(mBean.getTitle());
        mTextPubDate.setText("发布于 " + StringUtils.formatYearMonthDay(mBean.getPubDate()));
        Author author = mBean.getAuthor();
        if (author != null) {
            mTextAuthor.setText(author.getName());
        }
        mPortraitView.setup(mBean.getAuthor());
    }

    @Override
    protected ShareFragment getShareFragment() {
        return ShareNewsFragment.newInstance(mBean);
    }
}
