package net.oschina.app.improve.main.synthesize.top;

import net.oschina.app.improve.base.BaseRecyclerFragment;
import net.oschina.app.improve.base.adapter.BaseRecyclerAdapter;
import net.oschina.app.improve.bean.Top;

/**
 * 头条界面
 * Created by huanghaibin on 2017/10/23.
 */

public class TopFragment extends BaseRecyclerFragment<TopContract.Presenter,Top> implements TopContract.View{

    public static TopFragment newInstance(){
        return new TopFragment();
    }

    @Override
    protected void onItemClick(Top top, int position) {

    }

    @Override
    protected BaseRecyclerAdapter<Top> getAdapter() {
        return new TopAdapter(mContext);
    }

}
