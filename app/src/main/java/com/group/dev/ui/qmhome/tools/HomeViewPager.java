package com.group.dev.ui.qmhome.tools;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.group.dev.ui.qmhome.bottom.NestedBottomRecyclerView;

/**
 * 描述:
 * <p>
 * author zys
 * create by 2019-12-04
 */
public class HomeViewPager extends ViewPager implements INestedBottomView {
    static final String KEY_CURRENT_POSITION = "demo_bottom_current_position";

    public HomeViewPager(Context context) {
        super(context);
    }


    private NestedBottomRecyclerView getCurrentItemView() {
        PagerAdapter adapter = getAdapter();
        if (adapter instanceof BottomPagerAdapter) {
            return ((BottomPagerAdapter) adapter).getCurrentItemView();
        }
        return null;
    }

    public int getCurrentPosition() {
        PagerAdapter adapter = getAdapter();
        if (adapter instanceof BottomPagerAdapter) {
            return ((BottomPagerAdapter) adapter).getCurrentPosition();
        }
        return -1;
    }

    public INestedScrollCommon.OnScrollNotifier getOnScrollNotifier() {
        PagerAdapter adapter = getAdapter();
        if (adapter instanceof BottomPagerAdapter) {
            return ((BottomPagerAdapter) adapter).getOnScrollNotifier();
        }
        return null;
    }

    public void setOnScrollNotifier(INestedScrollCommon.OnScrollNotifier onScrollNotifier) {
        PagerAdapter adapter = getAdapter();
        if (adapter instanceof BottomPagerAdapter) {
            ((BottomPagerAdapter) adapter).setOnScrollNotifier(onScrollNotifier);
        }
    }

    @Override
    public void consumeScroll(int dyUnconsumed) {

        if (getCurrentItemView() != null) {
            getCurrentItemView().consumeScroll(dyUnconsumed);
        }

    }

    @Override
    public void smoothScrollYBy(int dy, int duration) {
        if (getCurrentItemView() != null) {
            getCurrentItemView().smoothScrollYBy(dy, duration);
        }
    }

    @Override
    public void stopScroll() {
        if (getCurrentItemView() != null) {
            getCurrentItemView().stopScroll();
        }
    }

    @Override
    public int getContentHeight() {
        if (getCurrentItemView() != null) {
            return getCurrentItemView().getContentHeight();
        }
        return 0;
    }

    @Override
    public int getCurrentScroll() {
        if (getCurrentItemView() != null) {
            return getCurrentItemView().getCurrentScroll();
        }
        return 0;
    }

    @Override
    public int getScrollOffsetRange() {
        if (getCurrentItemView() != null) {
            return getCurrentItemView().getScrollOffsetRange();
        }
        return getHeight();
    }

    @Override
    public void injectScrollNotifier(OnScrollNotifier notifier) {
        setOnScrollNotifier(notifier);
        if (getCurrentItemView() != null) {
            getCurrentItemView().injectScrollNotifier(notifier);
        }
    }

    @Override
    public void saveScrollInfo(@NonNull Bundle bundle) {
        bundle.putInt(KEY_CURRENT_POSITION, getCurrentPosition());
        if (getCurrentItemView() != null) {
            getCurrentItemView().saveScrollInfo(bundle);
        }
    }

    @Override
    public void restoreScrollInfo(@NonNull Bundle bundle) {
        if (getCurrentItemView() != null) {
            int currentPos = bundle.getInt(KEY_CURRENT_POSITION, -1);
            if (currentPos == getCurrentPosition()) {
                getCurrentItemView().restoreScrollInfo(bundle);
            }
        }
    }
}
