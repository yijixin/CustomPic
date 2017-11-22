package com.chinaoly.cp.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.chinaoly.cp.utils.ConstantUtils;

/**
 * @author Created by yijixin at 2017/11/22
 */
public class BottomBarView extends RelativeLayout {

    /**
     * 上拉顶部View
     */
    private View bottomView;

    /**
     * 内容列表
     */
    private View bottomContentView;

//    private View allView;

    /**
     * 使控件滑动
     */
    private Scroller mScroller;

    private int downY;
    private int scrollOffset;

    public BottomBarView(Context context) {
        this(context, null);
    }

    public BottomBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //初始化
        initView();
    }

    private void initView() {
        int count = getChildCount();
        if(count != 2){
        }
//        allView = getChildAt(0);
        bottomView = getChildAt(0);
        bottomContentView = getChildAt(1);

        Rect barRect = new Rect();
        mScroller = new Scroller(getContext());
        bottomView.getGlobalVisibleRect(barRect);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //按下时的Y轴位置点
                downY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //滑动时的Y轴位置点
                int endY = (int) event.getY();
                int dy = endY - downY;
                //滑动的距离
                int toScrollY = getScrollY() - dy;
                if (toScrollY < 0){
                    toScrollY = 0;
                }else if (toScrollY > bottomContentView.getMeasuredHeight()){
                    //滑动距离大于内容高度让其等于内容View的高度
                    toScrollY = bottomContentView.getMeasuredHeight();
                }
                scrollTo(0,toScrollY);
                downY = (int) event.getY();
                break;
            case MotionEvent.ACTION_UP:
                //抬手时 位移量等于滑动距离
                scrollOffset = getScrollY();
                if (scrollOffset > bottomContentView.getMeasuredHeight() / ConstantUtils.VIEW_HALF){
                    showContentView();
                }else{
                    closeContentView();
                }
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 展示Content内容的View
     */
    private void showContentView(){
        int dy = bottomContentView.getMeasuredHeight() - scrollOffset;
        //500毫秒完成
        mScroller.startScroll(getScrollX(),getScrollY(),0,dy,500);
        invalidate();
    }

    /**
     * 关闭Content内容的View
     */
    private void closeContentView(){
        int dy = 0 - scrollOffset;
        mScroller.startScroll(getScrollX(),getScrollY(),0,dy,500);
        invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        /**
         * bottomView的控件大小范围
         * 分别为控件的左、上、右、下的位置
         * getMeasureHeight屏幕的高度   getMeasureWidth屏幕的宽度
         */
        bottomView.layout(0,getMeasuredHeight() - bottomView.getMeasuredHeight(),getMeasuredWidth(),getMeasuredHeight());

        bottomContentView.layout(0,getMeasuredHeight(),getMeasuredWidth(),bottomView.getBottom() + bottomContentView.getMeasuredHeight());

//        allView.layout(0,0,getMeasuredWidth(),getMeasuredHeight());
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            invalidate();
        }
    }
}
