package com.chinaoly.cp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Created by yijixin at 2017/11/8
 * 波浪效果0
 */
public class WaveView extends View{

    private Path mAbovePath;
    private Path mBelowPath;

    private Paint mAbovePaint;
    private Paint mBelowPaint;
    /**
     * 参数1 φ
     */
    private float waveFlag_1;

    /**
     * 振幅  上下浮动
     */
    private static int rangeA = 15;
    /**
     * 宽度浮动，每次走的宽度
     */
    private static final int WIDTH_RANGE = 20;

    public WaveView(Context context) {
        this(context,null);
    }

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //初始化路径
        mAbovePath = new Path();
        mBelowPath = new Path();
        //初始化画笔
        mAbovePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mAbovePaint.setStyle(Paint.Style.FILL);
        mAbovePaint.setColor(Color.WHITE);
        mAbovePaint.setAntiAlias(true);

        mBelowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBelowPaint.setStyle(Paint.Style.FILL);
        mBelowPaint.setColor(Color.WHITE);
        mBelowPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mAbovePath.reset();
        mBelowPath.reset();

        //参数1 的 左右移动
        waveFlag_1 = waveFlag_1 - 0.1f;
        //参数2 ω
        double waveFlag_2 = 2 * Math.PI / getWidth();

        float y1,y2;
        /**
         *  y=Asin(ωx+φ)+k
         *  A—振幅越大，波形在y轴上最大与最小值的差值越大
         *  ω—角速度， 控制正弦周期(单位角度内震动的次数)
         *  φ—初相，反映在坐标系上则为图像的左右移动。这里通过不断改变φ,达到波浪移动效果
         *  k—偏距，反映在坐标系上则为图像的上移或下移。
         */

        //路径初始点
        mAbovePath.moveTo(getLeft(),getBottom());
        mBelowPath.moveTo(getLeft(),getBottom());

        for (int x = 0; x <= getWidth(); x = x + WIDTH_RANGE) {
            //移动
            y1 = (float) (rangeA*Math.cos(waveFlag_2*x+waveFlag_1)+15);
            y2 = (float) (rangeA*Math.sin(waveFlag_2*x+waveFlag_1)+15);

            mAbovePath.lineTo(x,y1);
            mBelowPath.lineTo(x,y2);

        }

        mAbovePath.lineTo(getRight(),getBottom());
        mBelowPath.lineTo(getRight(),getBottom());

        canvas.drawPath(mAbovePath,mAbovePaint);
        canvas.drawPath(mBelowPath,mBelowPaint);

        postInvalidateDelayed(20);
    }
}
