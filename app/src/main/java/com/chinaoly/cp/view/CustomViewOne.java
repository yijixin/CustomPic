package com.chinaoly.cp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.chinaoly.cp.R;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @author Created by yijixin at 2017/11/9
 */
public class CustomViewOne extends View{

    /**
     * 文本
     */
    private String mTitleText;

    /**
     * 颜色
     */
    private int mTitleColor;

    /**
     * 字体大小
     */
    private int mTitleSize;

    private Paint mPaint;
    private Rect mRect;

    private static final int COUNT_SIZE = 4;

    public CustomViewOne(Context context) {
        this(context,null);
    }

    public CustomViewOne(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomViewOne(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        /**
         * 获得我们所定义的自定义样式属性
         */
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomViewOne,defStyleAttr,0);
        int count = typedArray.getIndexCount();
        for (int i = 0; i < count; i++) {
            int n = typedArray.getIndex(i);
            switch (n){
                case R.styleable.CustomViewOne_titleText:
                    mTitleText = typedArray.getString(n);
                    break;
                case R.styleable.CustomViewOne_titleTextColor:
                    mTitleColor = typedArray.getColor(n, Color.BLACK);
                    break;
                case R.styleable.CustomViewOne_titleTextSize:
                    mTitleSize = typedArray.getDimensionPixelSize(n, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,16,getResources().getDisplayMetrics()));
                    break;
                default:
                    break;
            }
        }
        typedArray.recycle();

        //初始化
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(mTitleSize);
        mRect = new Rect();
        mPaint.getTextBounds(mTitleText,0,mTitleText.length(),mRect);

        //设置点击事件
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mTitleText = getRandom();
                postInvalidate();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),mPaint);

        mPaint.setColor(mTitleColor);
        canvas.drawText(mTitleText,getWidth()/2-mRect.width()/2,getHeight()/2+mRect.height()/2,mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width,height;

        /**
         * EXACTLY：一般是设置了明确的值或者是MATCH_PARENT
         * AT_MOST：表示子布局限制在一个最大值内，一般为WARP_CONTENT
         * UNSPECIFIED：表示子布局想要多大就多大，很少使用
         */
        if (widthMode == MeasureSpec.EXACTLY){
            width = widthSize;
        }else{
            mPaint.setTextSize(mTitleSize);
            mPaint.getTextBounds(mTitleText,0,mTitleText.length(),mRect);
            float textWidth = mRect.width();
            width = (int) (getPaddingLeft()+getPaddingRight()+textWidth);
        }

        if (heightMode == MeasureSpec.EXACTLY){
            height = heightSize;
        }else{
            mPaint.setTextSize(mTitleSize);
            mPaint.getTextBounds(mTitleText,0,mTitleText.length(),mRect);
            float textHeight = mRect.height();
            height = (int) (getPaddingBottom()+getPaddingTop()+textHeight);
        }

        setMeasuredDimension(width,height);
    }

    private String getRandom(){
        Random random = new Random();
        Set<Integer> set = new HashSet<>();
        while (set.size()<COUNT_SIZE){
            int randomInt = random.nextInt(10);
            set.add(randomInt);
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer i : set){
            stringBuilder.append(""+i);
        }
        return stringBuilder.toString();
    }
}
