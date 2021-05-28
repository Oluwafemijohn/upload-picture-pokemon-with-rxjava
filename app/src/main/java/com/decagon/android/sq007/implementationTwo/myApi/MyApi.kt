package com.decagon.android.sq007.implementationTwo.myApi

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface MyApi {

    @Multipart
    @POST("upload")
    fun uploadImage(@Part image: MultipartBody.Part): Call<UploadRes>
}
