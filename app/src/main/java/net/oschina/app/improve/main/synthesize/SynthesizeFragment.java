package net.oschina.app.improve.main.synthesize;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import net.oschina.app.R;
import net.oschina.app.improve.base.fragments.BaseGeneralListFragment;
import net.oschina.app.improve.base.fragments.BaseGeneralRecyclerFragment;
import net.oschina.app.improve.base.fragments.BasePagerFragment;
import net.oschina.app.improve.bean.SubTab;
import net.oschina.app.improve.main.subscription.SubFragment;
import net.oschina.app.improve.main.synthesize.top.TopFragment;
import net.oschina.app.improve.search.activities.SearchActivity;
import net.oschina.app.interf.OnTabReselectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 新版综合界面
 * Created by huanghaibin on 2017/10/23.
 */

public class SynthesizeFragment extends BasePagerFragment implements OnTabReselectListener, View.OnClickListener {

    public static SynthesizeFragment newInstance() {
        return new SynthesizeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_synthesize;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        setStatusBarPadding();
    }

    @OnClick({R.id.iv_search})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_search:
                SearchActivity.show(mContext);
                break;
        }
    }

    @Override
    public void onTabReselect() {
        if (mViewPager != null && mViewPager.getAdapter() != null) {
            BasePagerFragment.Adapter pagerAdapter = (BasePagerFragment.Adapter) mViewPager.getAdapter();
            Fragment fragment = pagerAdapter.getCurFragment();
            if (fragment != null) {
                if (fragment instanceof BaseGeneralListFragment)
                    ((BaseGeneralListFragment) fragment).onTabReselect();
                else if (fragment instanceof BaseGeneralRecyclerFragment)
                    ((BaseGeneralRecyclerFragment) fragment).onTabReselect();
            }
        }
    }

    @Override
    protected List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(TopFragment.newInstance());
        fragments.add(getSubFragment(6,
                "开源资讯",
                "https://www.oschina.net/action/apiv2/sub_list?token=d6112fa662bc4bf21084670a857fbd20",
                1,
                "d6112fa662bc4bf21084670a857fbd20"));
        fragments.add(getSubFragment(2,
                "技术问答",
                "https://www.oschina.net/action/apiv2/sub_list?token=98d04eb58a1d12b75d254deecbc83790",
                3,
                "98d04eb58a1d12b75d254deecbc83790"));
        fragments.add(getSubFragment(3,
                "推荐博客",
                "https://www.oschina.net/action/apiv2/sub_list?token=df985be3c5d5449f8dfb47e06e098ef9",
                4,
                "df985be3c5d5449f8dfb47e06e098ef9"));

        return fragments;
    }

    @Override
    protected String[] getTitles() {
        return getResources().getStringArray(R.array.synthesize_titles);
    }

    private SubFragment getSubFragment(int type, String title, String url, int subType, String token) {
        SubTab tab = new SubTab();
//        if(type == 6){
//            SubTab.Banner banner = tab.new Banner();
//            banner.setCatalog(1);
//            banner.setHref("https://www.oschina.net/action/apiv2/banner?catalog=1");
//            tab.setBanner(banner);
//        }
        tab.setType(type);
        tab.setFixed(false);
        tab.setName(title);
        tab.setNeedLogin(false);
        tab.setHref(url);
        tab.setSubtype(subType);
        tab.setToken(token);

        Bundle bundle = new Bundle();
        bundle.putSerializable("sub_tab", tab);
        return SubFragment.newInstance(tab);
    }
}
