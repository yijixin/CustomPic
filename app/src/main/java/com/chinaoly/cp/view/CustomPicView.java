package com.chinaoly.cp.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.chinaoly.cp.R;

/**
 * @author Created by yijixin at 2017/11/20
 */
public class CustomPicView extends View {

    private Paint mPaint;

    public CustomPicView(Context context) {
        this(context,null);
    }

    public CustomPicView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int w = getWidth();
        int h = getHeight();

        int radius = w <= h ? w/2 : h/2;

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.onepiece);
        //以目标宽高创建一个缩放过的图片
        Bitmap result = Bitmap.createScaledBitmap(bm,w,h,false);

        Shader shader = new BitmapShader(result, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mPaint.setShader(shader);
        canvas.drawCircle(w/2,h/2,radius,mPaint);
    }
}
