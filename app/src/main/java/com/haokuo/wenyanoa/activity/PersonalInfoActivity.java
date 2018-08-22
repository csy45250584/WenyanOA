package com.haokuo.wenyanoa.activity;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.haokuo.midtitlebar.MidTitleBar;
import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.bean.UpdateAvatarResultBean;
import com.haokuo.wenyanoa.bean.UserInfoDetailBean;
import com.haokuo.wenyanoa.network.HttpHelper;
import com.haokuo.wenyanoa.network.NetworkCallback;
import com.haokuo.wenyanoa.network.bean.UpdateAvatarParams;
import com.haokuo.wenyanoa.util.ImageBase64Util;
import com.haokuo.wenyanoa.util.ImageLoadUtil;
import com.haokuo.wenyanoa.util.OaSpUtil;
import com.haokuo.wenyanoa.util.utilscode.TimeUtils;
import com.haokuo.wenyanoa.view.SettingItemView;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.TResult;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;

/**
 * Created by zjf on 2018-08-10.
 */

public class PersonalInfoActivity extends BaseTakePhotoActivity {
    @BindView(R.id.mid_title_bar)
    MidTitleBar mMidTitleBar;
    @BindView(R.id.iv_avatar)
    CircleImageView mIvAvatar;
    @BindView(R.id.tv_change_avatar)
    TextView mTvChangeAvatar;
    @BindView(R.id.siv_user_name)
    SettingItemView mSivUserName;
    @BindView(R.id.siv_real_name)
    SettingItemView mSivRealName;
    @BindView(R.id.siv_sex)
    SettingItemView mSivSex;
    @BindView(R.id.siv_tel)
    SettingItemView mSivTel;
    @BindView(R.id.siv_short_tel)
    SettingItemView mSivShortTel;
    @BindView(R.id.siv_qq)
    SettingItemView mSivQq;
    @BindView(R.id.siv_wechat)
    SettingItemView mSivWechat;
    @BindView(R.id.siv_office)
    SettingItemView mSivOffice;
    @BindView(R.id.siv_office_phone)
    SettingItemView mSivOfficePhone;
    @BindView(R.id.siv_duties)
    SettingItemView mSivDuties;
    @BindView(R.id.siv_birth_day)
    SettingItemView mSivBirthDay;
    @BindView(R.id.ll_avatar)
    LinearLayout mLlAvatar;
    private UserInfoDetailBean mUserInfoDetail;

    @Override
    protected int initContentLayout() {
        return R.layout.activity_personal_info;
    }

    @Override
    protected void initData() {
        setSupportActionBar(mMidTitleBar);
        mMidTitleBar.addBackArrow(this);
    }

    @Override
    protected void initListener() {
        //从SP中加载用户信息
        mUserInfoDetail = OaSpUtil.getUserInfoDetail();
        loadInfo();
    }

    private void loadInfo() {
        Glide.with(this).load(mUserInfoDetail.getHeadPhoto())
                .apply(ImageLoadUtil.sAvatarOptions)
                .into(mIvAvatar);
        mSivUserName.setContentText(!TextUtils.isEmpty(mUserInfoDetail.getUserName()) ? mUserInfoDetail.getUserName() : "尚未设置用户名");
        mSivRealName.setContentText(!TextUtils.isEmpty(mUserInfoDetail.getRealname()) ? mUserInfoDetail.getRealname() : "尚未设置真实姓名");
        mSivSex.setContentText(!TextUtils.isEmpty(mUserInfoDetail.getSex()) ? mUserInfoDetail.getSex() : "尚未设置性别");
        mSivBirthDay.setContentText(!TextUtils.isEmpty(mUserInfoDetail.getBirthday()) ? mUserInfoDetail.getBirthday() : "尚未设置出生日期");
        mSivTel.setContentText(!TextUtils.isEmpty(mUserInfoDetail.getTelPhone()) ? mUserInfoDetail.getTelPhone() : "尚未设置手机号码");
        mSivShortTel.setContentText(!TextUtils.isEmpty(mUserInfoDetail.getMobilePhone()) ? mUserInfoDetail.getMobilePhone() : "尚未设置手机短号");
        mSivOfficePhone.setContentText(!TextUtils.isEmpty(mUserInfoDetail.getSectionPhone()) ? mUserInfoDetail.getSectionPhone() : "尚未设置科室电话");
        mSivQq.setContentText(!TextUtils.isEmpty(mUserInfoDetail.getQq()) ? mUserInfoDetail.getQq() : "尚未设置QQ号");
        mSivWechat.setContentText(!TextUtils.isEmpty(mUserInfoDetail.getWeChat()) ? mUserInfoDetail.getWeChat() : "尚未设置微信号");
        mSivOffice.setContentText(!TextUtils.isEmpty(mUserInfoDetail.getUserSecition()) ? mUserInfoDetail.getUserSecition() : "尚未设置所在科室");
        mSivDuties.setContentText(!TextUtils.isEmpty(mUserInfoDetail.getUserJob()) ? mUserInfoDetail.getUserJob() : "尚未设置职务");
    }

    private String sex;

