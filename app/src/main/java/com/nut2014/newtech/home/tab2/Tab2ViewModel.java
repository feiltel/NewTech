package com.nut2014.newtech.home.tab2;

import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class Tab2ViewModel extends ViewModel {
    private MutableLiveData<List<User>> listData;

    List<User> mainListData = new ArrayList<>();

    public LiveData<List<User>> getListData() {
        if (listData == null) {
            listData = new MutableLiveData<List<User>>();
        }
        return listData;
    }

    private MutableLiveData<Integer> loadMoreStatue;

    public LiveData<Integer> getLoadMoreStatue() {
        if (loadMoreStatue == null) {
            loadMoreStatue = new MutableLiveData<>();
        }
        loadUsers();
        return loadMoreStatue;
    }


    public void loadUsers() {
         loadNum = 1;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int all = 40;
                for (int i = 0; i < all; i++) {
                    mainListData.add(new User(i, "张三" + i));
                }
                listData.setValue(mainListData);
            }
        }, 1500);


    }

    private int loadNum = 1;

    public void loadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadNum++;
                List<User> list = new ArrayList<>();
                for (int i = 150; i < 162; i++) {
                    mainListData.add(new User(i, "张三" + i + ">>" + loadNum));
                }
                list.addAll(mainListData);

                listData.setValue(list);
                if (loadNum >= 4) {
                    loadMoreStatue.setValue(2);
                } else {
                    loadMoreStatue.setValue(1);
                }

            }
        }, 3000);
    }

}
