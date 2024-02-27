package com.example.hospital.ui.reception

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hospital.data.ModelCreateCall
import com.example.hospital.network.ResponseState
import com.example.hospital.repo.Repo
import kotlinx.coroutines.launch
import javax.inject.Inject

class CreateCallViewModel
@Inject constructor(private val repo: Repo) : ViewModel() {
    private var _createCallLiveData = MutableLiveData<ResponseState<ModelCreateCall>>()
    val createCallLiveData: LiveData<ResponseState<ModelCreateCall>> get() = _createCallLiveData
    fun createCall(
        patientName: String,
        doctorId: Int,
        age: String,
        phone: String,
        description: String
    ) {
        viewModelScope.launch {
            _createCallLiveData.postValue(ResponseState.Loading)
            try {
                _createCallLiveData
                    .postValue(repo.createCall(patientName, doctorId, age, phone, description))
            } catch (e: Exception) {
                _createCallLiveData.postValue(e.localizedMessage?.let { ResponseState.Error(it) })
            }
        }
    }
}