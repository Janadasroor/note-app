package com.example.notex.auth.domain.usecase.user

import com.example.notex.auth.data.MediaPost
import com.example.notex.auth.data.userrepo.UserRepository

class LoadPostsUseCase(private val repo: UserRepository) {
    suspend operator fun invoke(token: String): List<MediaPost>? {
        return repo.getProtectedData(token)
    }
}
