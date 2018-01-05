package net.oschina.app.improve.search.v2;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import net.oschina.app.R;
import net.oschina.app.improve.base.adapter.BaseRecyclerAdapter;
import net.oschina.app.improve.bean.Article;
import net.oschina.app.improve.media.Util;
import net.oschina.app.improve.search.software.SearchSoftwareActivity;

import java.util.List;

/**
 * 搜索头部
 * Created by huanghaibin on 2018/1/5.
 */

public class SearchHeaderView extends LinearLayout {

    private Adapter mAdapter;
    private RadioGroup mRadioGroup;
    private TextView mTextSoftwareCount;
    private LinearLayout mLinearSoftware;
    private RecyclerView mRecyclerView;

    public SearchHeaderView(Context context) {
        this(context, null);
    }

    public SearchHeaderView(final Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_search_header, this, true);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerSoftware);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mAdapter = new Adapter(context);
        mRecyclerView.setAdapter(mAdapter);
        mLinearSoftware = (LinearLayout) findViewById(R.id.ll_software);
        mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        mTextSoftwareCount = (TextView) findViewById(R.id.tv_software_count);
    }

    void setSearchSoftwareListener(OnClickListener listener) {
        mTextSoftwareCount.setOnClickListener(listener);
    }

    void setData(List<Article> articles) {
        if (articles == null)
            return;
        if (articles.size() == 0) {
            mLinearSoftware.setVisibility(GONE);
            mRecyclerView.setVisibility(GONE);
        } else {
            mLinearSoftware.setVisibility(VISIBLE);
            mRecyclerView.setVisibility(VISIBLE);
        }
        mAdapter.resetItem(articles);
        mTextSoftwareCount.setText(String.format("查看其余%s款软件", articles.size()));
    }

    void setOrderChangeListener(RadioGroup.OnCheckedChangeListener listener) {
        mRadioGroup.setOnCheckedChangeListener(listener);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(Util.getScreenHeight(getContext()), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private static class Adapter extends BaseRecyclerAdapter<Article> {
        private RequestManager mLoader;

        public Adapter(Context context) {
            super(context, NEITHER);
            mLoader = Glide.with(context);
        }

        @Override
        protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
            return new SoftwareHolder(mInflater.inflate(R.layout.item_list_software, parent, false));
        }

        @Override
        protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, Article item, int position) {
            SoftwareHolder h = (SoftwareHolder) holder;
            mLoader.load(item.getSoftwareLogo())
                    .fitCenter()
                    .into(h.mImageLogo);
            h.mTextTitle.setText(item.getTitle());
            h.mTextDesc.setText(item.getDesc());
        }

        private static class SoftwareHolder extends RecyclerView.ViewHolder {
            private ImageView mImageLogo;
            private TextView mTextTitle, mTextDesc;

            private SoftwareHolder(View itemView) {
                super(itemView);
                mImageLogo = (ImageView) itemView.findViewById(R.id.iv_logo);
                mTextTitle = (TextView) itemView.findViewById(R.id.tv_title);
                mTextDesc = (TextView) itemView.findViewById(R.id.tv_desc);
            }
        }
    }
}
