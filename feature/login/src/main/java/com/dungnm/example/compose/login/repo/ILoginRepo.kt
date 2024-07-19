package com.dungnm.example.compose.login.repo

interface ILoginRepo {
    suspend fun login(userName: String?, password: String?): Boolean
}