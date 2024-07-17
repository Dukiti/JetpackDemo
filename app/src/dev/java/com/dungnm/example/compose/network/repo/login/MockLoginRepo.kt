package com.dungnm.example.compose.network.repo.login

import com.dungnm.example.compose.network.repo.ILoginRepo
import java.net.UnknownHostException

class MockLoginRepo : ILoginRepo {
    override suspend fun login(userName: String?, password: String?): Boolean {
        if (userName == "admin" && password == "admin") {
            return true
        } else if (userName == "a") {
            throw UnknownHostException()
        } else {
            return false
        }
    }
}