/*
 * Tencent is pleased to support the open source community by making QMUI_Android available.
 *
 * Copyright (C) 2017-2018 THL A29 Limited, a Tencent company. All rights reserved.
 *
 * Licensed under the MIT License (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dev.zhaoys.ui.qmhome.bottom;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.dev.zhaoys.R;
import com.dev.zhaoys.ui.qmhome.tools.BaseRecyclerAdapter;
import com.dev.zhaoys.ui.qmhome.tools.BottomPagerAdapter;
import com.dev.zhaoys.ui.qmhome.tools.HomeViewPager;
import com.dev.zhaoys.ui.qmhome.tools.OnItemClickCallback;
import com.dev.zhaoys.ui.qmhome.tools.RecyclerViewHolder;
import com.group.dev.ext.DensityUtil;

import java.util.ArrayList;
import java.util.List;

public class NestedBottomView extends NestedBottomDelegateLayout {

    private HomeViewPager mViewPager;
    private RecyclerView headerView;
    private BaseRecyclerAdapter<String> adapter;

    public NestedBottomView(Context context) {
        super(context);
    }

    public NestedBottomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NestedBottomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void load() {
        List<String> list = new ArrayList<>();
        list.add("Header1");
        list.add("Header2");
        list.add("Header3");
        list.add("Header4");
        list.add("Header5");
        adapter.setData(list);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                changePosition(position);
            }
        });

        BottomPagerAdapter pagerAdapter = new BottomPagerAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

        };
        mViewPager.setAdapter(pagerAdapter);
        changePosition(0);
    }

    private void changePosition(int pos) {
        adapter.setSelectPos(pos);
        if (mViewPager.getCurrentItem() != pos) {
            mViewPager.setCurrentItem(pos);
        }
    }

    @NonNull
    @Override
    protected View onCreateHeaderView() {
        headerView = new RecyclerView(getContext());

        headerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });

        adapter = new BaseRecyclerAdapter<String>(getContext(), null) {
            private int selectPos = -1;

            @Override
            public int getItemLayoutId(int viewType) {
                return R.layout.simple_item_text_1;
            }

            @Override
            public void bindData(RecyclerViewHolder holder, int position, String item) {
                holder.setText(R.id.text1, item);
                holder.setBackground(R.id.text1, selectPos == position ? Color.RED : Color.GRAY);
            }

            @Override
            public void setSelectPos(int pos) {
                if (selectPos != pos) {
                    selectPos = pos;
                    notifyDataSetChanged();
                }
            }
        };
        adapter.setOnItemClickListener(new OnItemClickCallback() {
            @Override
            public void onItemClick(View itemView, int pos) {
                changePosition(pos);
            }
        });
        headerView.setAdapter(adapter);
        return headerView;
    }

    @Override
    protected int getHeaderHeightLayoutParam() {
        return DensityUtil.dp2px(50);
    }

    @Override
    protected int getHeaderStickyHeight() {
        return getHeaderHeightLayoutParam();
    }


    @NonNull
    @Override
    protected View onCreateContentView() {
        mViewPager = new HomeViewPager(getContext());
        return mViewPager;
    }


}