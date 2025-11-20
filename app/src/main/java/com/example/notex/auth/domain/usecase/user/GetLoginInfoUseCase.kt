package com.example.notex.auth.domain.usecase.user

import com.example.notex.auth.data.LoginPrefModel
import com.example.notex.auth.data.userrepo.UserRepository

class GetLoginInfoUseCase(private val repo: UserRepository) {
    suspend operator fun invoke(): LoginPrefModel? {
        return repo.getLoginInfo()
    }
}
