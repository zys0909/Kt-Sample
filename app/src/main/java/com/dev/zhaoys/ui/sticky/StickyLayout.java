package com.dev.zhaoys.ui.sticky;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;


public class StickyLayout extends SmartRefreshLayout implements ViewCallback {

    private RecyclerView mRecyclerView;
    private StickyItemDecoration mStickyItemDecoration;
    private FrameLayout mStickyWrapView;
    private int mStickyViewHeight = -1;
    /**
     * if scrollToPosition happened before mStickyWrapView finished layout,
     * the target item may be covered by mStickyWrapView, so we delay to
     * execute the scroll action
     */
    private Runnable mPendingScrollAction = null;

    public StickyLayout(Context context) {
        this(context, null);
    }

    public StickyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mStickyWrapView = new FrameLayout(context);
        mRecyclerView = new RecyclerView(context);
        mRecyclerView.setOverScrollMode(OVER_SCROLL_NEVER);

        addView(mRecyclerView, new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(mStickyWrapView, new FrameLayout.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mStickyWrapView.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
            mStickyViewHeight = bottom - top;
            if (mStickyViewHeight > 0 && mPendingScrollAction != null) {
                mPendingScrollAction.run();
                mPendingScrollAction = null;
            }
        });
    }

    public FrameLayout getStickySectionWrapView() {
        return mStickyWrapView;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public void setLayoutManager(@NonNull RecyclerView.LayoutManager layoutManager) {
        mRecyclerView.setLayoutManager(layoutManager);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }

    public void addItemDecoration(StickyItemDecoration decor) {
        mStickyItemDecoration = decor;
        mRecyclerView.addItemDecoration(decor);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mStickyItemDecoration != null) {
            mStickyWrapView.layout(mStickyWrapView.getLeft(),
                    mStickyItemDecoration.getTargetTop(),
                    mStickyWrapView.getRight(),
                    mStickyItemDecoration.getTargetTop() + mStickyWrapView.getHeight());
        }
    }

    @Override
    public void scrollToPosition(final int position, boolean isSectionHeader, final boolean scrollToTop) {
        mPendingScrollAction = null;
        RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
        if (adapter == null || position < 0 || position >= adapter.getItemCount()) {
            return;
        }
        RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            int firstVPos = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
            int lastVPos = linearLayoutManager.findLastCompletelyVisibleItemPosition();
            int offset = 0;
            if (!isSectionHeader) {
                if (mStickyViewHeight <= 0) {
                    // delay to re scroll
                    mPendingScrollAction = new Runnable() {
                        @Override
                        public void run() {
                            scrollToPosition(position, false, scrollToTop);
                        }
                    };
                }
                offset = mStickyWrapView.getHeight();
            }
            if (position < firstVPos + 1 /* increase one to avoid being covered */ || position > lastVPos || scrollToTop) {
                linearLayoutManager.scrollToPositionWithOffset(position, offset);
            }
        } else {
            mRecyclerView.scrollToPosition(position);
        }
    }

    @Nullable
    @Override
    public RecyclerView.ViewHolder findViewHolderForAdapterPosition(int position) {
        return mRecyclerView.findViewHolderForAdapterPosition(position);
    }

    @Override
    public void requestChildFocus(View view) {
        mRecyclerView.requestChildFocus(view, null);
    }
}
