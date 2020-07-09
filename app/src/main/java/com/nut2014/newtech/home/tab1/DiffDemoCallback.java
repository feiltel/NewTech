package com.nut2014.newtech.home.tab1;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class DiffDemoCallback extends DiffUtil.ItemCallback<Tab1ListItemBean> {

    /**
     * 判断是否是同一个item
     *
     * @param oldItem New data
     * @param newItem old Data
     * @return
     */
    @Override
    public boolean areItemsTheSame(@NonNull Tab1ListItemBean oldItem, @NonNull Tab1ListItemBean newItem) {
        return oldItem.getId() == newItem.getId() && oldItem.getName().equals(newItem.getName());
    }

    /**
     * 当是同一个item时，再判断内容是否发生改变
     *
     * @param oldItem New data
     * @param newItem old Data
     * @return
     */
    @Override
    public boolean areContentsTheSame(@NonNull Tab1ListItemBean oldItem, @NonNull Tab1ListItemBean newItem) {
        return oldItem.getName().equals(newItem.getName());
    }

    /**
     * 可选实现
     * 如果需要精确修改某一个view中的内容，请实现此方法。
     * 如果不实现此方法，或者返回null，将会直接刷新整个item。
     *
     * @param oldItem Old data
     * @param newItem New data
     * @return Payload info. if return null, the entire item will be refreshed.
     */
    @Override
    public Object getChangePayload(@NonNull Tab1ListItemBean oldItem, @NonNull Tab1ListItemBean newItem) {
        return null;
    }
}
