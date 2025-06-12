package com.smile.retrofitapp.data.repositoryImpl

import com.smile.retrofitapp.data.models.Language
import com.smile.retrofitapp.domain.repository.GenLanguageRepository

class GenLanguageRepositoryImpl: GenLanguageRepository {
    override suspend fun getLanguages(): ArrayList<Language> {
        val languages = ArrayList<Language>().apply {
            for (i in 1..100) {
                val ch = (i+65).toChar().toString()
                add(Language(i, "$i", ch, ch))
            }
        }

        return languages
    }
}