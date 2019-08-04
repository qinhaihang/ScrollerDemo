package com.sensetime.scrollerdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
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

    public SlideView(Context context) {
        super(context);
        init(context,null,0);
    }

    public SlideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs,0);
    }

    public SlideView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr){
        mScroller = new Scroller(context);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            measureChild(childView,widthMeasureSpec,heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        if(changed){
            int childCount = getChildCount();
            Log.d("qhh",">>> childCount = " + childCount);
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

                Log.i("qhh",">>> i = " + i + " , childView Width = " + childView.getMeasuredWidth()
                 + " , childView Height = " + childView.getMeasuredHeight());
            }

        }

    }

    /*@Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }*/

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(mScroller.computeScrollOffset()){
            //当在View中则是移动View中的内容，如果parent是ViewGroup 则会移动整体的ViewGroup中所有的内容
            ((View) getParent()).scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    public void smoothScrollTo(int destX,int DestY){
        int scrollX = getScrollX();
        int delta = destX - scrollX;
        mScroller.startScroll(scrollX,0,delta,0,2000);
        invalidate();
    }

}
