package com.nut2014.newtech.dataBinding;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.nut2014.newtech.R;
import com.nut2014.newtech.databinding.ActivityVmDbBinding;

import java.util.List;
import java.util.Random;

public class VmDbActivity extends AppCompatActivity {
    ActivityVmDbBinding activityDataBing;
    private VmDbViewModel vmDbViewModel;
    private VmDbBean vmDbBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDataBing = DataBindingUtil.setContentView(this, R.layout.activity_vm_db);
        activityDataBing.setLifecycleOwner(this);
        activityDataBing.setTitle("Title");
        vmDbBean = new VmDbBean(0, "张三", "李四");
        activityDataBing.setVmdb(vmDbBean);
        activityDataBing.setGoodsHandler(new GoodsHandler());
        vmDbViewModel = ViewModelProviders.of(this).get(VmDbViewModel.class);
        vmDbViewModel.getListData().observe(this, new Observer<List<VmDbBean>>() {
            @Override
            public void onChanged(List<VmDbBean> vmDbBeans) {
                activityDataBing.setTitle(">>>>>>" + vmDbBeans.size());
            }
        });

    }

    public class GoodsHandler {
        public void changeGoodsName() {
            vmDbBean.setName(">>>>>>>>"+ new Random().nextInt(100));
        }

        public void changeGoodsDetails() {

        }

    }
}
