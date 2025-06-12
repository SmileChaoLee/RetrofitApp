package com.smile.retrofitapp.domain.repository

import com.smile.retrofitapp.data.models.Comment

interface CommentRepository {
    suspend fun getComments(): ArrayList<Comment>
}