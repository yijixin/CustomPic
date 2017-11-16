package com.chinaoly.cp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Created by yijixin at 2017/11/16
 */
public class CircleCustomView extends View {

    private Paint mPaint;
    private Path mPath;

    public CircleCustomView(Context context) {
        this(context,null);
    }

    public CircleCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);

        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPath.setFillType(Path.FillType.EVEN_ODD);
        //CW顺时针  CCW逆时针   Fill全填充 对于顺时针逆时针没有影响
        mPath.addCircle(getWidth()/2,getWidth()/2,300, Path.Direction.CW);
        mPath.addCircle(getWidth()/2,getWidth()/2,150, Path.Direction.CW);
        canvas.drawPath(mPath,mPaint);
    }
}
