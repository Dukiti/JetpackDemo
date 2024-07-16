package com.dungnm.example.compose.network.repo

interface ILoginRepo {
    suspend fun login(userName: String?, password: String?): Boolean
}