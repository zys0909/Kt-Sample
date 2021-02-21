package com.group.dev.widget.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.group.dev.R;

/**
 * Created by ylhu on 17-2-23.
 */
@SuppressWarnings("unused")
public class UCViewTitleBehavior extends ViewOffsetBehavior<View> {

    public UCViewTitleBehavior() {
        super();
    }

    public UCViewTitleBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        //因为UCViewTitle默认是在屏幕外不可见，所以在UCViewTitle进行布局的时候设置其topMargin让其不可见
        ((CoordinatorLayout.LayoutParams) child.getLayoutParams()).topMargin = -child.getMeasuredHeight();
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        return isDependOn(dependency);
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        //因为UCViewTitle与UCViewHeader的滑动方向相反
        //所以当依赖UCViewHeader发生变化时，只需要时设置反向的translationY即可
        child.setTranslationY(-dependency.getTranslationY());
        return false;
    }

    private boolean isDependOn(View dependency) {
        return dependency != null && dependency.getId() == R.id.id_uc_view_header_layout;
    }
}
