package com.smile.retrofitapp.domain.repository

import com.smile.retrofitapp.data.models.Language

interface GenLanguageRepository {
    suspend fun getLanguages(): ArrayList<Language>
}