package com.haokuo.wenyanoa.fragment;

import android.content.res.Resources;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.adapter.BasketAdapter;
import com.haokuo.wenyanoa.bean.DishesBean;
import com.haokuo.wenyanoa.view.RecyclerViewDivider;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by zjf on 2018-08-08.
 */

public class BasketFragment extends BaseLazyLoadFragment {
    @BindView(R.id.rv_basket)
    RecyclerView mRvBasket;

    @Override
    protected int initContentLayout() {
        return R.layout.fragment_basket;
    }

    @Override
    protected void initData() {
        Resources resources = getResources();
        mRvBasket.setLayoutManager(new LinearLayoutManager(mContext));
        int dividerPadding = (int) (resources.getDimension(R.dimen.dp_16) + 0.5f);
        int dividerHeight = (int) (resources.getDimension(R.dimen.dp_1) + 0.5f);
        mRvBasket.addItemDecoration(new RecyclerViewDivider(mContext, LinearLayoutManager.HORIZONTAL, dividerHeight, R.color.divider,
                dividerPadding, dividerPadding));
        //        mRvBasket.setItemAnimator(new SlideInRightAnimator());
        BasketAdapter basketAdapter = new BasketAdapter(R.layout.item_basket);
        mRvBasket.setAdapter(basketAdapter);
        ArrayList<DishesBean> dishesBeans = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            dishesBeans.add(new DishesBean());
        }
        basketAdapter.setNewData(dishesBeans);
    }

    @Override
    protected void initListener() {

    }
}
