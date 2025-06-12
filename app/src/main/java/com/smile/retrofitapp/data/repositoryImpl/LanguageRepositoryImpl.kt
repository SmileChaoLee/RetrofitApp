package com.smile.retrofitapp.data.repositoryImpl

import com.smile.retrofitapp.data.models.Constants
import com.smile.retrofitapp.data.models.Language
import com.smile.retrofitapp.data.retrofit.RestApiSync
import com.smile.retrofitapp.domain.repository.LanguageRepository

class LanguageRepositoryImpl: LanguageRepository {
    override suspend fun getLanguages(): ArrayList<Language> {
        return RestApiSync.getAllLanguages(Constants.CHAO_URL).languages
    }
}