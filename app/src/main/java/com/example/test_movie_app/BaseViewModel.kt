package com.example.test_movie_app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_movie_app.presentation.viewModels.navigation.ResultViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    private lateinit var _resultViewModel: ResultViewModel

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    val resultCallBack by lazy {
        ResultCallBack()
    }

    fun setResultViewModel(resultViewModel: ResultViewModel) {
        if (!this::_resultViewModel.isInitialized) {
            _resultViewModel = resultViewModel
            _resultViewModel.clearOldData()
            viewModelScope.launch(Dispatchers.IO) {
                _resultViewModel.result.collectLatest { onResult(it) }
            }
        }
    }

    open fun onResult(screenResult: Any?) {
        println("==>result: $screenResult")
    }

    override fun onCleared() {
        super.onCleared()
        println("==>result: super ${this.javaClass.name}")
    }
}

class ResultCallBack {
    private val _result = MutableStateFlow<Any?>(null)
    val result: StateFlow<Any?>
        get() = _result.asStateFlow()

    fun setResult(data: Any) {
        _result.value = data
    }

}