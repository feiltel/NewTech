package com.nut2014.newtech.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ShareViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    var sharedName: MutableLiveData<String> = MutableLiveData()

    init {
        sharedName.value = "这是Tab3 按钮"
    }
}
