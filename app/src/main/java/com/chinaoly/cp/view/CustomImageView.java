package com.chinaoly.cp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.chinaoly.cp.R;

/**
 * @author Created by yijixin at 2017/11/9
 */
public class CustomImageView extends View {

    private String mTitleText;
    private int mTitleColor;
    private int mTitleSize;
    private Bitmap image;
    private int scaleType;

    private Paint mPaint;
    private Rect mRect;
    private Rect mTextRect;

    private int mWidth,mHeight;

    public CustomImageView(Context context) {
        this(context,null);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //获得我们所定义的自定义样式属性
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomImageView,defStyleAttr,0);
        int count = typedArray.getIndexCount();
        for (int i = 0; i < count; i++) {
            int index = typedArray.getIndex(i);
            switch (index){
                case R.styleable.CustomImageView_image:
                    image = BitmapFactory.decodeResource(getResources(),typedArray.getResourceId(index,0));
                    break;
                case R.styleable.CustomImageView_imageScaleType:
                    scaleType = typedArray.getInt(index,0);
                    break;
                case R.styleable.CustomImageView_titleText:
                    mTitleText = typedArray.getString(index);
                    break;
                case R.styleable.CustomImageView_titleTextColor:
                    mTitleColor = typedArray.getColor(index, Color.BLACK);
                    break;
                case R.styleable.CustomImageView_titleTextSize:
                    mTitleSize = typedArray.getDimensionPixelSize(index, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,16,getResources().getDisplayMetrics()));
                    break;
                default:
                    break;
            }
        }
        typedArray.recycle();

        mRect = new Rect();
        mTextRect = new Rect();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(mTitleSize);
        //先计算好字体占得宽高
        mPaint.getTextBounds(mTitleText,0,mTitleText.length(),mTextRect);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY){
            mWidth = widthSize;
        }else{
            //获取图片的宽度
            int widthByImg = getPaddingLeft()+getPaddingRight()+image.getWidth();
            //获取字体的宽度
            int widthByTitle = getPaddingLeft()+getPaddingRight()+mTextRect.width();

            if (widthMode == MeasureSpec.AT_MOST){
                int desWidth = Math.max(widthByImg,widthByTitle);

                //最终宽度不能超过控件宽度
                mWidth = Math.min(desWidth,widthSize);
            }
        }

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (heightMode == MeasureSpec.EXACTLY){
            mHeight = heightSize;
        }else{
            int desHeight = getPaddingTop()+getPaddingBottom()+image.getHeight()+mTextRect.height();
            if (heightMode == MeasureSpec.AT_MOST){
                mHeight = Math.min(desHeight,heightSize);
            }
        }

        setMeasuredDimension(mWidth,mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制边框
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.CYAN);
        mPaint.setStrokeWidth(4);
        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),mPaint);

        //整天界面大小
        mRect.left = getPaddingLeft();
        mRect.top = getPaddingTop();
        mRect.right = getMeasuredWidth() - getPaddingRight();
        mRect.bottom = getMeasuredHeight() - getPaddingBottom();

        mPaint.setColor(mTitleColor);
        mPaint.setStyle(Paint.Style.FILL);
        //文字不显示部分用xxx   文字宽度大于控件宽度
        if (mTextRect.width()>mWidth){
            TextPaint paint = new TextPaint(mPaint);
            //截取规定长度的文字
            String msg = TextUtils.ellipsize(mTitleText,paint,mWidth - getPaddingRight()-getPaddingLeft(), TextUtils.TruncateAt.END).toString();
            canvas.drawText(msg,getPaddingLeft(),mHeight-getPaddingBottom(),mPaint);
        }else{
            canvas.drawText(mTitleText,mWidth/2 - mTextRect.width()/2,mHeight - getPaddingBottom(),mPaint);
        }

        //去掉文字部分
        mRect.bottom = mRect.bottom - mTextRect.height();

        //0即为fitXy
        if (scaleType == 0){
            canvas.drawBitmap(image,null,mRect,mPaint);
        }else{
            mRect.left = mWidth/2 - image.getWidth()/2;
            mRect.right = mWidth/2 + image.getWidth()/2;
            mRect.top = (mHeight - mTextRect.height())/2 - image.getHeight()/2;
            mRect.bottom = (mHeight - mTextRect.height())/2 + image.getHeight()/2;

            canvas.drawBitmap(image,null,mRect,mPaint);
        }
    }
}
