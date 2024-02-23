package com.example.hospital.ui.login

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hospital.data.ModelUser
import com.example.hospital.network.ResponseState
import com.example.hospital.network.RetrofitService
import com.example.hospital.repo.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo: Repo,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {
    private val _loginState = MutableLiveData<ResponseState<ModelUser>>()
    val loginState: LiveData<ResponseState<ModelUser>> = _loginState

    fun loginUser(email: String, password: String) {
        _loginState.value = ResponseState.Loading
        viewModelScope.launch {
            try {
                when(val response = repo.login(email, password, "deviceToken")){
                    is ResponseState.Success -> {
                        _loginState.value = response
                        cacheLoginData(response.data)
                    }
                    else -> _loginState.value = response
                }
            } catch (e: Exception) {
                _loginState.value = e.localizedMessage?.let { ResponseState.Error(it) }
            }

        }
    }

    private fun cacheLoginData(user: ModelUser) {
        with(sharedPreferences.edit()) {
            putString("email", user.data.email)
            putString("userType", user.data.type)
            apply()
        }
    }
}