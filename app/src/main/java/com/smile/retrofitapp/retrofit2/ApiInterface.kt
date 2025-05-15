package com.smile.retrofitapp.retrofit2

import com.smile.retrofitapp.models.Language
import com.smile.retrofitapp.models.LanguageList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("api/Languages")
    fun getAllLanguages(): Call<LanguageList>

    @GET("api/Languages/{id}")
    fun getLanguageById(@Path("id") id: Int): Call<Language>
}