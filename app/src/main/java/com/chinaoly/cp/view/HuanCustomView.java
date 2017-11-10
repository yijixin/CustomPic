package com.chinaoly.cp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.chinaoly.cp.R;

/**
 * @author Created by yijixin at 2017/11/10
 */
public class HuanCustomView extends View {
    /**
     * 圆环宽度
     */
    private int mCircleWidth;

    /**
     * 第一种颜色
     */
    private int firstColor;

    /**
     * 第二种颜色
     */
    private int secondColor;

    /**
     * 转到角速度
     */
    private int speed;

    /**
     * 转动进度
     */
    private int mProgress;

    /**
     * 判断下一轮开始
     */
    private boolean isNext = false;

    private Paint mPaint;

    public HuanCustomView(Context context) {
        this(context,null);
    }

    public HuanCustomView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HuanCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //获得我们所定义的自定义样式属性
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.HuanCustomView,defStyleAttr,0);
        int count = typedArray.getIndexCount();
        for (int i = 0; i < count; i++) {
            int index = typedArray.getIndex(i);
            switch (index){
                case R.styleable.HuanCustomView_circleWidth:
                    mCircleWidth = typedArray.getDimensionPixelSize(index, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,20,getResources().getDisplayMetrics()));
                    break;
                case R.styleable.HuanCustomView_firstColor:
                    firstColor = typedArray.getColor(index, Color.BLUE);
                    break;
                case R.styleable.HuanCustomView_secondColor:
                    secondColor = typedArray.getColor(index,Color.RED);
                    break;
                case R.styleable.HuanCustomView_speed:
                    speed = typedArray.getInt(index,20);
                    break;
                default:
            }
        }
        typedArray.recycle();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    mProgress++;
                    if (mProgress == 360){
                        mProgress = 0;
                        if (!isNext){
                            isNext = true;
                        }else{
                            isNext = false;
                        }
                    }
                    postInvalidate();
                    try {
                        Thread.sleep(speed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int radius = getWidth()/2 - mCircleWidth/2;
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mCircleWidth);
        RectF rectF = new RectF(getWidth()/2-radius,getWidth()/2-radius,getWidth()/2+radius,getWidth()/2+radius);

        if (!isNext){
            mPaint.setColor(firstColor);
            canvas.drawCircle(getWidth()/2,getWidth()/2,radius,mPaint);
            mPaint.setColor(secondColor);
            canvas.drawArc(rectF,-90,mProgress,false,mPaint);
        }else{
            mPaint.setColor(secondColor);
            canvas.drawCircle(getWidth()/2,getWidth()/2,radius,mPaint);
            mPaint.setColor(firstColor);
            canvas.drawArc(rectF,-90,mProgress,false,mPaint);
        }
    }
}
