package com.chinaoly.cp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.chinaoly.cp.R;
import com.chinaoly.cp.beans.Ability;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by yijixin at 2017/11/13
 */
public class AbilityLolView extends View{

    /**
     * 最外层颜色
     */
    private int zuiWaiColor;

    /**
     * 次外层颜色
     */
    private int erCengColor;

    /**
     * 次内层颜色
     */
    private int sanCengColor;

    /**
     * 最内层颜色
     */
    private int neiCengColor;

    /**
     * 各个点的集合
     */
    private List<ArrayList<PointF>> mPointsList;

    /**
     * 线画笔
     */
    private Paint mLinePaint;

    /**
     * 文本画笔
     */
    private TextPaint mTextPaint;

    /**
     * 半径 (也可叫直角三角形的斜边)
     */
    private float radius;

    /**
     * 边的数量
     */
    private int n;

    /**
     * 两条顶点到中线点的线之间的角度
     */
    private float angle;

    /**
     * 间隔数量，就把半径分为几段
     */
    private int intervalCount;

    /**
     * 控件的宽高
     */
    private int viewWidth,viewHeight;

    public void setAbility(Ability ability) {
        mAbility = ability;
    }

    private Ability mAbility;

    public AbilityLolView(Context context) {
        this(context,null);
    }

