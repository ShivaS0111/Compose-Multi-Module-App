package com.example.test_movie_app.presentation.viewModels.navigation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ResultViewModel:ViewModel() {
    private val _result = MutableStateFlow<Any?>(null)
    val result: StateFlow<Any?> get() = _result

    fun setResult(result: Any) {
        _result.value = result
    }

    fun clearOldData() {
        _result.value = null
    }
}



class ResultCallBack {
    private val _result = MutableStateFlow<Any?>(null)
    val result: StateFlow<Any?>
        get() = _result.asStateFlow()

    fun setResult(data:Any){
        _result.value = data
    }

}