package com.haokuo.wenyanoa.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.bean.StaffDestinationResultBean;
import com.haokuo.wenyanoa.util.ImageLoadUtil;

/**
 * Created by Naix on 2017/8/7 17:29.
 */
public class StaffDestinationAdapter extends BaseQuickAdapter<StaffDestinationResultBean.StaffDestinationBean, BaseViewHolder> {

    public StaffDestinationAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final StaffDestinationResultBean.StaffDestinationBean item) {
        ImageView ivStaffAvatar = helper.getView(R.id.iv_staff_avatar);
        ImageLoadUtil.getInstance().loadAvatar(mContext, item.getUserInfo().getHeadPhoto(), ivStaffAvatar, item.getSex());
        helper.setText(R.id.tv_staff_name, String.format("%s的人员去向", item.getName()));
        helper.setText(R.id.tv_original_dept, String.format("原属科室：%s", item.getSecition()));
        helper.setText(R.id.tv_target_dept, String.format("人员去向：%s", item.getLocation()));
        helper.setText(R.id.tv_start_time, String.format("开始时间：%s", item.getStartDate()));
        helper.setText(R.id.tv_end_time, String.format("结束时间：%s", item.getEndDate()));
        helper.setText(R.id.tv_end_time, String.format("结束时间：%s", item.getEndDate()));
        helper.setText(R.id.tv_create_date, item.getCreateDate().substring(0, 10));
        helper.addOnClickListener(R.id.btn_modify);
        helper.addOnClickListener(R.id.btn_delete);
    }
}
