package com.smile.retrofitapp.presentation.compose.mvi.uiLayer

import com.smile.retrofitapp.data.models.Comment

data class CommentViewState(
    val listTitle: String = "Comments List",
    val comments: List<Comment> = emptyList(),
    val sizeOfList: Int = 0
)