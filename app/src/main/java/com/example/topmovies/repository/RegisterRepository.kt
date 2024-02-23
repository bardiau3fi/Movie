package com.example.topmovies.repository

import com.example.topmovies.api.ApiServices
import com.example.topmovies.models.register.BodyRegister
import javax.inject.Inject

class RegisterRepository @Inject constructor(private val api: ApiServices) {
    suspend fun registerUser(body: BodyRegister) = api.registerUser(body)
}