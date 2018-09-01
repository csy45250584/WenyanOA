package com.haokuo.wenyanoa.fragment;

import android.content.res.Resources;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.adapter.BasketAdapter;
import com.haokuo.wenyanoa.bean.BasketDishesBean;
import com.haokuo.wenyanoa.bean.GetFoodListResultBean;
import com.haokuo.wenyanoa.eventbus.DishClickEvent;
import com.haokuo.wenyanoa.view.RecyclerViewDivider;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.litepal.LitePal;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by zjf on 2018-08-08.
 */

public class BasketFragment extends BaseLazyLoadFragment {
    @BindView(R.id.rv_basket)
    RecyclerView mRvBasket;
    private ArrayList<BasketDishesBean> mBasketDishList;
    private BasketAdapter mBasketAdapter;

    @Override
    protected int initContentLayout() {
        return R.layout.fragment_basket;
    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
        Resources resources = getResources();
        mRvBasket.setLayoutManager(new LinearLayoutManager(mContext));
        int dividerPadding = (int) (resources.getDimension(R.dimen.dp_16) + 0.5f);
        int dividerHeight = (int) (resources.getDimension(R.dimen.dp_1) + 0.5f);
        mRvBasket.addItemDecoration(new RecyclerViewDivider(mContext, LinearLayoutManager.HORIZONTAL, dividerHeight, R.color.divider,
                dividerPadding, dividerPadding));
        mBasketAdapter = new BasketAdapter(R.layout.item_basket);
        mRvBasket.setAdapter(mBasketAdapter);
        mBasketDishList = new ArrayList<>();
        mBasketAdapter.setNewData(mBasketDishList);
    }

    @Subscribe
    public void onDishClickEvent(DishClickEvent event) {
        String selectedDate = event.getSelectedDate();
        GetFoodListResultBean.DishesBean dishesBean = event.getDishesBean();
        int weekday = event.getWeekday();
//        new BasketDishesBean(dishesBean.getFoodName(), dishesBean.getCoverImage(), dishesBean.getFoodPrice()*dishesBean.getCount(),
//                )
        //显示
        int count = dishesBean.getCount();
        if (count == 0) {
            int deleteCount = LitePal.deleteAll(BasketDishesBean.class, "dishId = ? AND diningDate = ?", String.valueOf(dishesBean.getId()), selectedDate);
            if (deleteCount > 0) {
                mBasketAdapter.removeItem(selectedDate, dishesBean.getId());
            }
        } else {

//            LitePal.updateAll(BasketDishesBean.class,,"dishId = ? AND diningDate = ?", String.valueOf(dishesBean.getId()), selectedDate)
//            mBasketAdapter.updateItem(selectedDate, dishesBean.getId());
        }
        //更新数据库

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initListener() {

    }
}
