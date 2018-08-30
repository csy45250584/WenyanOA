package com.haokuo.wenyanoa.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.bean.ContactResultBean;
import com.haokuo.wenyanoa.util.ImageLoadUtil;

/**
 * Created by Naix on 2017/8/7 17:29.
 */
public class ContactsAdapter extends BaseQuickAdapter<ContactResultBean.ContactBean, BaseViewHolder> {

    public ContactsAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, ContactResultBean.ContactBean item) {
        helper.addOnClickListener(R.id.rv_contact_container);
        //设置首字母
        int position = helper.getLayoutPosition();
        if (position == 0 || item.getFirstLetter().compareTo(mData.get(position - 1).getFirstLetter()) > 0) {
            helper.setText(R.id.tv_contact_first_letter, item.getFirstLetter());
            helper.setGone(R.id.fl_contact_first_letter, true);
            helper.setGone(R.id.divider, false);
        } else {
            helper.setGone(R.id.divider, true);
            helper.setGone(R.id.fl_contact_first_letter, false);
        }
        //设置通讯录内容
        helper.setText(R.id.tv_contact_name, item.getRealname());
        helper.setText(R.id.tv_contact_tel, item.getTelphone());
        ImageView ivContactAvatar = helper.getView(R.id.iv_contact_avatar);
        ImageLoadUtil.getInstance().loadAvatar(mContext,item.getHeadPhoto(),ivContactAvatar,item.getSex());
    }
}
