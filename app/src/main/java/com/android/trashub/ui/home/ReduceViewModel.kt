package com.android.trashub.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ReduceViewModel {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}