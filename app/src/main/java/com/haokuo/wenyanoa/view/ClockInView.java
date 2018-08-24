package com.haokuo.wenyanoa.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.haokuo.wenyanoa.R;
import com.haokuo.wenyanoa.util.SafeHandler;
import com.haokuo.wenyanoa.util.utilscode.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by zjf on 2018-08-13.
 */

public class ClockInView extends View {

    private int mWidthSize;
    private int mHeightSize;
    private Paint mBgPaint;
    private Paint mTitlePaint;
    private Paint mTimePaint;
    private SafeHandler<Context> mHandler;
    private SimpleDateFormat mDateFormat;
    private int mColorResId;
    private String mTitleText;
    private boolean mCanClock;

    public void setBgColorResId(int colorResId) {
        mColorResId = colorResId;
    }

    public ClockInView(Context context) {
        this(context, null);
    }

    private void handlerMessage(Message msg) {
        invalidate();
        mHandler.sendEmptyMessageDelayed(0, 1000);
    }

    public ClockInView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mHandler = new SafeHandler<>(context, new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                ClockInView.this.handlerMessage(msg);
                return true;
            }
        });
        setCanClock(false, "");
        mBgPaint = new Paint();
        mBgPaint.setStyle(Paint.Style.FILL);
        mBgPaint.setAntiAlias(true);
        mBgPaint.setColor(ContextCompat.getColor(context, mColorResId));
        mTitlePaint = new Paint();
        mTitlePaint.setStyle(Paint.Style.FILL);
        mTitlePaint.setAntiAlias(true);
        mTitlePaint.setColor(ContextCompat.getColor(context, R.color.colorWhite));
        mTitlePaint.setTextAlign(Paint.Align.CENTER);
        mTitlePaint.setTextSize(getResources().getDimension(R.dimen.text_32px));
        Paint.FontMetrics fontMetrics = mTitlePaint.getFontMetrics();
        mTimePaint = new Paint();
        mTimePaint.setStyle(Paint.Style.FILL);
        mTimePaint.setAntiAlias(true);
        mTimePaint.setColor(ContextCompat.getColor(context, R.color.colorHalfWhite));
        mTimePaint.setTextAlign(Paint.Align.CENTER);
        mTimePaint.setTextSize(getResources().getDimension(R.dimen.text_26px));
        Paint.FontMetrics fontMetrics2 = mTimePaint.getFontMetrics();
        mDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        mHandler.sendEmptyMessageDelayed(0, 1000);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        mWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        mHeightSize = MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(mWidthSize / 2.f, mHeightSize / 2.f, mWidthSize / 2.f, mBgPaint);
        canvas.drawText(mTitleText, mWidthSize / 2.f, mHeightSize / 2.f, mTitlePaint);
        String nowString = TimeUtils.getNowString(mDateFormat);
        canvas.drawText(nowString, mWidthSize / 2.f, mHeightSize * 2.f / 3.f, mTimePaint);
    }

    public boolean isCanClock() {
        return mCanClock;
    }

    public void setCanClock(boolean canClock, String titleText) {
        mCanClock = canClock;
        if (mCanClock) {
            mColorResId = R.color.colorPrimary;
            mTitleText = titleText;
        } else {
            mColorResId = R.color.colorUnable;
            mTitleText = "不能打卡";
        }
        invalidate();
    }
}
