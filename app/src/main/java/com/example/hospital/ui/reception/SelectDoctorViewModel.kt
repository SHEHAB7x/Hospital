package com.example.hospital.ui.reception

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hospital.data.Const
import com.example.hospital.data.DataAllUsers
import com.example.hospital.data.ModelAllUsers
import com.example.hospital.network.ResponseState
import com.example.hospital.network.RetrofitService
import com.example.hospital.repo.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectDoctorViewModel
@Inject constructor(private val repo: Repo) : ViewModel() {
    private var _selectDoctorLiveData = MutableLiveData<ResponseState<ModelAllUsers>>()
    val selectDoctorLiveData: LiveData<ResponseState<ModelAllUsers>> = _selectDoctorLiveData

    fun getDoctors() {
        _selectDoctorLiveData.value = ResponseState.Loading
        viewModelScope.launch {
            try {
                _selectDoctorLiveData.value = repo.getUsersByType(Const.DOCTOR)
            } catch (e: Exception) {
                _selectDoctorLiveData.value = e.localizedMessage.let { ResponseState.Error(it!!) }
            }
        }
    }

    private val _filteredUsers = MutableLiveData<List<DataAllUsers>>()
    val filteredUsers: LiveData<List<DataAllUsers>> = _filteredUsers

    fun filterUsers(query: String) {
        viewModelScope.launch {
            val filteredList = repo.filterUsers(query)
            _filteredUsers.value = filteredList
        }

    }
}