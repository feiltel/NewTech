package com.nut2014.newtech.navigation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class Nav1ViewModel : ViewModel() {
    var btnName: MutableLiveData<String> = MutableLiveData()
    var listData:MutableLiveData<List<String>> =MutableLiveData();
    var scrollInfoPos:MutableLiveData<Int> =MutableLiveData();
    var scrollInfoY:MutableLiveData<Int> =MutableLiveData();
    init {
        btnName.value = "跳转"
        listData.value=ArrayList<String>();
        scrollInfoPos.value=0;
        scrollInfoY.value=0;
    }
}
