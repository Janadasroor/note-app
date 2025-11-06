package com.example.myapplication.auth.domain.usecase.user

import com.example.myapplication.auth.data.MediaPost
import com.example.myapplication.auth.data.userrepo.UserRepository

class LoadPostsUseCase(private val repo: UserRepository) {
    suspend operator fun invoke(token: String): List<MediaPost>? {
        return repo.getProtectedData(token)
    }
}
