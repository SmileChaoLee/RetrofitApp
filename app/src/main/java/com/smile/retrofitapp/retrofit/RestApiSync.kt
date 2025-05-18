package com.smile.retrofitapp.retrofit

import android.util.Log
import com.smile.retrofitapp.Constants
import com.smile.retrofitapp.models.Comment
import com.smile.retrofitapp.models.Language
import com.smile.retrofitapp.models.LanguageList
import retrofit2.Response

object RestApiSync {

    private const val TAG = "RestApiSync"

    // get Retrofit client and Retrofit Api
    private val apiInstanceMap: HashMap<String, ApiInterface> = HashMap()
    private fun getApiInstance(url: String): ApiInterface {
        val apiInstance: ApiInterface
        if (apiInstanceMap.contains(url)) {
            apiInstance = apiInstanceMap.getValue(url)
        } else {
            apiInstance = Client().getInstance(url).create(ApiInterface::class.java)
            apiInstanceMap[url] = apiInstance
        }
        Log.d(TAG, "getApiInstance = $apiInstance")
        return apiInstance
    }

    fun getAllLanguages(url: String = Constants.CHAO_URL): LanguageList {
        Log.d(TAG, "getAllLanguages")
        // get Call from Retrofit Api
        try {
            val response: Response<LanguageList> = getApiInstance(url)
                .getAllLanguages().execute()
            return response.body() ?: LanguageList()
        } catch (ex: Exception) {
            Log.d(TAG, "getAllLanguages().Exception")
            ex.printStackTrace()
            return LanguageList()
        }
    }

    fun getLanguageId(id: Int, url: String = Constants.CHAO_URL): Language {
        // get Call from Retrofit Api
        try {
            val response: Response<Language> = getApiInstance(url)
                .getLanguageById(id).execute()
            return response.body() ?: Language()
        } catch (ex: Exception) {
            Log.d(TAG, "getLanguageId().Exception")
            ex.printStackTrace()
            return Language()
        }
    }

    fun getComments(url: String = Constants.COMMENTS_URL): ArrayList<Comment> {
        Log.d(TAG, "getComments")
        // get Call from Retrofit Api
        try {
            val response: Response<ArrayList<Comment>> = getApiInstance(url)
                .getComments().execute()
            return response.body() ?: ArrayList()
        } catch (ex: Exception) {
            Log.d(TAG, "getComments().Exception")
            ex.printStackTrace()
            return ArrayList()
        }
    }
}