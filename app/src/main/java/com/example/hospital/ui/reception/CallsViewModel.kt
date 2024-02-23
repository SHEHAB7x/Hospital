package com.example.hospital.ui.reception

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hospital.data.ModelAllCalls
import com.example.hospital.network.ResponseState
import com.example.hospital.network.RetrofitService
import com.example.hospital.repo.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CallsViewModel @Inject constructor(private val repo: Repo) :
    ViewModel() {
    private val _callsLiveData = MutableLiveData<ResponseState<ModelAllCalls>>()
    val callsLiveData: LiveData<ResponseState<ModelAllCalls>> = _callsLiveData

    fun getCalls(date: String) {
        _callsLiveData.value = ResponseState.Loading
        viewModelScope.launch {
            try {
                _callsLiveData.value = repo.getCalls(date)
            } catch (e: Exception) {
                _callsLiveData.value = e.localizedMessage?.let { ResponseState.Error(it) }
            }
        }
    }
}