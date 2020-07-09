package com.nut2014.newtech.navigation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class Nav1ViewModel : ViewModel() {
    var listData: MutableLiveData<List<String>> = MutableLiveData()
    var scrollInfoPos: MutableLiveData<Int> = MutableLiveData()
    var scrollInfoY: MutableLiveData<Int> = MutableLiveData()

    init {
        var initData = ArrayList<String>()
        for (i in 1..30) {
            initData.add("测试$i")
        }

        listData.value = initData
        scrollInfoPos.value = 0
        scrollInfoY.value = 0
    }
}