    @OnClick({R.id.ll_avatar, R.id.siv_real_name, R.id.siv_birth_day, R.id.siv_sex, R.id.siv_tel, R.id.siv_short_tel, R.id.siv_qq, R.id.siv_wechat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_avatar:
                //启动修改头像
                String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/wenyanOA/CompressAvatar/";
                File file = new File(dir);
                file.mkdirs();
                File outFile = new File(dir, mUserInfoDetail.getUserId() + "_" + System.currentTimeMillis() + ".jpg");
                Uri uri = Uri.fromFile(outFile);
                CropOptions cropOptions = new CropOptions.Builder().setAspectX(1).setAspectY(1).setWithOwnCrop(true).create();
                CompressConfig compressConfig = new CompressConfig.Builder().setMaxSize(1024 * 1024).setMaxPixel(1024).create();
                getTakePhoto().onEnableCompress(compressConfig, true);
                getTakePhoto().onPickFromGalleryWithCrop(uri, cropOptions);
                break;
            case R.id.siv_birth_day:
                final Calendar calendar = Calendar.getInstance();
                if (!TextUtils.isEmpty(mUserInfoDetail.getBirthday())) {
                    Date date = TimeUtils.string2Date(mUserInfoDetail.getBirthday(), TimeUtils.CUSTOM_FORMAT);
                    calendar.setTime(date);
                }
                DatePickerDialog birthdayDpd = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                calendar.set(year, monthOfYear, dayOfMonth);
                                String birthday = TimeUtils.date2String(calendar.getTime(), TimeUtils.CUSTOM_FORMAT);
                                mUserInfoDetail.setBirthday(birthday);
                                updateUserInfo(mUserInfoDetail);
                            }
                        },
                        calendar.get(Calendar.YEAR), // Initial year selection
                        calendar.get(Calendar.MONTH), // Initial month selection
                        calendar.get(Calendar.DAY_OF_MONTH) // Inital day selection
                );
                birthdayDpd.vibrate(false);
                birthdayDpd.setTitle("请选择出生日期");
                birthdayDpd.show(getFragmentManager(), "BirthdayDatePickerDialog");
                break;
            case R.id.siv_real_name:
                showEditDialog("真实姓名", false, new InfoApply() {
                    @Override
                    public void applyInfo(String content) {
                        mUserInfoDetail.setRealname(content);
                    }
                });
                break;
            case R.id.siv_sex:
                final String[] sexChoice = {"男", "女"};
                new AlertDialog.Builder(this)
                        .setTitle("修改性别")
                        .setSingleChoiceItems(sexChoice, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sex = sexChoice[which];
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mUserInfoDetail.setSex(sex);
                                updateUserInfo(mUserInfoDetail);
                            }
                        })
                        .setNegativeButton("取消", null)
                        .create().show();

                break;
            case R.id.siv_tel:
                showEditDialog("电话号码", true, new InfoApply() {
                    @Override
                    public void applyInfo(String content) {
                        mUserInfoDetail.setTelPhone(content);
                    }
                });
                break;
            case R.id.siv_short_tel:
                showEditDialog("手机短号", true, new InfoApply() {
                    @Override
                    public void applyInfo(String content) {
                        mUserInfoDetail.setMobilePhone(content);
                    }
                });
                break;
            case R.id.siv_qq:
                showEditDialog("QQ号", true, new InfoApply() {
                    @Override
                    public void applyInfo(String content) {
                        mUserInfoDetail.setQq(content);
                    }
                });
                break;
            case R.id.siv_wechat:
                showEditDialog("微信号", false, new InfoApply() {
                    @Override
                    public void applyInfo(String content) {
                        mUserInfoDetail.setWeChat(content);
                    }
                });
                break;
        }
    }

    private void showEditDialog(String typeName, boolean isNumber, final InfoApply infoApply) {
        View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_change_info_edittext, null);
        final EditText etPersonalInfo = inflate.findViewById(R.id.et_personal_info);
        etPersonalInfo.setInputType(isNumber ? InputType.TYPE_CLASS_NUMBER : InputType.TYPE_CLASS_TEXT);
        etPersonalInfo.setHint(String.format("请输入%s", typeName));
        new AlertDialog.Builder(this)
                .setTitle(String.format("修改%s", typeName))
                .setView(inflate)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String content = etPersonalInfo.getEditableText().toString().trim();
                        infoApply.applyInfo(content);
                        OaSpUtil.getUserInfoDetail().set
                        updateUserInfo(mUserInfoDetail);
                    }
                })
                .setNegativeButton("取消", null)
                .create().show();
    }

    private interface InfoApply {
        void applyInfo(String content);
    }

    private void updateUserInfo(final UserInfoDetailBean userInfoDetail) {
        showLoading("提交修改中...");
        HttpHelper.getInstance().updateUserInfo(userInfoDetail, new NetworkCallback() {
            @Override
            public void onSuccess(Call call, String json) {
                loadSuccess("修改成功", false);
                OaSpUtil.saveUserDetailInfo(userInfoDetail);
                loadInfo();
            }

            @Override
            public void onFailure(Call call, String message) {
                loadFailed("修改失败，" + message);
            }
        });
    }

    @Override
    public void takeSuccess(TResult result) {
        final String compressPath = result.getImage().getCompressPath();
        final String fileName = new File(compressPath).getName();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                //上传头像
                showLoading("提交修改中...");
                String base64Code = ImageBase64Util.imageToBase64(compressPath);
                UpdateAvatarParams params = new UpdateAvatarParams(mUserInfoDetail.getUserId(), mUserInfoDetail.getApikey(), base64Code, fileName);
                HttpHelper.getInstance().updateUserHeadPhoto(params, new NetworkCallback() {
                    @Override
                    public void onSuccess(Call call, String json) {
                        UpdateAvatarResultBean updateAvatarResult = JSON.parseObject(json, UpdateAvatarResultBean.class);

                        updateAvatarResult.getData()
                        loadSuccess("修改成功", false);
                    }

                    @Override
                    public void onFailure(Call call, String message) {
                        loadFailed("修改失败");
                    }
                });
            }
        });
    }
}
