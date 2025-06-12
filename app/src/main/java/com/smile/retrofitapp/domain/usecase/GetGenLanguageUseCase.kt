package com.smile.retrofitapp.domain.usecase

import com.smile.retrofitapp.data.repositoryImpl.GenLanguageRepositoryImpl

open class GetGenLanguageUseCase(
    private val genGenLanguageRepository: GenLanguageRepositoryImpl) {
    suspend operator fun invoke() = genGenLanguageRepository.getLanguages()
}