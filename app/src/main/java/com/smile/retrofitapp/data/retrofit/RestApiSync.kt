package com.smile.retrofitapp.data.retrofit

import android.util.Log
import com.smile.retrofitapp.data.models.Comment
import com.smile.retrofitapp.data.models.Language
import com.smile.retrofitapp.data.models.LanguageList
import retrofit2.Response

object RestApiSync {

    private const val TAG = "RestApiSync"

    // get Retrofit client and Retrofit Api
    private val apiInstanceMap: HashMap<String, ApiInterface> = HashMap()
    private fun getApiInstance(webUrl: String): ApiInterface {
        val apiInstance: ApiInterface
        if (apiInstanceMap.contains(webUrl)) {
            apiInstance = apiInstanceMap.getValue(webUrl)
        } else {
            apiInstance = Client.getInstance(webUrl).create(ApiInterface::class.java)
            apiInstanceMap[webUrl] = apiInstance
        }
        Log.d(TAG, "getApiInstance = $apiInstance")
        return apiInstance
    }

    fun getAllLanguages(webUrl: String): LanguageList {
        Log.d(TAG, "getAllLanguages")
        // get Call from Retrofit Api
        try {
            val response: Response<LanguageList> = getApiInstance(webUrl)
                .getAllLanguages().execute()
            return response.body() ?: LanguageList()
        } catch (ex: Exception) {
            Log.d(TAG, "getAllLanguages().Exception")
            ex.printStackTrace()
            return LanguageList()
        }
    }

    fun getLanguageId(id: Int, webUrl: String): Language {
        // get Call from Retrofit Api
        try {
            val response: Response<Language> = getApiInstance(webUrl)
                .getLanguageById(id).execute()
            return response.body() ?: Language()
        } catch (ex: Exception) {
            Log.d(TAG, "getLanguageId().Exception")
            ex.printStackTrace()
            return Language()
        }
    }

    fun getComments(webUrl: String): ArrayList<Comment> {
        Log.d(TAG, "getComments")
        // get Call from Retrofit Api
        try {
            val response: Response<ArrayList<Comment>> = getApiInstance(webUrl)
                .getComments().execute()
            return response.body() ?: ArrayList()
        } catch (ex: Exception) {
            Log.d(TAG, "getComments().Exception")
            ex.printStackTrace()
            return ArrayList()
        }
    }
}