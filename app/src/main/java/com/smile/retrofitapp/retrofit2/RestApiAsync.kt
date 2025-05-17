package com.smile.retrofitapp.retrofit2

import android.util.Log
import com.smile.retrofitapp.models.Language
import com.smile.retrofitapp.models.LanguageList
import retrofit2.Callback

abstract class RestApiAsync<T> : Callback<T> {
    companion object {
        private const val TAG = "RestApiAsync"
    }

    abstract fun getUrl(): String

    private val callback : Callback<T>
        get() {
            return this
        }
    // get Retrofit client and Retrofit Api
    private val apiInterface : ApiInterface
        get() = Client().getInstance(getUrl()).create(ApiInterface::class.java)

    @Suppress("UNCHECKED_CAST")
    fun getAllLanguages() {
        Log.d(TAG, "getAllLanguages")
        // get Call from Retrofit Api
        apiInterface.getAllLanguages().enqueue(callback as Callback<LanguageList>)
    }

    @Suppress("UNCHECKED_CAST")
    fun getLanguageId(id: Int) {
        // get Call from Retrofit Api
        apiInterface.getLanguageById(id).enqueue(callback as Callback<Language>)
    }
}