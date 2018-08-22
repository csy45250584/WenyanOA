package com.haokuo.wenyanoa.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.bean.ContactResultBean;

/**
 * Created by Naix on 2017/8/7 17:29.
 */
public class CcAdapter extends BaseQuickAdapter<ContactResultBean.ContactBean, BaseViewHolder> {

    public CcAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ContactResultBean.ContactBean item) {
        //设置通讯录内容
        helper.setText(R.id.tv_contact_name, item.getRealname());
        helper.setText(R.id.tv_contact_tel, item.getTelphone());
        ImageView ivContactAvatar = helper.getView(R.id.iv_contact_avatar);
    }
}
