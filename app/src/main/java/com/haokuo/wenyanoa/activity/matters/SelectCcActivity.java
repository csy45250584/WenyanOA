package com.haokuo.wenyanoa.activity.matters;

import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.haokuo.midtitlebar.MidTitleBar;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.activity.BaseActivity;
import com.haokuo.wenyanoa.bean.StaffBean;
import com.haokuo.wenyanoa.fragment.ContactsFragment;
import com.haokuo.wenyanoa.util.utilscode.FragmentUtils;
import com.haokuo.wenyanoa.util.utilscode.KeyboardUtils;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import butterknife.BindView;

/**
 * Created by zjf on 2018/8/23.
 */
public class SelectCcActivity extends BaseActivity {
    @BindView(R.id.mid_title_bar)
    MidTitleBar mMidTitleBar;
    @BindView(R.id.search_view)
    MaterialSearchView mSearchView;
    @BindView(R.id.fl_fragment_container)
    FrameLayout mFlFragmentContainer;
    private ContactsFragment mContactsFragment;

    @Override
    protected int initContentLayout() {
        return R.layout.activity_select_cc;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                KeyboardUtils.hideSoftInput(this, v);
                return super.dispatchTouchEvent(ev); //隐藏键盘时，其他控件不响应点击事件==》注释则不拦截点击事件
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public static boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            v.getLocationInWindow(leftTop);
            int left = leftTop[0], top = leftTop[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void initData() {
        setSupportActionBar(mMidTitleBar);
        mMidTitleBar.addBackArrow(this);
        mContactsFragment = new ContactsFragment();
        mContactsFragment.setIsSelectCc(true);
        FragmentUtils.addFragment(getSupportFragmentManager(),mContactsFragment,R.id.fl_fragment_container);
    }

    @Override
    public void onBackPressed() {
        if (mSearchView.isSearchOpen()) {
            mSearchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void initListener() {
        mSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mContactsFragment.onQueryTextChange(newText);
                return true;
            }
        });
    }

    public void ccSelected(StaffBean cc) {
        setResult(RESULT_OK);
    }
}
