package com.nut2014.newtech.home.tab1;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.nut2014.newtech.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author feiltel 2020/4/24 0024
 */
public class Tab1Adapter extends BaseQuickAdapter<Tab1ListItemBean, BaseViewHolder> implements LoadMoreModule {
    /**
     * 构造方法，此示例中，在实例化Adapter时就传入了一个List。
     * 如果后期设置数据，不需要传入初始List，直接调用 super(layoutResId); 即可
     */
    public Tab1Adapter(List<Tab1ListItemBean> list) {
        super(R.layout.tab1_base_item, list);
    }

    /**
     * 在此方法中设置item数据
     */
    @Override
    protected void convert(@NotNull BaseViewHolder helper, @NotNull Tab1ListItemBean item) {
        helper.setText(R.id.cov_tv, item.getName());
    }
}
