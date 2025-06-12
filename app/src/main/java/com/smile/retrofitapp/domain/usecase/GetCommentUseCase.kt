package com.smile.retrofitapp.domain.usecase

import com.smile.retrofitapp.domain.repository.CommentRepository

class GetCommentUseCase(
  private val commentRepository: CommentRepository) {
    suspend operator fun invoke() = commentRepository.getComments()
}