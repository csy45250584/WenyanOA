package com.haokuo.wenyanoa.activity.approval;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.activity.BaseActivity;
import com.haokuo.wenyanoa.network.NetworkCallback;

import okhttp3.Call;

/**
 * Created by zjf on 2018/8/30.
 */
public abstract class BaseApprovalDetailActivity extends BaseActivity {
    protected NetworkCallback mHandleCallback = new NetworkCallback() {
        @Override
        public void onSuccess(Call call, String json) {
            setResult(RESULT_OK);
            loadSuccess("提交成功");
        }

        @Override
        public void onFailure(Call call, String message) {
            loadFailed("提交失败，" + message);
        }
    };

    protected void showRejectDialog() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_reject_approval, null);
        final EditText etPersonalInfo = inflate.findViewById(R.id.et_personal_info);
        etPersonalInfo.setHint("请输入拒绝理由");
        new AlertDialog.Builder(this)
                .setTitle("提示")
                .setView(inflate)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String rejectReason = etPersonalInfo.getEditableText().toString();
                        rejectApproval(rejectReason);
                    }
                })
                .setNegativeButton("取消", null)
                .create().show();
    }

    protected abstract void rejectApproval(String rejectReason);
}
