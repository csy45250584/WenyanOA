package com.haokuo.wenyanoa.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.haokuo.midtitlebar.MidTitleBar;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.adapter.StaffDestinationAdapter;
import com.haokuo.wenyanoa.bean.DishesBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zjf on 2018-08-09.
 */

public class StaffDestinationActivity extends BaseActivity {
    @BindView(R.id.mid_title_bar)
    MidTitleBar mMidTitleBar;
    @BindView(R.id.rv_staff_destination)
    RecyclerView mRvStaffDestination;
    @BindView(R.id.srl_staff_destination)
    SmartRefreshLayout mSrlStaffDestination;
    @BindView(R.id.et_name)
    EditText mEtName;

    @Override
    protected int initContentLayout() {
        return R.layout.activity_staff_destination;
    }

    @Override
    protected void initData() {
        setSupportActionBar(mMidTitleBar);
        mMidTitleBar.addBackArrow(this);
        mRvStaffDestination.setLayoutManager(new LinearLayoutManager(this));
        StaffDestinationAdapter staffDestinationAdapter = new StaffDestinationAdapter(R.layout.item_staff_destination);
        mRvStaffDestination.setAdapter(staffDestinationAdapter);
        ArrayList<DishesBean> dishesBeans = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            dishesBeans.add(new DishesBean());
        }
        staffDestinationAdapter.setNewData(dishesBeans);
    }

    @Override
    protected void initListener() {

    }


}
