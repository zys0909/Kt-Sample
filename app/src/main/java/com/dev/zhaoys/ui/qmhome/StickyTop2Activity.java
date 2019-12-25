package com.dev.zhaoys.ui.qmhome;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.zhaoys.R;
import com.dev.zhaoys.base.BaseActivity;
import com.dev.zhaoys.ui.qmhome.bottom.NestedBottomAreaBehavior;
import com.dev.zhaoys.ui.qmhome.bottom.NestedBottomView;
import com.dev.zhaoys.ui.qmhome.tools.BaseRecyclerAdapter;
import com.dev.zhaoys.ui.qmhome.tools.RecyclerViewHolder;
import com.dev.zhaoys.ui.qmhome.top.NestedTopAreaBehavior;
import com.dev.zhaoys.ui.qmhome.top.NestedTopDelegateLayout;
import com.dev.zhaoys.ui.qmhome.top.NestedTopRecyclerView;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StickyTop2Activity extends BaseActivity {
    private static final String TAG = "测试TAG";
    private NestedScrollLayout mCoordinatorLayout;
    private NestedTopDelegateLayout mTopDelegateLayout;
    private NestedTopRecyclerView mTopRecyclerView;
    private NestedBottomView mBottomView;
    private BaseRecyclerAdapter<String> mAdapter;

    @Override
    protected int layoutId() {
        return R.layout.activity_qm_home;
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        mCoordinatorLayout = findViewById(R.id.coordinator);
        //继承FrameLayout,内部包含一个头，一个脚，和一个RecyclerView
        mTopDelegateLayout = new NestedTopDelegateLayout(this);
        mTopDelegateLayout.setBackgroundColor(Color.LTGRAY);

       /* AppCompatTextView headerView = new AppCompatTextView(this) {
            @Override
            protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(
                        DensityUtil.dp2px(100), MeasureSpec.EXACTLY
                ));
            }
        };
        headerView.setTextSize(17);
        headerView.setBackgroundColor(Color.GRAY);
        headerView.setTextColor(Color.WHITE);
        headerView.setText("This is Top Header");
        headerView.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        mTopDelegateLayout.setHeaderView(headerView);*/

        AppCompatTextView footerView = new AppCompatTextView(this) {
            @Override
            protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(
                        DensityUtil.dp2px(100), MeasureSpec.EXACTLY
                ));
            }
        };
        footerView.setTextSize(17);
        footerView.setBackgroundColor(Color.GRAY);
        footerView.setTextColor(Color.WHITE);
        footerView.setGravity(Gravity.CENTER);
        footerView.setText("This is Top Footer");
        mTopDelegateLayout.setFooterView(footerView);

        mTopRecyclerView = new NestedTopRecyclerView(this);
        mTopRecyclerView.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
        mTopDelegateLayout.setDelegateView(mTopRecyclerView);

        int matchParent = ViewGroup.LayoutParams.MATCH_PARENT;
        CoordinatorLayout.LayoutParams topLp = new CoordinatorLayout.LayoutParams(
                matchParent, matchParent);
        topLp.setBehavior(new NestedTopAreaBehavior(this));

        mCoordinatorLayout.setTopAreaView(mTopDelegateLayout, topLp);

        //继承FrameLayout，内部包含一个头和一个ViewPager
        mBottomView = new NestedBottomView(this);
        CoordinatorLayout.LayoutParams recyclerViewLp = new CoordinatorLayout.LayoutParams(
                matchParent, matchParent);
        recyclerViewLp.setBehavior(new NestedBottomAreaBehavior());
        mCoordinatorLayout.setBottomAreaView(mBottomView, recyclerViewLp);
        mBottomView.load();

        mCoordinatorLayout.addOnScrollListener(new NestedScrollLayout.OnScrollListener() {
            @Override
            public void onScroll(int topCurrent, int topRange, int offsetCurrent,
                                 int offsetRange, int bottomCurrent, int bottomRange) {
                Log.i("abc", String.format("topCurrent = %d; topRange = %d; " +
                                "offsetCurrent = %d; offsetRange = %d; " +
                                "bottomCurrent = %d, bottomRange = %d",
                        topCurrent, topRange, offsetCurrent, offsetRange, bottomCurrent, bottomRange));
            }

            @Override
            public void onScrollStateChange(int newScrollState, boolean fromTopBehavior) {

            }
        });

        mAdapter = new BaseRecyclerAdapter<String>(this, null) {
            @Override
            public int getItemLayoutId(int viewType) {
                return android.R.layout.simple_list_item_1;
            }

            @Override
            public void bindData(RecyclerViewHolder holder, int position, String item) {
                holder.setText(android.R.id.text1, item);
            }
        };
        mAdapter.setOnItemClickListener((itemView, pos) ->
                Toast.makeText(StickyTop2Activity.this, "click position=" + pos, Toast.LENGTH_SHORT).show());
        mTopRecyclerView.setAdapter(mAdapter);
        mCoordinatorLayout.setDraggableScrollBarEnabled(true);
        onDataLoaded();
    }

    private void onDataLoaded() {
        List<String> data = new ArrayList<>(Arrays.asList("Helps", "Maintain", "Liver",
                "Health", "Function", "Supports", "Healthy", "Fat", "Metabolism", "Nuturally",
                "Bracket", "Refrigerator", "Bathtub", "Wardrobe", "Comb", "Apron", "Carpet",
                "Bolster", "Pillow", "Cushion"));
        mAdapter.setData(data);
    }
}
