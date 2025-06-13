package com.smile.retrofitapp.presentation.compose.mvi.uiLayer

import com.smile.retrofitapp.domain.model.Task

sealed class UserIntents {
    data object Languages : UserIntents()
    data object GenerateLanguages: UserIntents()
    data object Comments: UserIntents()
    data object Tasks: UserIntents()
    data class TaskWork(val task: Task): UserIntents()
}

/*
enum class UserIntents {
    Languages,
    GenerateLanguages,
    Comments,
}
 */