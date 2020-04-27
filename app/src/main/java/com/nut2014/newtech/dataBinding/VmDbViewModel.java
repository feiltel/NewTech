package com.nut2014.newtech.dataBinding;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author feiltel 2020/4/27 0027
 */
public class VmDbViewModel extends ViewModel {
    private MutableLiveData<List<VmDbBean>> listData;

    public LiveData<List<VmDbBean>> getListData() {
        if (listData == null) {
            listData = new MutableLiveData<>();
            List<VmDbBean> beanList = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                beanList.add(new VmDbBean(i, "张三" + i, "学生" + i));
            }
            listData.setValue(beanList);
        }
        return listData;
    }
}
