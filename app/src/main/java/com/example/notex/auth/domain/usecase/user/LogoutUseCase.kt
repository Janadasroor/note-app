package com.example.notex.auth.domain.usecase.user

import com.example.notex.auth.data.userrepo.UserRepository

class LogoutUseCase(private val repo: UserRepository) {
    suspend operator fun invoke(){
        return repo.logout()
    }
}