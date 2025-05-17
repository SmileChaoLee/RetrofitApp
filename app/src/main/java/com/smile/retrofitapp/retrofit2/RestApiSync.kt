package com.smile.retrofitapp.retrofit2

import android.util.Log
import com.smile.retrofitapp.models.Comment
import com.smile.retrofitapp.models.Language
import com.smile.retrofitapp.models.LanguageList
import retrofit2.Response

class RestApiSync(private val url: String = "http://137.184.120.171/") {
    companion object {
        private const val TAG = "RestApiSync"
    }

    // get Retrofit client and Retrofit Api
    @Suppress("UNCHECKED_CAST")
    private val apiInterface : ApiInterface
        get() {
            return Client().getInstance(url).create(ApiInterface::class.java)
        }

    @Suppress("UNCHECKED_CAST")
    fun getAllLanguages(): LanguageList {
        Log.d(TAG, "getAllLanguages")
        // get Call from Retrofit Api
        try {
            val response: Response<LanguageList> = apiInterface.getAllLanguages().execute()
            return response.body() ?: LanguageList()
        } catch (ex: Exception) {
            Log.d(TAG, "getAllLanguages().Exception")
            ex.printStackTrace()
            return LanguageList()
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun getLanguageId(id: Int): Language {
        // get Call from Retrofit Api
        try {
            val response: Response<Language> = apiInterface.getLanguageById(id).execute()
            return response.body() ?: Language()
        } catch (ex: Exception) {
            Log.d(TAG, "getLanguageId().Exception")
            ex.printStackTrace()
            return Language()
        }
    }

    fun getComments(): ArrayList<Comment> {
        Log.d(TAG, "getComments")
        // get Call from Retrofit Api
        try {
            val response: Response<ArrayList<Comment>> = apiInterface.getComments().execute()
            return response.body() ?: ArrayList()
        } catch (ex: Exception) {
            Log.d(TAG, "getComments().Exception")
            ex.printStackTrace()
            return ArrayList()
        }
    }
}