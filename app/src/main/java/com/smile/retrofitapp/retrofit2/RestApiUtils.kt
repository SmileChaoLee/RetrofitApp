package com.smile.retrofitapp.retrofit2

import com.smile.retrofitapp.models.Comment
import com.smile.retrofitapp.models.Language
import com.smile.retrofitapp.models.LanguageList

object RestApiUtils {
    suspend fun getAllLanguages(): LanguageList {
        return RestApiSync.getAllLanguages()
    }
    suspend fun getLanguageId(id: Int): Language {
        return RestApiSync.getLanguageId(id)
    }
    suspend fun getComments(): ArrayList<Comment> {
        return RestApiSync.getComments()
    }
}