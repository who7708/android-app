package net.oschina.app.improve.user.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.oschina.app.R;
import net.oschina.app.improve.base.adapter.BaseRecyclerAdapter;
import net.oschina.app.improve.user.bean.UserFansOrFollows;
import net.oschina.app.improve.widget.IdentityView;
import net.oschina.app.improve.widget.PortraitView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fei on 2016/8/24.
 * desc:
 */

public class UserFansOrFollowAdapter extends BaseRecyclerAdapter<UserFansOrFollows> {
    public UserFansOrFollowAdapter(Context context, int mode) {
        super(context, mode);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new UserFansViewHolder(mInflater.inflate(R.layout.activity_item_user_flow, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, UserFansOrFollows item, int position) {
        if (item == null) return;
        final UserFansViewHolder vh = (UserFansViewHolder) holder;
        vh.identityView.setup(item);
        vh.mCiIcon.setup(item);
        vh.mTvName.setText(item.getName());
        switch (item.getGender()) {
            case 0:
                vh.mIvSex.setVisibility(View.GONE);
                break;
            case 1:
                vh.mIvSex.setVisibility(View.VISIBLE);
                vh.mIvSex.setImageResource(R.mipmap.userinfo_icon_male);
                break;
            case 2:
                vh.mIvSex.setVisibility(View.VISIBLE);
                vh.mIvSex.setImageResource(R.mipmap.userinfo_icon_female);
                break;
            default:
                break;
        }
        vh.mTvDesc.setText(item.getDesc());
        UserFansOrFollows.More more = item.getMore();
        if (more == null) return;
        vh.mTvCity.setText(more.getCity());
        vh.mTvExp.setText(more.getExpertise());

    }

    @Override
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        super.setOnItemClickListener(onItemClickListener);
    }

    /**
     *
     */
    class UserFansViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.identityView)
        IdentityView identityView;
        @BindView(R.id.iv_user_flow_icon)
        PortraitView mCiIcon;
        @BindView(R.id.tv_user_flow_name)
        TextView mTvName;
        @BindView(R.id.iv_user_flow_sex)
        ImageView mIvSex;
        @BindView(R.id.tv_user_flow_city)
        TextView mTvCity;
        @BindView(R.id.tv_user_flow_desc)
        TextView mTvDesc;
        @BindView(R.id.tv_user_flow_expertise)
        TextView mTvExp;


        UserFansViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
