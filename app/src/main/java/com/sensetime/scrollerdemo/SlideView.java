package com.sensetime.scrollerdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * @author qinhaihang_vendor
 * @version $Rev$
 * @time 2019/7/17 14:46
 * @des
 * @packgename com.sensetime.scrollerdemo
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes
 */
public class SlideView extends ViewGroup {

    private Scroller mScroller;
    private ViewConfiguration mViewConfiguration;
    private float mXDown;
    private float mLastXMove;
    private float mXMove;
    private int mTouchSlop;

    public SlideView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public SlideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public SlideView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mScroller = new Scroller(context);
        mViewConfiguration = ViewConfiguration.get(context);
        float scaledHorizontalScrollFactor = ViewConfigurationCompat.getScaledHorizontalScrollFactor(mViewConfiguration, context);
        Log.i("qhh", "scaledHorizontalScrollFactor = " + scaledHorizontalScrollFactor);
        float scaledVerticalScrollFactor = ViewConfigurationCompat.getScaledVerticalScrollFactor(mViewConfiguration, context);
        Log.i("qhh", "scaledVerticalScrollFactor = " + scaledVerticalScrollFactor);
        int scaledHoverSlop = ViewConfigurationCompat.getScaledHoverSlop(mViewConfiguration);
        Log.i("qhh", "scaledHoverSlop = " + scaledHoverSlop);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(mViewConfiguration);
        Log.i("qhh", "touchSlop = " + mTouchSlop);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i("qhh", "onMeasure");
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.i("qhh", "onLayout changed " + changed);
        if (changed) {
            int childCount = getChildCount();
            Log.d("qhh", ">>> childCount = " + childCount);
            int previousWidth = 0;
            for (int i = 0; i < childCount; i++) {
                View childView = getChildAt(i);

                /*childView.layout(i * childView.getMeasuredWidth() + previousWidth, 0,
                        (i + 1) * childView.getMeasuredWidth() + previousWidth,
                        childView.getMeasuredHeight());*/

                childView.layout(previousWidth, 0,
                        childView.getMeasuredWidth() + previousWidth,
                        childView.getMeasuredHeight());
                previousWidth += childView.getMeasuredWidth();

                Log.i("qhh", ">>> i = " + i + " , childView Width = " + childView.getMeasuredWidth()
                        + " , childView Height = " + childView.getMeasuredHeight());
            }

        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i("qhh", "onDraw");
    }

    /*@Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        return super.onInterceptHoverEvent(event);
    }*/

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //super.onInterceptTouchEvent(ev);

        Log.v("qhh_slide", "============================== onInterceptTouchEvent ==============================");
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mXDown = ev.getRawX();
                mLastXMove = mXDown;
                Log.d("qhh_slide", ">>> onInterceptTouchEvent ACTION_DOWN mLastXMove " + mLastXMove);
                break;
            case MotionEvent.ACTION_MOVE:
                mXMove = ev.getRawX();
                mLastXMove = mXMove;
                Log.d("qhh_slide", ">>> onInterceptTouchEvent ACTION_MOVE mLastXMove " + mLastXMove);
                float diff = Math.abs(mXMove - mXDown);
                if (diff >= mTouchSlop) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.d("qhh_slide", ">>> onInterceptTouchEvent ACTION_UP mLastXMove " + mLastXMove);
                break;
            default:
                break;
        }

        return false;
    }

   /* @Override
    public boolean onTouchEvent(MotionEvent event) {

        Log.v("qhh_slide","============================== onTouchEvent ==============================");
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mXMove = event.getRawX();
                int srolledX = (int) (mLastXMove - mXMove);
                scrollBy(srolledX,0);
                mLastXMove = mXMove;
                Log.d("qhh_slide",">>> onTouchEvent ACTION_MOVE mLastXMove " + mLastXMove);
                break;
            case MotionEvent.ACTION_UP:

                break;
            default:
                break;
        }

        return true; //返回：true,则消耗事件，如果使用 super.onTouchEvent 可能会返回 false，则不会走 MOVE 事件。
    }*/

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            //当在View中则是移动View中的内容，如果parent是ViewGroup 则会移动整体的ViewGroup中所有的内容
            //((View) getParent()).scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    /**
     * 单位是像素
     *
     * @param destX
     * @param DestY
     */
    public void smoothScrollTo(int destX, int DestY) {
        int scrollX = getScrollX();
        int delta = destX - scrollX;
        mScroller.startScroll(scrollX, 0, delta, 0, 2000);
        invalidate();
    }

}
