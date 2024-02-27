package com.example.hospital.repo

import com.example.hospital.data.DataAllUsers
import com.example.hospital.data.ModelAllCalls
import com.example.hospital.data.ModelAllUsers
import com.example.hospital.data.ModelCreateCall
import com.example.hospital.data.ModelUser
import com.example.hospital.network.ResponseState

interface IRepo {
    suspend fun getUsersByType(type: String): ResponseState<ModelAllUsers>
    suspend fun filterUsers(query: String): List<DataAllUsers>
    suspend fun getCalls(date: String): ResponseState<ModelAllCalls>
    suspend fun login(
        email: String,
        password: String,
        deviceToken: String
    ): ResponseState<ModelUser>

    suspend fun createCall(
        patientName: String,
        doctorId: Int,
        age: String,
        phone: String,
        description: String
    ) :ResponseState<ModelCreateCall>
}