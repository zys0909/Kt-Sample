
package com.dev.zhaoys.ui.sticky;

import android.graphics.Canvas;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;

/**
 * 描述:
 * <p>
 * author zys
 * create by 2019-10-08 11:29
 */
@SuppressWarnings("unused")
public class StickyItemDecoration<VH extends RecyclerView.ViewHolder, Adapter extends RecyclerView.Adapter<VH>>
        extends RecyclerView.ItemDecoration {

    private int mStickyItemViewType;
    private Adapter mAdapter;
    private VH mStickyHeaderViewHolder;
    private int mStickyHeaderViewPosition = RecyclerView.NO_POSITION;
    private WeakReference<ViewGroup> mWeakSectionContainer;
    private int mTargetTop = 0;

    public StickyItemDecoration(ViewGroup sectionContainer, @NonNull Adapter adapter, int stickyItemViewType) {
        mAdapter = adapter;
        mStickyItemViewType = stickyItemViewType;
        mWeakSectionContainer = new WeakReference<>(sectionContainer);

        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                // stickyViewHolder should update when the adapter updates relative view holder
                super.onItemRangeChanged(positionStart, itemCount);
                if (mStickyHeaderViewPosition >= positionStart
                        && mStickyHeaderViewPosition < positionStart + itemCount
                        && mStickyHeaderViewHolder != null
                        && mWeakSectionContainer.get() != null) {
                    bindStickyViewHolder(mWeakSectionContainer.get(), mStickyHeaderViewHolder, mStickyHeaderViewPosition);
                }
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                if (mStickyHeaderViewPosition >= positionStart
                        && mStickyHeaderViewPosition < positionStart + itemCount) {
                    mStickyHeaderViewPosition = RecyclerView.NO_POSITION;
                    setHeaderVisibility(false);
                }
            }
        });
    }

    private void setHeaderVisibility(boolean visibility) {
        ViewGroup sectionContainer = mWeakSectionContainer.get();
        if (sectionContainer == null) {
            return;
        }
        sectionContainer.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent,
                           @NonNull RecyclerView.State state) {


        ViewGroup sectionContainer = mWeakSectionContainer.get();
        if (sectionContainer == null || parent.getChildCount() == 0) {
            return;
        }

        RecyclerView.Adapter adapter = parent.getAdapter();
        if (adapter == null) {
            return;
        }

        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (!(layoutManager instanceof LinearLayoutManager)) {
            setHeaderVisibility(false);
            return;
        }
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
        int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

        if (firstVisibleItemPosition == RecyclerView.NO_POSITION) {
            setHeaderVisibility(false);
            return;
        }

        int headerPos = getRelativeStickyPosition(firstVisibleItemPosition);
        if (headerPos == RecyclerView.NO_POSITION) {
            setHeaderVisibility(false);
            return;
        }
        int itemType = mAdapter.getItemViewType(headerPos);
        if (itemType == -1) {
            setHeaderVisibility(false);
            return;
        }
        if (mStickyHeaderViewHolder == null || mStickyHeaderViewHolder.getItemViewType() != itemType) {
            mStickyHeaderViewHolder = createStickyViewHolder(parent, headerPos, itemType);
        }

        if (mStickyHeaderViewPosition != headerPos) {
            mStickyHeaderViewPosition = headerPos;
            bindStickyViewHolder(sectionContainer, mStickyHeaderViewHolder, headerPos);
        }

        setHeaderVisibility(true);

        int contactPoint = sectionContainer.getHeight() - 1;
        final View childInContact = parent.findChildViewUnder(parent.getWidth() / 2, contactPoint);
        if (childInContact == null) {
            mTargetTop = parent.getTop();
            ViewCompat.offsetTopAndBottom(sectionContainer, mTargetTop - sectionContainer.getTop());
            return;
        }

        if (isHeaderItem(parent.getChildAdapterPosition(childInContact))) {
            mTargetTop = childInContact.getTop() + parent.getTop() - sectionContainer.getHeight();
            ViewCompat.offsetTopAndBottom(sectionContainer, mTargetTop - sectionContainer.getTop());
            return;
        }

        mTargetTop = parent.getTop();
        ViewCompat.offsetTopAndBottom(sectionContainer, mTargetTop - sectionContainer.getTop());
    }

    private int getRelativeStickyPosition(int pos) {
        int position = pos;
        while (mAdapter.getItemViewType(position) != mStickyItemViewType) {
            position--;
            if (position < 0) {
                return RecyclerView.NO_POSITION;
            }
        }
        return position;
    }

    private boolean isHeaderItem(int pos) {
        return mAdapter.getItemViewType(pos) == mStickyItemViewType;
    }

    public int getTargetTop() {
        return mTargetTop;
    }


    private VH createStickyViewHolder(RecyclerView recyclerView, int position, int itemType) {
        return mAdapter.createViewHolder(recyclerView, itemType);
    }

    private void bindStickyViewHolder(ViewGroup sectionContainer, VH viewHolder, int position) {
        mAdapter.bindViewHolder(viewHolder, position);
        sectionContainer.removeAllViews();
        sectionContainer.addView(viewHolder.itemView);
    }
}
