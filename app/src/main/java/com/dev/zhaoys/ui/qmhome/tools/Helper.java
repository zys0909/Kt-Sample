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


import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Interpolator;

public class Helper {
    public static final Interpolator QUNITIC_INTERPOLATOR = new Interpolator() {
        @Override
        public float getInterpolation(float t) {
            t -= 1.0f;
            return t * t * t * t * t + 1.0f;
        }
    };

    public static int constrain(int amount, int low, int high) {
        return amount < low ? low : (amount > high ? high : amount);
    }
    public static float constrain(float amount, float low, float high) {
        return amount < low ? low : (amount > high ? high : amount);
    }
    /**
     * Retrieve the transformed bounding rect of an arbitrary descendant view.
     * This does not need to be a direct child.
     *
     * @param descendant descendant view to reference
     * @param out        rect to set to the bounds of the descendant view
     */
    public static void getDescendantRect(ViewGroup parent, View descendant, Rect out) {
        out.set(0, 0, descendant.getWidth(), descendant.getHeight());
        ViewGroupHelper.offsetDescendantRect(parent, descendant, out);
    }


    private static class ViewGroupHelper {
        private static final ThreadLocal<Matrix> sMatrix = new ThreadLocal<>();
        private static final ThreadLocal<RectF> sRectF = new ThreadLocal<>();

        public static void offsetDescendantRect(ViewGroup group, View child, Rect rect) {
            Matrix m = sMatrix.get();
            if (m == null) {
                m = new Matrix();
                sMatrix.set(m);
            } else {
                m.reset();
            }

            offsetDescendantMatrix(group, child, m);

            RectF rectF = sRectF.get();
            if (rectF == null) {
                rectF = new RectF();
                sRectF.set(rectF);
            }
            rectF.set(rect);
            m.mapRect(rectF);
            rect.set((int) (rectF.left + 0.5f), (int) (rectF.top + 0.5f),
                    (int) (rectF.right + 0.5f), (int) (rectF.bottom + 0.5f));
        }

        static void offsetDescendantMatrix(ViewParent target, View view, Matrix m) {
            final ViewParent parent = view.getParent();
            if (parent instanceof View && parent != target) {
                final View vp = (View) parent;
                offsetDescendantMatrix(target, vp, m);
                m.preTranslate(-vp.getScrollX(), -vp.getScrollY());
            }

            m.preTranslate(view.getLeft(), view.getTop());

            if (!view.getMatrix().isIdentity()) {
                m.preConcat(view.getMatrix());
            }
        }
    }
}
