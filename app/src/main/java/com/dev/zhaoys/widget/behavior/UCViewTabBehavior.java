package com.dev.zhaoys.widget.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.dev.zhaoys.R;

import java.util.List;

/**
 * Created by ylhu on 17-2-23.
 */
@SuppressWarnings("unused")
public class UCViewTabBehavior extends HeaderScrollingViewBehavior {
    private int mTitleViewHeight = 0;

    public UCViewTabBehavior() {

        super();
    }

    public UCViewTabBehavior(Context context, AttributeSet attrs) {

        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {

        return isDependency(dependency);
    }

    @Override
    protected void layoutChild(CoordinatorLayout parent, View child, int layoutDirection) {

        mTitleViewHeight = parent.findViewById(R.id.id_uc_view_title_layout).getMeasuredHeight();
        super.layoutChild(parent, child, layoutDirection);
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {

        //UCViewTab要滑动的距离为Header的高度减去TitleView的高度
        float offsetRange = mTitleViewHeight - dependency.getMeasuredHeight();
        //当Header向上滑动mTitleViewHeight高度后，即滑动完成
        int headerOffsetRange = -mTitleViewHeight;

        if (dependency.getTranslationY() == headerOffsetRange) {//Header已经上滑结束
            child.setTranslationY(offsetRange);
        } else if (dependency.getTranslationY() == 0) {//下滑结束，也是初始化的状态
            child.setTranslationY(0);
        } else {
            //UCViewTab与UCViewHeader为同向滑动
            //根据依赖UCViewHeader的滑动比例计算当前UCViewTab应该要滑动的值translationY
            child.setTranslationY(dependency.getTranslationY() / (headerOffsetRange * 1.0f) * offsetRange);
        }
        return false;
    }

    private boolean isDependency(View dependency) {

        return dependency != null && dependency.getId() == R.id.id_uc_view_header_layout;
    }

    @Override
    public View findFirstDependency(List<View> views) {

        for (int i = 0; i < views.size(); i++) {
            if (isDependency(views.get(i))) {
                return views.get(i);
            }
        }
        return null;
    }
}
