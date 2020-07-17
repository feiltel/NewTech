package com.nut2014.newtech.main;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.nut2014.newtech.R;

import java.util.List;

public class MainListAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements LoadMoreModule {

    /**
     * 构造方法，此示例中，在实例化Adapter时就传入了一个List。
     * 如果后期设置数据，不需要传入初始List，直接调用 super(layoutResId); 即可
     */
    public MainListAdapter(List<String> list) {
        super(R.layout.layout_main_list_item, list);
    }


    /**
     * 在此方法中设置item数据
     */
    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.info_tv, item);
    }
}
