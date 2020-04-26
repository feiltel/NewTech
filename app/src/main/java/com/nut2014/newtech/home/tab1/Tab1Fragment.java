package com.nut2014.newtech.home.tab1;


import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.jaeger.library.StatusBarUtil;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.nut2014.baselibrary.utils.FLog;
import com.nut2014.baselibrary.utils.MToast;
import com.nut2014.baselibrary.utils.WindowUtils;
import com.nut2014.newtech.R;
import com.nut2014.newtech.home.tab2.User;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Tab1Fragment extends Fragment {

    private static final String TAG = "HomeFragment";
    @BindView(R.id.home_list_rv)
    RecyclerView home_list_rv;

    @BindView(R.id.top_bar1)
    FrameLayout main_top_bar1;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    private LinearLayoutManager linearLayoutManager;
    private List<Tab1ListItemBean> listData = new ArrayList<>();

    public static Tab1Fragment newInstance() {
        return new Tab1Fragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab1_layout, container, false);
        ButterKnife.bind(this, rootView);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        home_list_rv.setLayoutManager(linearLayoutManager);
        main_top_bar1.setPadding(0, WindowUtils.getStatusBarHeight(getActivity()), 0, 0);
        int all = 20;
        for (int i = 0; i < all; i++) {
            listData.add(new Tab1ListItemBean(i, "标题" + i));
        }
        Tab1Adapter tab1Adapter = new Tab1Adapter(listData);
        tab1Adapter.setAnimationEnable(true);
        //加入数据变化回调
        tab1Adapter.setDiffCallback(new DiffDemoCallback());
        View hearderView = LayoutInflater.from(getActivity()).inflate(R.layout.home_item_header, null);
        FrameLayout top_bar = hearderView.findViewById(R.id.top_bar);

        /*banner*/
        Banner banner = (Banner) hearderView.findViewById(R.id.banner);
        List<User> mDatas=new ArrayList<>();
        mDatas.add(new User(0,""));
        mDatas.add(new User(0,""));
        mDatas.add(new User(0,""));
        mDatas.add(new User(0,""));
        mDatas.add(new User(0,""));
        banner.setAdapter(new ImageBannerAdapter(mDatas));
        banner.setIndicator(new CircleIndicator(getActivity()));

        top_bar.setPadding(0, WindowUtils.getStatusBarHeight(getActivity()), 0, 0);
        tab1Adapter.addHeaderView(hearderView);
        home_list_rv.setAdapter(tab1Adapter);
        home_list_rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                View viewByPosition = linearLayoutManager.findViewByPosition(0);
                if (viewByPosition != null) {
                    FLog.d(TAG, ">>>" + viewByPosition.getTop());
                    if ((-viewByPosition.getTop()) >= WindowUtils.getDensity(getActivity()) * 56) {
                        main_top_bar1.setVisibility(View.VISIBLE);
                    } else {
                        main_top_bar1.setVisibility(View.GONE);
                    }
                }
            }

        });
        tab1Adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                MToast.show(getActivity(), "测试diff :" + position);
                //测试diff
                if (position==0){
                    tab1Adapter.setDiffNewData(getNewData());
                }

            }
        });
        tab1Adapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                home_list_rv.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        /*写法2*/
                        List<Tab1ListItemBean> tab1ListItemBeans = new ArrayList<>();
                        for (int i = 30; i < 55; i++) {
                            tab1ListItemBeans.add(new Tab1ListItemBean(i, "测试" + i));
                        }
                        tab1Adapter.addData(tab1ListItemBeans);


                        //加载状态
                        if (listData.size()>100){
                            tab1Adapter.getLoadMoreModule().loadMoreEnd();
                        }else {
                            tab1Adapter.getLoadMoreModule().loadMoreComplete();
                        }
                    }
                },2000);


               /* MToast.show(getContext(),">>>");
                List<DiffUtilDemoEntity> tab1ListItemBeans=new ArrayList<>();
                for (int i = 30; i < 55; i++) {
                    tab1ListItemBeans.add(new DiffUtilDemoEntity(i,"测试"+i));
                }
                listData.addAll(tab1ListItemBeans);
                tab1Adapter.setDiffNewData(tab1ListItemBeans);
                tab1Adapter.getLoadMoreModule().loadMoreEnd();*/
            }
        });

        //下拉刷新
        refreshLayout.setEnableLoadmore(false);
        refreshLayout.setHeaderView(new MHeaderView());
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tab1Adapter.setDiffNewData(getNewData());
                        refreshLayout.finishRefreshing();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
            }
        });
        return rootView;
    }
    private List<Tab1ListItemBean> getNewData(){
        List<Tab1ListItemBean> newDataList=new ArrayList<>();
        newDataList.add(new Tab1ListItemBean(11,">>>>"));
        newDataList.add(new Tab1ListItemBean(12,">>>>"));
        newDataList.add(new Tab1ListItemBean(13,">>>>"));
        newDataList.add(new Tab1ListItemBean(14,">>>>"));
        newDataList.addAll(listData);
        newDataList.add(5,new Tab1ListItemBean(333,"插入"));
        return newDataList;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}
