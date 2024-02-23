package com.example.hospital.ui.hr

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hospital.data.ModelUser
import com.example.hospital.network.ResponseState
import com.example.hospital.network.RetrofitService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val retrofitService: RetrofitService
) : ViewModel() {
    private val _profileLiveData = MutableLiveData<ResponseState<ModelUser>>()
    val profileLiveData: LiveData<ResponseState<ModelUser>> = _profileLiveData

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getUserProfile(id: Int) {
        viewModelScope.launch {
            _profileLiveData.value = ResponseState.Loading
            try {
                val response = retrofitService.showProfile(id)
                when (response.status) {
                    1 -> _profileLiveData.value = ResponseState.Success(response)
                    0 -> _profileLiveData.value = ResponseState.Error(response.message ?: "Unknown Error")
                    else -> _profileLiveData.value = ResponseState.Error("Invalid response status")
                }
            } catch (e: HttpException) {
                _profileLiveData.value = ResponseState.Error("Network error: ${e.message} ${e.message}")
            } catch (e: Exception) {
                _profileLiveData.value = ResponseState.Error("Error: ${e.localizedMessage}")
            }
        }
    }
}