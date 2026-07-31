package com.itslerdo.barber.network

import Ui.Screens.Auth.RegisterFormData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

// TODO: Define GoogleLoginRequest and AuthResponse data classes if they don't exist yet
data class GoogleLoginRequest(val idToken: String)
data class AuthResponse(val token: String, val message: String)

interface AuthApi {
    // Tu endpoint anterior
    @POST("/api/auth/google")
    suspend fun loginWithGoogle(@Body request: GoogleLoginRequest): Response<AuthResponse>

    // El nuevo endpoint conectando tu data class
    @POST("/api/auth/register")
    suspend fun registerAccount(@Body request: RegisterFormData): Response<AuthResponse>
}
