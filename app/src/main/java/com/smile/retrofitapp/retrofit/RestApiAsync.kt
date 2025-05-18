package com.smile.retrofitapp.retrofit

import android.util.Log
import com.smile.retrofitapp.models.Comment
import com.smile.retrofitapp.models.Language
import com.smile.retrofitapp.models.LanguageList
import retrofit2.Callback

abstract class RestApiAsync<T> : Callback<T> {
    companion object {
        private const val TAG = "RestApiAsync"
    }

    abstract fun getWebUrl(): String

    private val callback : Callback<T>
        get() {
            return this
        }
    // get Retrofit client and Retrofit Api
    private val apiInterface : ApiInterface
        get() = Client.getInstance(getWebUrl()).create(ApiInterface::class.java)

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

    @Suppress("UNCHECKED_CAST")
    fun getComments() {
        Log.d(TAG, "getComments")
        // get Call from Retrofit Api
        apiInterface.getComments().enqueue(callback as Callback<ArrayList<Comment>>)
    }
}