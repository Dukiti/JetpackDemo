package com.dungnm.example.compose.network.repo.login

import com.dungnm.example.compose.network.repo.ILoginRepo

class LoginRepo : ILoginRepo {
    override suspend fun login(userName: String?, password: String?): Boolean {
        return userName == "admin" && password == "admin"
    }
}