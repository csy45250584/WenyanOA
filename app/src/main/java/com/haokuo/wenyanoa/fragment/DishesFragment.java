package com.haokuo.wenyanoa.fragment;

import android.content.res.Resources;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.adapter.DishesAdapter;
import com.haokuo.wenyanoa.bean.DishesBean;
import com.haokuo.wenyanoa.view.RecyclerViewDivider;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by zjf on 2018-08-08.
 */

public class DishesFragment extends BaseLazyLoadFragment {

    @BindView(R.id.rv_dishes)
    RecyclerView mRvDishes;
    private DishesAdapter mDishesAdapter;

    @Override
    protected int initContentLayout() {
        return R.layout.fragment_dishes;
    }

    @Override
    protected void initData() {
        Resources resources = getResources();
        mRvDishes.setLayoutManager(new LinearLayoutManager(mContext));
        int dividerPadding = (int) (resources.getDimension(R.dimen.dp_16) + 0.5f);
        int dividerHeight = (int) (resources.getDimension(R.dimen.dp_1) + 0.5f);
        mRvDishes.addItemDecoration(new RecyclerViewDivider(mContext, LinearLayoutManager.HORIZONTAL, dividerHeight, R.color.divider,
                dividerPadding, dividerPadding));
        mDishesAdapter = new DishesAdapter(R.layout.item_dishes);
        mRvDishes.setAdapter(mDishesAdapter);
        ArrayList<DishesBean> dishesBeans = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            dishesBeans.add(new DishesBean());
        }
        mDishesAdapter.setNewData(dishesBeans);
    }

    @Override
    protected void initListener() {
        mDishesAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_delete_dish:
                        mDishesAdapter.changeCount(position, false);
                        break;
                    case R.id.iv_add_dish:
                        mDishesAdapter.changeCount(position, true);
                        break;
                }
            }
        });
    }
}
