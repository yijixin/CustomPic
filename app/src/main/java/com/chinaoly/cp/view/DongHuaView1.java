package com.chinaoly.cp.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.chinaoly.cp.utils.BezierEvaluator;

/**
 * @author Created by yijixin at 2017/11/20
 */
public class DongHuaView1 extends View {

    private Paint mPaint;
    private Point currentPoint;

    public DongHuaView1(Context context) {
        this(context,null);
    }

    public DongHuaView1(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();

        if (currentPoint == null){
            currentPoint = new Point(50,50);
            drawCircle(canvas);
            startAnimation();
        }else{
            drawCircle(canvas);
        }

        canvas.restore();
    }

    private void drawCircle(Canvas canvas){
        float x = currentPoint.x;
        float y = currentPoint.y;
        canvas.drawCircle(x,y,10,mPaint);
    }

    private void startAnimation(){
        Point startPoint = new Point(50,50);
        Point endPoint = new Point(getWidth()-50,600);
        Point controlPoint = new Point(500,getHeight()-200);
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new BezierEvaluator(controlPoint),startPoint,endPoint);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentPoint = (Point) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.setInterpolator(new AccelerateInterpolator());
        valueAnimator.setDuration(3000);
        valueAnimator.start();
    }
}
