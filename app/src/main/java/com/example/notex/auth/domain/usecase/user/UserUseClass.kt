package com.example.notex.auth.domain.usecase.user

class UserUseClass (
    val getLoginInfoUseCase: GetLoginInfoUseCase,
    val loadPostsUseCase: LoadPostsUseCase,
    val logoutUseCase: LogoutUseCase
)