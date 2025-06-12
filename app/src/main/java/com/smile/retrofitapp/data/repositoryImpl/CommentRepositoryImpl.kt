package com.smile.retrofitapp.data.repositoryImpl

import com.smile.retrofitapp.data.models.Comment
import com.smile.retrofitapp.data.models.Constants
import com.smile.retrofitapp.data.retrofit.RestApiSync
import com.smile.retrofitapp.domain.repository.CommentRepository

class CommentRepositoryImpl: CommentRepository {
    override suspend fun getComments(): ArrayList<Comment> {
        return RestApiSync.getComments(Constants.COMMENTS_URL)
    }
}