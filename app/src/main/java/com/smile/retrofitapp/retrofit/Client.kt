package com.smile.retrofitapp.retrofit

import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class Client {

    companion object {
        private const val TAG = "Client"
    }

    // private const val BASE_URL = "http://137.184.120.171/"
    // For emulator
    // private static final String BASE_URL = "http://10.0.2.2:5000/";
    private lateinit var retrofit: Retrofit

    fun getInstance(url: String): Retrofit {
        Log.d(TAG, "getInstance")
        val client = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS).build()
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()
        retrofit = Retrofit.Builder()
            .baseUrl(url)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        Log.d(TAG, "getInstance.retrofit = $retrofit")
        return retrofit
    }
}
