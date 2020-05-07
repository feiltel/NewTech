package com.nut2014.newtech.navigation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class Nav1ViewModel : ViewModel() {
    var btnName: MutableLiveData<String> = MutableLiveData()
    init {
        btnName.value = "跳转"
    }
}
