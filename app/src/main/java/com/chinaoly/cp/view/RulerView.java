package com.chinaoly.cp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;

import com.chinaoly.cp.R;

import java.util.Calendar;

/**
 * @author Created by yijixin at 2017/11/11
 */
public class RulerView extends View {

    private static final int ITEM_HALF_DIVIDER = 60;
    private static final int ITEM_MAX_HEIGHT = 10;

    /**
     * 刻度下字体大小
     */
    private float textSize;

    /**
     * 显示的字体大小
     */
    private float dateTimeSize;

    /**
     * 整体的背景色
     */
    private int colorBg;

    /**
     * 中线的颜色
     */
    private int colorMiddleLine;

    /**
     * 刻度的颜色
     */
    private int colorKeDu;

    /**
     * 走过时间的背景色
     */
    private int colorTimeBg;

    /**
     * 中线的画笔和背景的画笔
     */
    private Paint mMiddleLinePaint,mBgPaint;

    /**
     * 刻度的画笔
     */
    private Paint linePaint;

    /**
     * 刻度下的字体画笔和显示日期时间的字体画笔
     */
    private TextPaint textPaint,dateAndTimePaint;

    /**
     * 当前的时间
     */
    private Calendar mCalendar;

    /**
     * 左右的时间刻度
     */
    private Calendar mLeftCalendar,mRightCalendar;

    /**
     * 屏幕像素点
     */
    private float mDensity;

    /**
     * 当前刻度值
     */
    private int mValue = 12;
    private int mLineDivider = ITEM_HALF_DIVIDER;

    private float mLastX;
    /**
     * 记录刻度盘滑动的偏移量
     */
    private float mMove;
    private float mWidth, mHeight;

    /**
     * 手指抬起后最近滑动距离
     */
    private int mMinVelocity;

    /**
     * 控制控件的滑动
     */
    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;

    private float scaleUnit;
    private int hour,minute,second;

    /**
     * 线条底部的位置
     */
    float lineBottom;
    /**
     * 线条顶部得到位置
     */
    float lineTop;

    /**
     * 文字所占的宽度
     */
    private float textWidth;
    private boolean isChangeFromInSide;

    private boolean isNeedDrawableLeft, isNeedDrawableRight;

    public RulerView(Context context) {
        this(context, null);
    }

