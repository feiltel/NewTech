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
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.nut2014.baselibrary.utils.FLog;
import com.nut2014.baselibrary.utils.MToast;
import com.nut2014.baselibrary.utils.WindowUtils;
import com.nut2014.newtech.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Tab1Fragment extends Fragment {

    private static final String TAG = "HomeFragment";
    @BindView(R.id.home_list_rv)
    RecyclerView home_list_rv;
    @BindView(R.id.top_bar)
    FrameLayout main_top_bar;
    @BindView(R.id.top_bar1)
    FrameLayout main_top_bar1;
  /*  @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;*/
    private LinearLayoutManager linearLayoutManager;
    private List<DiffUtilDemoEntity> listData = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab1_layout, container, false);
        ButterKnife.bind(this, rootView);
        main_top_bar.setPadding(0, WindowUtils.getStatusBarHeight(getActivity()), 0, 0);
        main_top_bar1.setPadding(0, WindowUtils.getStatusBarHeight(getActivity()), 0, 0);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        home_list_rv.setLayoutManager(linearLayoutManager);

        int all = 20;
        for (int i = 0; i < all; i++) {
            listData.add(new DiffUtilDemoEntity(i, "标题" + i));
        }
        Tab1Adapter tab1Adapter = new Tab1Adapter(listData);
        tab1Adapter.setDiffCallback(new DiffDemoCallback());
        View hearderView = LayoutInflater.from(getActivity()).inflate(R.layout.home_item_header, null);
        FrameLayout top_bar = hearderView.findViewById(R.id.top_bar);
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
                MToast.show(getActivity(), "position:" + position);
            }
        });
        tab1Adapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                MToast.show(getContext(),">>>");
                List<DiffUtilDemoEntity> demoEntityList=new ArrayList<>();
                for (int i = 30; i < 55; i++) {
                    demoEntityList.add(new DiffUtilDemoEntity(i,"测试"+i));
                }
                listData.addAll(demoEntityList);
                tab1Adapter.setDiffNewData(demoEntityList);
                tab1Adapter.getLoadMoreModule().loadMoreEnd();
            }
        });

        //下拉刷新
      /*  refreshLayout.setEnableLoadmore(false);
        refreshLayout.setHeaderView(new MHeaderView());
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishRefreshing();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
            }
        });*/
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}
