package com.example.hospital.network

import androidx.core.content.ContextCompat.RegisterReceiverFlags
import com.example.hospital.data.DataUser
import com.example.hospital.data.ModelAllCalls
import com.example.hospital.data.ModelAllUsers
import com.example.hospital.data.ModelUser
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RetrofitService {
    @FormUrlEncoded
    @POST("register")
    suspend fun registerUser(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("first_name") firstName: String,
        @Field("last_name") lastName: String,
        @Field("gender") gender: String,
        @Field("specialist") specialist: String,
        @Field("birthday") birthday: String,
        @Field("status") status: String,
        @Field("address") address: String,
        @Field("mobile") mobile: String,
        @Field("type") type: String
    ): ModelUser

    @FormUrlEncoded
    @POST("login")
    suspend fun loginUser(
        @Field("email") email : String,
        @Field("password") password: String,
        @Field("device_token") deviceToken: String
    ) : ModelUser

    @FormUrlEncoded
    @POST("show-profile")
    suspend fun showProfile(
        @Field("user_id") userId : Int
    ) : ModelUser

    @GET("doctors")
    suspend fun getUserByType(
        @Query("type") type : String
    ): ModelAllUsers

    @GET("calls")
    suspend fun getCalls(
        @Query("date") date : String
    ) : ModelAllCalls

}