    public RulerView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RulerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RulerView,defStyleAttr,0);
        int count = typedArray.getIndexCount();
        for (int i = 0; i < count; i++) {
            int index = typedArray.getIndex(i);
            switch (index){
                case R.styleable.RulerView_textSize:
                    textSize = typedArray.getDimensionPixelSize(index, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,14,getResources().getDisplayMetrics()));
                    break;
                case R.styleable.RulerView_dateTimeSize:
                    dateTimeSize = typedArray.getDimensionPixelSize(index, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,16,getResources().getDisplayMetrics()));
                    break;
                case R.styleable.RulerView_colorBg:
                    colorBg = typedArray.getColor(index, Color.BLACK);
                    break;
                case R.styleable.RulerView_colorMiddleLine:
                    colorMiddleLine = typedArray.getColor(index,Color.RED);
                    break;
                case R.styleable.RulerView_colorKeDu:
                    colorKeDu = typedArray.getColor(index,Color.WHITE);
                    break;
                case R.styleable.RulerView_colorTimeBg:
                    colorTimeBg = typedArray.getColor(index,Color.BLUE);
                    break;
                default:
            }
            typedArray.recycle();

            mScroller = new Scroller(context);
            mDensity = getContext().getResources().getDisplayMetrics().density;
            mMinVelocity = ViewConfiguration.get(getContext()).getScaledMaximumFlingVelocity();

            linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            linePaint.setStrokeWidth(2);
            linePaint.setColor(colorKeDu);

            mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mBgPaint.setStrokeWidth(2);
            mBgPaint.setColor(colorBg);

            textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
            textPaint.setTextSize(textSize * mDensity);

            dateAndTimePaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
            dateAndTimePaint.setTextSize(dateTimeSize * mDensity);

            mMiddleLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            scaleUnit = mLineDivider * mDensity;
            mCalendar = Calendar.getInstance();
            initDateAndTime(mCalendar);

            mLeftCalendar = Calendar.getInstance();
            mRightCalendar = Calendar.getInstance();
        }
    }

    /**
     * 根据时间来计算偏差，(minute*60+second)*scaleUnit/3600
     */
    private void initOffSet() {
        mMove = (minute * 60 + second) * scaleUnit / 3600;
    }

    private void initDateAndTime(Calendar calendar) {
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        second = calendar.get(Calendar.SECOND);
        mValue = hour;
        initOffSet();
    }

    /**
     * 获取当前刻度值
     *
     * @return
     */
    public float getValue() {
        return mValue;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        mWidth = getWidth();
        mHeight = getHeight();
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawMiddleLine(canvas);
        drawScaleLine(canvas);
    }

    private boolean isChange = false;
    private float offsetPercent;
    /**
     * 从中间往两边开始画刻度线
     * @param canvas 画布
     */
    private void drawScaleLine(Canvas canvas) {
        canvas.save();

        isNeedDrawableLeft = true;
        isNeedDrawableRight = true;
        //控件宽度
        float width = mWidth;
        //位置
        float xPosition = 0;
        //刻度底部
        lineBottom = mHeight - getPaddingBottom();
        //刻度顶部
        lineTop = lineBottom - mDensity * ITEM_MAX_HEIGHT;

        //控制mValue的值在0到23之间
        if (mValue>0){
            mValue = mValue % 24;
        }else if (mValue<0){
            mValue = mValue % 24 + 24;
        }
        // <0向左滑动,>0向右滑动
        if (mMove < 0){
            if (mValue == 0 && hour != 23){
                mCalendar.set(Calendar.DAY_OF_MONTH,mCalendar.get(Calendar.DAY_OF_MONTH)-1);
            }

            hour = mValue - 1;
            //上一天的23点
            if (hour == -1){
                hour = 23;
            }

            offsetPercent = 1 + mMove / scaleUnit;
        }else if (mValue >= 0){
            offsetPercent = mMove / scaleUnit;
            hour = mValue;
            //滑动到次日0点
            if (hour == 0&& !isChange){
                //如果没有ischange，那么在hour==0时，day会重复加一
                mCalendar.set(Calendar.DAY_OF_MONTH,
                        mCalendar.get(Calendar.DAY_OF_MONTH) + 1);
                // 避免重复把day+1
                isChange = true;
            }

            if (hour != 0){
                isChange = false;
            }
            //获取现在的分钟和秒
            countMinAndSecond(offsetPercent);


            //画刻度
            for (int i = 0; true ; i++) {
                //初始位置往右边开始画
                xPosition = (width/2 - mMove) + i * scaleUnit;
                //在view范围内绘制
                if ( isNeedDrawableRight && xPosition + getPaddingRight() < mWidth){
                    canvas.drawLine(xPosition, lineTop, xPosition, lineBottom, linePaint);
                    textWidth = Layout.getDesiredWidth(int2Str(mValue + i),textPaint);
                    canvas.drawText(int2Str(mValue + i),xPosition - textWidth/2,lineTop - 5,textPaint);
                }else{
                    isNeedDrawableRight = false;
                }

                // 往左边开始画
                // i != 0 防止中间的刻度画两遍
                if (i>0){
                    xPosition = (width/2 - mMove) - i * scaleUnit;
                    if ( isNeedDrawableLeft && xPosition > getPaddingLeft()){
                        canvas.drawLine(xPosition, lineTop, xPosition, lineBottom,linePaint);
                        textWidth = Layout.getDesiredWidth(int2Str(mValue - i),textPaint);
                        canvas.drawText(int2Str(mValue - i), xPosition - textWidth/2 , lineTop - 5,textPaint);
                    }else{
                        isNeedDrawableLeft = false;
                    }
                }

                // 当不需要向左或者向右画的时候就退出循环，结束绘制操作
                if (!isNeedDrawableLeft && !isNeedDrawableRight){
                    break;
                }
            }
        }

        canvas.restore();
    }

    /**
     * 计算分钟和秒钟
     * @param percent
     * @return
     */
    private int[] countMinAndSecond(float percent) {
        minute = (int) ((3600 * percent) / 60);
        second = (int) ((3600 * percent) % 60);
        return new int[]{minute , second};
    }

    /**
     * 画背景色
     *
     * @param canvas
     */
    private void drawBgColorRect(float left, float top, float right,
                                 float bottom, Canvas canvas) {
        mBgPaint.setColor(colorTimeBg);
        canvas.drawRect(left, top, right, bottom, mBgPaint);
    }

    /**
     * 画中线
     * @param canvas 画布对象
     */
    private void drawMiddleLine(Canvas canvas){
        //保存了当前这个canvas的状态，和已经画出来的图形无关
        canvas.save();

        mMiddleLinePaint.setStrokeWidth(4);
        mMiddleLinePaint.setColor(colorMiddleLine);
        canvas.drawLine(mWidth/2,0,mWidth/2,mHeight,mMiddleLinePaint);
        //取出最近一次save的canvas的状态，仍然不影响图形
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int xPosition = (int) event.getX();

        if (mVelocityTracker == null){
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);

        switch (action){
            case MotionEvent.ACTION_DOWN:
                mScroller.forceFinished(true);
                mLastX = xPosition;
                isChangeFromInSide = true;
                break;
            case MotionEvent.ACTION_MOVE:
                mMove += (mLastX - xPosition);
                changeMoveAndValue();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                countMoveEnd();
                countVelocityTracker(event);
                return false;
            default:
                break;
        }

        mLastX = xPosition;

        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()){
            //滑动完毕
            if (mScroller.getCurrX() == mScroller.getFinalX()){
                countMoveEnd();
                notifyChangeOver();
            }else{
                int xPosition = mScroller.getCurrX();
                mMove += (mLastX - xPosition);
                changeMoveAndValue();
                mLastX = xPosition;
            }
        }
    }

    private void changeMoveAndValue() {
        float fValue = mMove / scaleUnit;
        int tValue = (int) fValue;
        //滑动超过一格以后，记录下当前刻度盘上的值
        if (Math.abs(fValue) > 0) {
            mValue += tValue;
            //偏移量永远都小于一格
            mMove -= tValue * scaleUnit;
//            notifyValueChange();
            postInvalidate();
        }
    }

    private void countMoveEnd() {
        mLastX = 0;
//        notifyValueChange();
        postInvalidate();
    }

    private void countVelocityTracker(MotionEvent event) {
        mVelocityTracker.computeCurrentVelocity(1000,1500);
        float xVelocity = mVelocityTracker.getXVelocity();
        if (Math.abs(xVelocity)>mMinVelocity){
            mScroller.fling(0,0,(int) xVelocity,0,Integer.MIN_VALUE,Integer.MAX_VALUE,0,0);
        }else{
            notifyChangeOver();
        }
    }

    private void notifyChangeOver() {
//        if (null != mListener) {
//            mListener.onValueChangeEnd(mCalendar);
//        }
        isChangeFromInSide = false;
    }

    public String int2Str(int i) {
        if (i > 0) {
            i = i % 24;
        } else if (i < 0) {
            i = i % 24 + 24;
        }
        String str = String.valueOf(i);
        if (str.length() == 1) {
            return "0" + str + ":00";
        } else if (str.length() == 2) {
            return str + ":00";
        }
        return "";
    }

}

