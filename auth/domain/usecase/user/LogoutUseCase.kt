package com.example.myapplication.auth.domain.usecase.user

import com.example.myapplication.auth.data.userrepo.UserRepository

class LogoutUseCase(private val repo: UserRepository) {
    suspend operator fun invoke(){
        return repo.logout()
    }
}