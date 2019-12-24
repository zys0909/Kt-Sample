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

package com.dev.zhaoys.ui.qmhome.tools;

import android.graphics.Color;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.dev.zhaoys.R;
import com.dev.zhaoys.ui.qmhome.bottom.NestedBottomRecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author cginechen
 * @date 2017-09-13
 */

public class BottomPagerAdapter extends PagerAdapter {
    private SparseArray<Object> mScrapItems = new SparseArray<>();
    private NestedBottomRecyclerView mCurrentItemView;
    private INestedScrollCommon.OnScrollNotifier mOnScrollNotifier;
    private int mCurrentPosition;

    public BottomPagerAdapter() {
    }

    public NestedBottomRecyclerView getCurrentItemView() {
        return mCurrentItemView;
    }

    public int getCurrentPosition() {
        return mCurrentPosition;
    }

    @Override
    public int getCount() {
        return 0;
    }

    public INestedScrollCommon.OnScrollNotifier getOnScrollNotifier() {
        return mOnScrollNotifier;
    }

    public void setOnScrollNotifier(INestedScrollCommon.OnScrollNotifier mOnScrollNotifier) {
        this.mOnScrollNotifier = mOnScrollNotifier;
    }

    /**
     * Hydrating an object is taking an object that exists in memory,
     * that doesn't yet contain any domain data ("real" data),
     * and then populating it with domain data.
     */
    protected Object hydrate(ViewGroup container, int position) {
        NestedBottomRecyclerView recyclerView = new NestedBottomRecyclerView(container.getContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
            }
        });

        BaseRecyclerAdapter<String> adapter = new BaseRecyclerAdapter<String>(container.getContext(), null) {
            @Override
            public int getItemLayoutId(int viewType) {
                return R.layout.simple_item_text_1;
            }

            @Override
            public void bindData(RecyclerViewHolder holder, int position, String item) {
                holder.setText(R.id.text1, item);
                holder.setBackground(R.id.text1, Color.WHITE);
            }
        };
        adapter.setOnItemClickListener(new OnItemClickCallback() {
            @Override
            public void onItemClick(View itemView, int pos) {
                Toast.makeText(container.getContext(), "click position=" + pos, Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);
        onDataLoaded(adapter, position);
        return recyclerView;
    }

    private void onDataLoaded(BaseRecyclerAdapter<String> adapter, int position) {
        List<String> data1 = new ArrayList<>(Arrays.asList("Helps", "Maintain", "Liver", "Health",
                "Function", "Supports", "Healthy", "Fat", "Metabolism", "Nuturally", "Bracket",
                "Refrigerator", "Bathtub", "Wardrobe", "Comb", "Apron", "Carpet", "Bolster",
                "Pillow", "Cushion"));
        List<String> data2 = new ArrayList<>(Arrays.asList("Helps", "Maintain", "Liver"));
        List<String> data = position % 2 == 0 ? data1 : data2;
        Collections.shuffle(data);
        adapter.setData(data);
    }

    protected void populate(ViewGroup container, Object item, int position) {
        container.addView((View) item);
    }

    @Override
    @NonNull
    public final Object instantiateItem(@NonNull ViewGroup container, int position) {
        Object item = mScrapItems.get(position);
        if (item == null) {
            item = hydrate(container, position);
            mScrapItems.put(position, item);
        }
        populate(container, item, position);
        return item;
    }

    @Override
    public final void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);

    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.setPrimaryItem(container, position, object);
        mCurrentItemView = (NestedBottomRecyclerView) object;
        mCurrentPosition = position;
        if (mOnScrollNotifier != null) {
            mCurrentItemView.injectScrollNotifier(mOnScrollNotifier);
        }
    }

    /**
     * sometimes you may need to perform some operations on all items,
     * such as perform cleanup when the ViewPager is destroyed
     * once the action return true, then do not handle remain items
     */
    public void each(@NonNull Action action) {
        int size = mScrapItems.size();
        for (int i = 0; i < size; i++) {
            Object item = mScrapItems.valueAt(i);
            if (action.call(item)) {
                break;
            }
        }
    }

    public interface Action {
        /**
         * @return true to intercept forEach
         */
        boolean call(Object item);
    }
}
