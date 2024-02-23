package com.example.hospital.repo

import com.example.hospital.data.DataAllUsers
import com.example.hospital.data.ModelAllCalls
import com.example.hospital.data.ModelAllUsers
import com.example.hospital.data.ModelUser
import com.example.hospital.network.ResponseState
import com.example.hospital.network.RetrofitService
import javax.inject.Inject

class Repo @Inject constructor (private val retrofitService: RetrofitService) : IRepo {
    private var originalList: ModelAllUsers? = null

    override suspend fun getUsersByType(type: String): ResponseState<ModelAllUsers> {
        val response = retrofitService.getUserByType(type)
        return if (response.status == 1) {
            originalList = response
            ResponseState.Success(response)
        } else {
            ResponseState.Error(response.message)
        }
    }

    override suspend fun filterUsers(query: String): List<DataAllUsers> {
        return originalList?.data?.filter {
            it.first_name.contains(query, ignoreCase = true) || it.type.contains(query, ignoreCase = true)
        } ?: emptyList()
    }

    override suspend fun getCalls(date: String): ResponseState<ModelAllCalls> {
        val response = retrofitService.getCalls(date)
        return if (response.status == 1) {
            ResponseState.Success(response)
        }else{
            ResponseState.Error(response.message)
        }
    }

    override suspend fun login(
        email: String,
        password: String,
        deviceToken: String
    ): ResponseState<ModelUser> {
        val response = retrofitService.loginUser(email,password,deviceToken)
        return if(response.status == 1){
            ResponseState.Success(response)
        }else{
            ResponseState.Error(response.message)
        }
    }


}