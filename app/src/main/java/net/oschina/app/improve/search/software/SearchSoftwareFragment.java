package net.oschina.app.improve.search.software;

import net.oschina.app.improve.base.BaseRecyclerFragment;
import net.oschina.app.improve.base.adapter.BaseRecyclerAdapter;
import net.oschina.app.improve.bean.Article;

/**
 * 软件搜索
 * Created by huanghaibin on 2018/1/5.
 */

public class SearchSoftwareFragment extends BaseRecyclerFragment<SearchSoftwareContract.Presenter, Article>
        implements SearchSoftwareContract.View {

    static SearchSoftwareFragment newInstance() {
        return new SearchSoftwareFragment();
    }

    @Override
    protected void onItemClick(Article article, int position) {

    }

    @Override
    protected BaseRecyclerAdapter<Article> getAdapter() {
        return new SoftwareAdapter(mContext);
    }

}
