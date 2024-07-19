package com.dungnm.example.compose.login.repo


class LoginRepo : ILoginRepo {
    override suspend fun login(userName: String?, password: String?): Boolean {
        return userName == "admin" && password == "admin"
    }
}