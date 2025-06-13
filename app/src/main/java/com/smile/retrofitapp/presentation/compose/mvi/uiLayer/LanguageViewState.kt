package com.smile.retrofitapp.presentation.compose.mvi.uiLayer

import com.smile.retrofitapp.data.models.Language

data class LanguageViewState(
    val listTitle: String = "Languages List",
    val languages: List<Language> = emptyList(),
    val sizeOfList: Int = 0
)