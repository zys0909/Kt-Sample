package com.group.dev.widget.behavior;

import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;

/**
 * 描述:
 * <p>
 * author zhaoys
 * create by 2019/7/31 0031
 */
class LogU {
    private LogU() {
    }

    static void d(Object object, @NonNull View... views) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0, size = views.length; i < size; i++) {
            builder.append("\nview")
                    .append(i).append(" = ")
                    .append(views[i].getClass().getSimpleName());
        }
        Log.d("测试TAG", "\n" +
                "class = " + object.getClass().getSimpleName() + builder.toString());
    }
}
