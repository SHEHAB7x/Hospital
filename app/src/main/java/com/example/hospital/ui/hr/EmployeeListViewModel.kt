package com.example.hospital.ui.hr

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hospital.data.DataAllUsers
import com.example.hospital.data.ModelAllUsers
import com.example.hospital.network.ResponseState
import com.example.hospital.network.RetrofitService
import com.example.hospital.repo.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeeListViewModel @Inject constructor(
    private val repository : Repo
) : ViewModel() {
    private val _employeeListLiveData = MutableLiveData<ResponseState<ModelAllUsers>>()
    val employeeListLiveData: LiveData<ResponseState<ModelAllUsers>> = _employeeListLiveData

    fun getUsers(type: String) {
        viewModelScope.launch {
            try {
                _employeeListLiveData.value = repository.getUsersByType(type)
            }catch (e : Exception){
                _employeeListLiveData.value = e.localizedMessage?.let { ResponseState.Error(it) }
            }
        }
    }

    private val _filteredUsers = MutableLiveData<List<DataAllUsers>>()
    val filteredUsers: LiveData<List<DataAllUsers>> = _filteredUsers

     fun filterUsers(query: String) {
        viewModelScope.launch {
            val filteredList = repository.filterUsers(query)
            _filteredUsers.value = filteredList
        }
    }
}