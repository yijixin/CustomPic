package com.chinaoly.cp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.chinaoly.cp.beans.AndroidData;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by yijixin at 2017/11/17
 */
public class ShanView extends View {

    private Paint mPaint;
    private List<AndroidData> mDatas;

    public ShanView(Context context) {
        this(context,null);
    }

    public ShanView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);

        mDatas = new ArrayList<>();
        mDatas.add(new AndroidData("Gingerbread",0.01,Color.GREEN));
        mDatas.add(new AndroidData("Ice Cream/Sandwich",0.01,Color.CYAN));
        mDatas.add(new AndroidData("Jelly Bean",0.113,Color.YELLOW));
        mDatas.add(new AndroidData("KitKat",0.219,Color.BLUE));
        mDatas.add(new AndroidData("Lollipop",0.329,Color.MAGENTA));
        mDatas.add(new AndroidData("Marshmallow",0.307,Color.LTGRAY));
        mDatas.add(new AndroidData("Nougat",0.012, Color.RED));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(getWidth()/2,getWidth()/2);
        float radios = getWidth()/2-100;
        float angle = 0;
        float qianAngle = 0;
        RectF rectF;
        for (int i = 0; i < mDatas.size(); i++) {
            /**
             圆点坐标：(x0,y0)
             半径：r
             角度：a0

             则圆上任一点为：（x1,y1）
             x1   =   x0   +   r   *   cos(ao   *   3.14   /180   )
             y1   =   y0   +   r   *   sin(ao   *   3.14   /180   )
             */
            float xuanAngle = (float) (360 * mDatas.get(i).getPercent());
            mPaint.setColor(mDatas.get(i).getColor());
            angle = angle+qianAngle;
            if (i == mDatas.size()-2){

            }else{
                rectF = new RectF(-radios+50,-radios+50,radios+50,radios+50);
                canvas.drawArc(rectF, angle,xuanAngle,true,mPaint);
            }

            qianAngle = xuanAngle;
        }

//        int x1 = (int) (getWidth()/2 + radios * Math.cos((2 * Math.PI - 4.32) * 3.14 / 180));
//        int y1 = (int) (getWidth()/2 + radios * Math.sin((2 * Math.PI - 4.32) * 3.14 / 180));
//        canvas.translate(x1,y1);
//        rectF = new RectF(-radios+50+x1,-radios+50+y1,radios+50-x1,radios+50+y1);
//        canvas.drawArc(rectF, (float) (2 * Math.PI - 4.32 - 2 * Math.PI * mDatas.get(5).getPercent()),(float) (2 * Math.PI * mDatas.get(5).getPercent()),true,mPaint);

//        RectF rectF1 = new RectF(50,50,getWidth()-80,getWidth()-80);
//        canvas.drawArc(rectF1,-180,90,true,mPaint);
//        RectF rectF = new RectF(50,50,getWidth()-50,getWidth()-50);
//        mPaint.setColor(Color.BLUE);
//        canvas.drawArc(rectF,-90,35,true,mPaint);
//        mPaint.setColor(Color.CYAN);
//        canvas.drawArc(rectF,-55,80,true,mPaint);
//        mPaint.setColor(Color.YELLOW);
//        canvas.drawArc(rectF,25,100,true,mPaint);
//        mPaint.setColor(Color.GREEN);
//        canvas.drawArc(rectF,125,55,true,mPaint);

    }
}