    public AbilityLolView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AbilityLolView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.AbilityLolView,defStyleAttr,0);
        int count = typedArray.getIndexCount();
        for (int i = 0; i < count; i++) {
            int index = typedArray.getIndex(i);
            switch (index){
                case R.styleable.AbilityLolView_colorZuiWai:
                    zuiWaiColor = typedArray.getColor(index, Color.BLUE);
                    break;
                case R.styleable.AbilityLolView_colorErCeng:
                    erCengColor = typedArray.getColor(index,Color.WHITE);
                    break;
                case R.styleable.AbilityLolView_colorSanCeng:
                    sanCengColor = typedArray.getColor(index,Color.RED);
                    break;
                case R.styleable.AbilityLolView_colorZuiNei:
                    neiCengColor = typedArray.getColor(index,Color.GREEN);
                    break;
                default:
                    break;
            }
            typedArray.recycle();
            
            mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mLinePaint.setStyle(Paint.Style.FILL_AND_STROKE);
            mLinePaint.setStrokeWidth(4);
            
            mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
            mTextPaint.setTextSize(14 * getResources().getDisplayMetrics().density);

            initSize(context);
            //点 集合
            initPoint();
        }
    }

    private void initSize(Context context) {
        n = 7;
        intervalCount = 4;
        angle = (float) ((2 * Math.PI)/n);
        //100dp
        radius = dip2px(context,100);

        int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        viewHeight = viewWidth = screenWidth;
//        viewHeight = screenWidth;
    }

    private void initPoint() {
        mPointsList = new ArrayList<>();
        float x,y;
        for (int i = 0; i < intervalCount; i++) {
            ArrayList<PointF> points = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                float r = radius * ((float) (4-i)/intervalCount);
                x = (float)(r * Math.cos(angle * j - Math.PI/2));
                y = (float)(r * Math.sin(angle * j - Math.PI/2));
                points.add(new PointF(x,y));
            }
            mPointsList.add(points);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(viewWidth,viewHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initSize(getContext());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //把画布的原点移动到控件的中心点
        canvas.translate(viewWidth/2,viewHeight/2);
        drawPolygon(canvas);
        drawOutLine(canvas);
        drawAbilityLine(canvas);
        drawAbilityText(canvas);
    }

    /**
     * 画文字
     * @param canvas 画布
     */
    private void drawAbilityText(Canvas canvas) {
        canvas.save();

        List<PointF> textPoints = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            float r;
            if (i==5||i==6){
                r = radius + dip2px(getContext(), 29f);
            }else if(i==1||i==2){
                r = radius + dip2px(getContext(), 8f);
            }else if (i==0){
                r = radius + dip2px(getContext(), 10f);
            }else{
                r = radius + dip2px(getContext(), 14f);
            }
            float x = (float)(r * Math.cos(angle * i - Math.PI/2));
            float y = (float)(r * Math.sin(angle * i - Math.PI/2));
            textPoints.add(new PointF(x,y));
        }

        //拿到字体测量器
        Paint.FontMetrics metrics = mTextPaint.getFontMetrics();
        String[] abilitys = Ability.abilitys;
        for (int i = 0; i < n; i++) {
            float x = textPoints.get(i).x;
            //ascent:上坡度，是文字的基线到文字的最高处的距离
            //descent:下坡度,，文字的基线到文字的最低处的距离
            float y = textPoints.get(i).y
                    - (metrics.ascent + metrics.descent) / 2;
            canvas.drawText(abilitys[i], x, y, mTextPaint);
        }

        canvas.restore();
    }

    /**
     * 画能力线
     * @param canvas 画布
     */
    private void drawAbilityLine(Canvas canvas) {
        if (mAbility != null){
            float x,y;
            int[] ability = {mAbility.getKill(),mAbility.getSurvival(),mAbility.getAssist(),mAbility.getAd(),mAbility.getAp(),mAbility.getDefense(),mAbility.getMoney()};
            ArrayList<PointF> points = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                float r = (ability[j]/100.0f) * radius;
                x = (float)(r * Math.cos(angle * j - Math.PI/2));
                y = (float)(r * Math.sin(angle * j - Math.PI/2));
                points.add(new PointF(x,y));
            }

            canvas.save();

            Path path = new Path();
            mLinePaint.setColor(Color.RED);
            mLinePaint.setStyle(Paint.Style.STROKE);
            mLinePaint.setStrokeWidth(3);

            for (int i = 0; i < n; i++) {
                float x1 = points.get(i).x;
                float y1 = points.get(i).y;
                if (i == 0){
                    path.moveTo(x1,y1);
                }else{
                    path.lineTo(x1,y1);
                }
            }
            path.close();
            canvas.drawPath(path,mLinePaint);

            canvas.restore();
        }
    }

    /**
     * 绘制轮廓线
     * 1.最外层多边形
     * 2.各点到中心
     * @param canvas 画布
     */
    private void drawOutLine(Canvas canvas) {
        canvas.save();

        Path path = new Path();
        mLinePaint.setColor(erCengColor);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(3);
        for (int i = 0; i < n; i++) {
            float x = mPointsList.get(0).get(i).x;
            float y = mPointsList.get(0).get(i).y;
            if (i==0){
                path.moveTo(x,y);
            }else{
                path.lineTo(x,y);
            }
        }
        path.close();
        canvas.drawPath(path,mLinePaint);
        path.reset();

        for (int i = 0; i < n; i++) {
            float x = mPointsList.get(0).get(i).x;
            float y = mPointsList.get(0).get(i).y;

            canvas.drawLine(0,0,x,y,mLinePaint);
        }

        canvas.restore();
    }

    /**
     * 绘制多边形框,每一层都绘制
     * @param canvas 画布
     */
    private void drawPolygon(Canvas canvas) {
        canvas.save();

        Path path = new Path();
        for (int i = 0; i < intervalCount; i++) {
            switch (i){
                case 0:
                    mLinePaint.setColor(zuiWaiColor);
                    break;
                case 1:
                    mLinePaint.setColor(erCengColor);
                    break;
                case 2:
                    mLinePaint.setColor(sanCengColor);
                    break;
                case 3:
                    mLinePaint.setColor(neiCengColor);
                    break;
                default:
                    break;
            }
            for (int j = 0; j < n; j++) {
                float x = mPointsList.get(i).get(j).x;
                float y = mPointsList.get(i).get(j).y;
                if (j == 0){
                    path.moveTo(x,y);
                }else{
                    path.lineTo(x,y);
                }
            }
            path.close();
            canvas.drawPath(path,mLinePaint);
            //清除path存储的路径
            path.reset();
        }

        canvas.restore();
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    private static int px2dip(Context context,float pxValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue/scale+0.5f);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
