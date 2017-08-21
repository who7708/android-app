package net.oschina.app.improve.user.data;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import net.oschina.app.R;
import net.oschina.app.improve.base.activities.BaseBackActivity;
import net.oschina.app.improve.base.adapter.BaseRecyclerAdapter;
import net.oschina.app.improve.bean.User;
import net.oschina.app.improve.detail.db.DBManager;

import butterknife.Bind;

/**
 * 修改所在地区
 * Created by huanghaibin on 2017/8/21.
 */

public class ModifyAreaActivity extends BaseBackActivity {

    @Bind(R.id.rv_province)
    RecyclerView mRecyclerProvince;
    @Bind(R.id.rv_city)
    RecyclerView mRecyclerCity;
    private CityAdapter mAdapterCity;
    private ProvinceAdapter mAdapterProvince;

    public static void show(Context context, User info) {
        Intent intent = new Intent(context, ModifyAreaActivity.class);
        intent.putExtra("user_info", info);
        context.startActivity(intent);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_modify_area;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void initData() {
        super.initData();
        mRecyclerProvince.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerCity.setLayoutManager(new LinearLayoutManager(this));
        mAdapterCity = new CityAdapter(this);
        mAdapterProvince = new ProvinceAdapter(this);
        mRecyclerProvince.setAdapter(mAdapterProvince);
        mRecyclerCity.setAdapter(mAdapterCity);
        mAdapterProvince.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, long itemId) {
                mAdapterProvince.setSelectedPosition(position);
                mAdapterCity.resetItem(DBManager.getCountryManager()
                        .get(City.class, String.format("where province='%s'", mAdapterProvince.getItem(position).getName())));
                mAdapterCity.setSelectedPosition(0);
            }
        });
        mAdapterCity.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, long itemId) {
                mAdapterCity.setSelectedPosition(position);
            }
        });
        mAdapterProvince.addAll(DBManager.getCountryManager().get(Province.class));
        mAdapterCity.resetItem(DBManager.getCountryManager()
                .get(City.class, String.format("where province='%s'", mAdapterProvince.getItem(0).getName())));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_commit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_commit) {

        }
        return false;
    }
}
