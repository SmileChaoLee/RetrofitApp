package com.smile.retrofitapp.view.compose.uiLayer

import com.smile.retrofitapp.models.Comment

data class CommentViewState(
    val listTitle: String = "Comments List",
    val comments: List<Comment> = emptyList(),
    val sizeOfList: Int = 0
)