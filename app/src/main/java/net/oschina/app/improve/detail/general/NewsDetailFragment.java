package net.oschina.app.improve.detail.general;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import net.oschina.app.R;
import net.oschina.app.api.remote.OSChinaApi;
import net.oschina.app.improve.bean.SubBean;
import net.oschina.app.improve.bean.simple.Author;
import net.oschina.app.improve.detail.v2.DetailFragment;
import net.oschina.app.improve.utils.ReadedIndexCacheManager;
import net.oschina.app.improve.widget.AutoScrollView;
import net.oschina.app.improve.widget.PortraitView;
import net.oschina.app.util.StringUtils;

/**
 * Created by haibin
 * on 2016/11/30.
 */

public class NewsDetailFragment extends DetailFragment {
    private TextView mTextTitle;

    private TextView mTextPubDate;

    private TextView mTextAuthor;

    private PortraitView mPortraitView;

    public static NewsDetailFragment newInstance() {
        return new NewsDetailFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news_detail_v2;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mTextTitle = (TextView)mHeaderView.findViewById(R.id.tv_title);
        mTextPubDate = (TextView)mHeaderView.findViewById(R.id.tv_pub_date);
        mTextAuthor = (TextView)mHeaderView.findViewById(R.id.tv_author);
        mPortraitView = (PortraitView)mHeaderView.findViewById(R.id.iv_avatar);
    }

    @Override
    protected void initData() {
        super.initData();
        CACHE_CATALOG = OSChinaApi.CATALOG_NEWS;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void showGetDetailSuccess(SubBean bean) {
        super.showGetDetailSuccess(bean);
        mTextTitle.setText(bean.getTitle());
        mTextPubDate.setText(StringUtils.formatYearMonthDay(bean.getPubDate()));
        Author author = mBean.getAuthor();
        if (author != null) {
            mTextAuthor.setText(author.getName());
        }
        mPortraitView.setup(author);
    }

    @Override
    protected int getCommentOrder() {
        return OSChinaApi.COMMENT_HOT_ORDER;
    }

    @Override
    public void onDestroy() {
        if (mBean != null && mBean.getId() > 0) {
            ReadedIndexCacheManager.saveIndex(getContext(), mBean.getId(), OSChinaApi.CATALOG_NEWS,
                    mViewScroller.getScrollY());
        }
        super.onDestroy();
    }

    @Override
    protected View getHeaderView() {
        return new NewsDetailHeaderView(mContext);
    }

    private static class NewsDetailHeaderView extends AutoScrollView{
        public NewsDetailHeaderView(Context context) {
            super(context);
            LayoutInflater.from(context).inflate(R.layout.layout_news_detail_header, this, true);
        }
    }
}
