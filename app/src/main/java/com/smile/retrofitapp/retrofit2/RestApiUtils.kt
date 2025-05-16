package com.smile.retrofitapp.retrofit2

import com.smile.retrofitapp.models.Language
import com.smile.retrofitapp.models.LanguageList

object RestApiUtils {
    private val restApiSync = RestApiSync()

    suspend fun getAllLanguages(): LanguageList {
        return restApiSync.getAllLanguages()
    }

    suspend fun getLanguageId(id: Int): Language {
        return restApiSync.getLanguageId(id)
    }
}