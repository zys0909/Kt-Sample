package com.dev.zhaoys.widget.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.OverScroller;
import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import com.dev.zhaoys.R;
import com.zys.core.App;

import java.lang.ref.WeakReference;

/**
 * Created by ylhu on 17-2-23.
 */
@SuppressWarnings("unused")
public class UCViewHeaderBehaviorNormal extends ViewOffsetBehavior<View> {
    private int mTitleViewHeight = 0;
    private final OverScroller mOverScroller;
    private WeakReference<View> mChild;

    private static final int STATE_OPENED = 0;
    private static final int STATE_CLOSED = 1;
    private static final int DURATION_SHORT = 300;
    private static final int DURATION_LONG = 600;

    private int mCurState = STATE_OPENED;

    public UCViewHeaderBehaviorNormal() {

        super();
        mOverScroller = new OverScroller(App.instance());
    }

    public UCViewHeaderBehaviorNormal(Context context, AttributeSet attrs) {

        super(context, attrs);
        mOverScroller = new OverScroller(context);
    }

    @Override
    protected void layoutChild(CoordinatorLayout parent, View child, int layoutDirection) {

        super.layoutChild(parent, child, layoutDirection);
        mTitleViewHeight = parent.findViewById(R.id.id_uc_view_title_layout).getMeasuredHeight();
        mChild = new WeakReference<>(child);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild,
                                       @NonNull View target, int nestedScrollAxes, int type) {
        LogU.d(this, child, directTargetChild, target);
        //开始滑动的条件，垂直方向滑动，滑动未结束
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL && canScroll(child, 0) && !isClosed(child);
    }

    /**
     * 当前是否可以滑动
     *
     * @param pendingDy Y轴方向滑动的translationY
     */
    private boolean canScroll(View child, float pendingDy) {

        int pendingTranslationY = (int) (child.getTranslationY() - pendingDy);
        return pendingTranslationY >= getHeaderOffsetRange() && pendingTranslationY <= 0;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target,
                                  int dx, int dy, @NonNull int[] consumed, int type) {

        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        //开始滑动之前的逻辑处理
//        LogU.d(this, child, target);
        //dy>0 向上滑
        //dy<0 向下滑
        float halfOfDis = dy / 4.0f;
        if (!canScroll(child, halfOfDis)) {//滑动结束
            if (halfOfDis > 0) {
                child.setVisibility(View.GONE);//滑动结束后，隐藏此视图
                child.setTranslationY(getHeaderOffsetRange());
            } else {
                child.setTranslationY(0);
            }
        } else {//滑动未结束
            if (halfOfDis <= 0) {
                child.setVisibility(View.VISIBLE);
            }
            //滑动
            child.setTranslationY(child.getTranslationY() - halfOfDis);
        }
        //消耗掉当前垂直方向上的滑动距离
        consumed[1] = dy;
    }

    @Override
    public boolean onNestedPreFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, float velocityX, float velocityY) {
        // consumed the flinging behavior until Closed
        return !isClosed(child);
    }

    @Override
    public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int type) {
        mCurState = child.getTranslationY() < 0 ? STATE_CLOSED : STATE_OPENED;
    }

    /**
     * 向上滑动过程时translationY的最小值
     */
    private int getHeaderOffsetRange() {
        return -mTitleViewHeight;
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_UP) {
            //对松开手指时进行处理，如果松开时滑动滑动了1/4则自动滑动到结束，否则则回归原位
            handlerActionUp(child);
        }
        return super.onInterceptTouchEvent(parent, child, ev);
    }

    private void handlerActionUp(View child) {
        if (mFlingRunnable != null) {
            child.removeCallbacks(mFlingRunnable);
            mFlingRunnable = null;
        }
        mFlingRunnable = new FlingRunnable(child, mOverScroller);
        if (child.getTranslationY() < getHeaderOffsetRange() / 4.0f) {
            scrollToClosed(child, DURATION_SHORT);
        } else if (child.getTranslationY() > getHeaderOffsetRange() / 4.0f * 3) {
            scrollToOpen(child, DURATION_SHORT);
        }
    }

    private void onFlingFinished(View layout) {

        boolean isClosed = isClosed(layout);
        mCurState = isClosed ? STATE_CLOSED : STATE_OPENED;
        if (isClosed) {
            layout.setVisibility(View.GONE);
        }
    }

    /**
     * 是否滑动结束
     */
    private boolean isClosed(View child) {
        return child.getTranslationY() <= getHeaderOffsetRange();
    }

    public boolean isClosed() {
        return mCurState == STATE_CLOSED;
    }

    public void openPager() {
        openPager(DURATION_LONG);
    }

    /**
     * @param duration open animation duration
     */
    public void openPager(int duration) {
        View child = mChild.get();
        if (isClosed() && child != null) {
            if (child.getVisibility() == View.GONE) {
                child.setVisibility(View.VISIBLE);
            }
            if (mFlingRunnable != null) {
                child.removeCallbacks(mFlingRunnable);
                mFlingRunnable = null;
            }
            LogU.d(this,child);
            mFlingRunnable = new FlingRunnable(child, mOverScroller);
            scrollToOpen(child, duration);
        }
    }

    public void closePager() {
        closePager(DURATION_LONG);
    }

    /**
     * @param duration close animation duration
     */
    public void closePager(int duration) {
        View child = mChild.get();
        if (!isClosed()) {
            if (mFlingRunnable != null) {
                child.removeCallbacks(mFlingRunnable);
                mFlingRunnable = null;
            }
            mFlingRunnable = new FlingRunnable(child, mOverScroller);
            scrollToClosed(child, duration);
        }
    }


    private FlingRunnable mFlingRunnable;

    private static class FlingRunnable implements Runnable {
        private final View mLayout;
        private final OverScroller mOverScroller;

        FlingRunnable(View layout, OverScroller overScroller) {
            mLayout = layout;
            mOverScroller = overScroller;

        }

        @Override
        public void run() {
            if (mLayout != null && mOverScroller != null) {
                if (mOverScroller.computeScrollOffset()) {
                    mLayout.setTranslationY(mOverScroller.getCurrY());
                    ViewCompat.postOnAnimation(mLayout, this);
                }
            }
        }
    }

    private void scrollToClosed(View view, int duration) {
        float curTranslationY = view.getTranslationY();
        float dy = getHeaderOffsetRange() - curTranslationY;
        mOverScroller.startScroll(0, Math.round(curTranslationY - 0.1f), 0, Math.round(dy + 0.1f), duration);
        start(view);
    }

    private void scrollToOpen(View view, int duration) {
        float curTranslationY = view.getTranslationY();
        mOverScroller.startScroll(0, (int) curTranslationY, 0, (int) -curTranslationY, duration);
        start(view);
    }

    private void start(View view) {
        if (mOverScroller.computeScrollOffset()) {
            mFlingRunnable = new FlingRunnable(view, mOverScroller);
            ViewCompat.postOnAnimation(view, mFlingRunnable);
        } else {
            onFlingFinished(view);
        }
    }
}
