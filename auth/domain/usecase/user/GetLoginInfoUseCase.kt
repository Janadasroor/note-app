package com.example.myapplication.auth.domain.usecase.user

import com.example.myapplication.auth.data.LoginPrefModel
import com.example.myapplication.auth.data.userrepo.UserRepository

class GetLoginInfoUseCase(private val repo: UserRepository) {
    suspend operator fun invoke(): LoginPrefModel? {
        return repo.getLoginInfo()
    }
}
