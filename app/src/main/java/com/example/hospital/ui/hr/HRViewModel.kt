package com.example.hospital.ui.hr

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
class HRViewModel @Inject constructor(
    private val retrofitService: RetrofitService
) : ViewModel() {

    private val _userRegistrationResult = MutableLiveData<ResponseState<ModelUser>>()
    val userRegistrationResult: LiveData<ResponseState<ModelUser>> = _userRegistrationResult

    fun registerUser(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        gender: String,
        specialist: String,
        birthday: String,
        status: String,
        address: String,
        phone: String,
        type: String
    ) {
        viewModelScope.launch {
            val response = retrofitService.registerUser(
                email,
                password,
                firstName,
                lastName,
                gender,
                specialist,
                birthday,
                status,
                address,
                phone,
                type
            )
            try {
                when (response.status) {
                    1 -> _userRegistrationResult.value = ResponseState.Success(response)
                    else -> ResponseState.Error("Please enter a valid data")
                }
            } catch (e: Exception) {
                e.localizedMessage?.let { ResponseState.Error(it) }
            }

        }
    }

}