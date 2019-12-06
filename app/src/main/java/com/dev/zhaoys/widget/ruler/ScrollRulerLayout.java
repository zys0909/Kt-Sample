package com.dev.zhaoys.widget.ruler;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dev.zhaoys.R;

/**
 * 描述:RulerView的载体.
 * <p>
 * author zys
 * create by 2019-12-01
 */
public class ScrollRulerLayout extends ViewGroup {

    private RulerView mRulerView;
    private ImageView mCenterPointer;
    private Paint mLinePaint;
    private Paint mPointerPaint;
    private int mLineWidth;
    private int mPadding;
    private int mPointerColor;//当前刻度标识
    private int mRulerTextColor;//文本颜色
    private int mRulerColor;//刻度颜色
    private int mStrokeColor;//边框颜色
    private Drawable mPointerDrawable;

    public ScrollRulerLayout(Context context) {
        this(context, null);
    }

    public ScrollRulerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollRulerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ScrollRulerLayout, 0, 0);
        //指针图片与指针颜色二者设置其一即可
        mPointerDrawable = typedArray.getDrawable(R.styleable.ScrollRulerLayout_pointer);
        mPointerColor = typedArray.getColor(R.styleable.ScrollRulerLayout_pointer_color,Color.RED);
        mRulerColor = typedArray.getColor(R.styleable.ScrollRulerLayout_ruler_color,Color.GRAY);
        mRulerTextColor = typedArray.getColor(R.styleable.ScrollRulerLayout_ruler_text_color,Color.BLACK);
        mStrokeColor = typedArray.getColor(R.styleable.ScrollRulerLayout_stoker_color,Color.GRAY);
        typedArray.recycle();
        initPaint();
    }

    private void initPaint() {
        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLineWidth = dp2px(1);
        mLinePaint.setStrokeWidth(mLineWidth);
        mLinePaint.setStrokeWidth(mLineWidth);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setColor(mStrokeColor);
        mPadding = dp2px(10);

        mPointerPaint = new Paint();
        mPointerPaint.setStyle(Paint.Style.FILL);
        mPointerPaint.setColor(mPointerColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (null != mRulerView) {
            mRulerView.measure(widthMeasureSpec, heightMeasureSpec);
        }
        if (null != mCenterPointer) {
            LayoutParams layoutParams = mCenterPointer.getLayoutParams();
            mCenterPointer.measure(layoutParams.width, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (null != mRulerView) {
            MarginLayoutParams lp = (MarginLayoutParams) mRulerView.getLayoutParams();
            int left = getPaddingLeft();
            int top = getPaddingTop();
            int right = getPaddingRight() + getMeasuredWidth();
            int bottom = getPaddingBottom() + getMeasuredHeight() - mLineWidth;
            mRulerView.layout(left, top, right, bottom);
        }
        if (null != mCenterPointer) {
            int measuredWidth = mCenterPointer.getMeasuredWidth();
            int width = getWidth() + getPaddingLeft() + getPaddingRight();
            int left = width / 2 - measuredWidth / 2;
            int right = width / 2 + measuredWidth / 2;
            mCenterPointer.layout(left, 0, right, getHeight());
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        canvas.drawRect(0, 0, getWidth(), getHeight(), mLinePaint);
        if (mPointerDrawable == null) {
            int centerX = getWidth() / 2;
            canvas.drawRect(centerX - dp2px(2), 0, centerX + dp2px(2), dp2px(16), mPointerPaint);
            canvas.drawRect(centerX - dp2px(2), getHeight() - dp2px(16), centerX + dp2px(2), getHeight(), mPointerPaint);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mRulerView = new RulerView(getContext());
        mRulerView.setRulerColor(mRulerColor);
        mRulerView.setRulerTextColor(mRulerTextColor);

        mCenterPointer = new ImageView(getContext());
        mCenterPointer.setImageDrawable(mPointerDrawable);
        MarginLayoutParams layoutParams = new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layoutParams.leftMargin = mPadding;
        layoutParams.rightMargin = mPadding;
        mRulerView.setLayoutParams(layoutParams);
        layoutParams.width = dp2px(12);
        layoutParams.height = LayoutParams.MATCH_PARENT;
        mCenterPointer.setLayoutParams(layoutParams);
        addView(mRulerView);
        addView(mCenterPointer);
    }

    public void setScope(int start, int end, int offSet, int minTipRuler) {
        if (null != mRulerView) {
            mRulerView.setScope(start, end, offSet, minTipRuler);
        }
    }

    public void setRulerViewMargin(int left, int top, int right, int bottom) {
        MarginLayoutParams layoutParams = (MarginLayoutParams) mRulerView.getLayoutParams();
        setRulerViewMargin(layoutParams, left, top, right, bottom);
    }

    public void setCurrentItem(String text) {
        if (null != mRulerView) {
            mRulerView.setCurrentItem(text);
        }
    }

    public void setRulerViewMargin(MarginLayoutParams layoutParams, int left, int top, int right, int bottom) {
        layoutParams.leftMargin = left;
        layoutParams.topMargin = top;
        layoutParams.rightMargin = right;
        layoutParams.bottomMargin = bottom;
        mRulerView.setLayoutParams(layoutParams);
    }

    @Override
    protected void onDetachedFromWindow() {
        mRulerView.destroy();
        mRulerView = null;
        mCenterPointer = null;
        super.onDetachedFromWindow();
    }

    private int dp2px(float dp) {
        return (int) (getContext().getResources().getDisplayMetrics().density * dp + 0.5f);
    }

    public void setScrollSelected(ScrollSelected scrollSelected) {
        mRulerView.setScrollSelected(scrollSelected);
    }
}
