package com.nut2014.newtech.home.tab2;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.nut2014.newtech.R;
import com.nut2014.newtech.test.ItemListDialogFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Tab2Fragment extends Fragment {

    private static final String TAG = "Tab2Fragment";
    private Tab2ViewModel mViewModel;
    @BindView(R.id.test_tv)
    Button testTv;
    @BindView(R.id.list_rv)
    RecyclerView list_rv;
    private Tab2Adapter adapter;

    public static Tab2Fragment newInstance() {
        return new Tab2Fragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab2_layout, container, false);
        ButterKnife.bind(this, rootView);
        list_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        testTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.loadUsers();
            }
        });
        initModelView();
        return rootView;
    }

    private void  initModelView(){
        mViewModel = ViewModelProviders.of(this).get(Tab2ViewModel.class);
        mViewModel.getListData().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {

                testTv.setText(users.size() + ">>");

                if (adapter == null) {
                    adapter = new Tab2Adapter(users);
                    adapter.setDiffCallback(new DiffTab2Callback());
                    list_rv.setAdapter(adapter);
                    adapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
                        @Override
                        public void onLoadMore() {
                            mViewModel.loadMore();
                        }
                    });
                }
                adapter.setDiffNewData(users);

            }
        });
        mViewModel.getLoadMoreStatue().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer==1){
                    adapter.getLoadMoreModule().loadMoreComplete();
                }else if (integer==2){
                    adapter.getLoadMoreModule().loadMoreEnd();
                }else {
                    adapter.getLoadMoreModule().loadMoreEnd();
                }
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}
