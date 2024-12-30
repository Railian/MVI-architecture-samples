package ua.railian.mvi.sample.feature.auth.domain.repository

import ua.railian.data.result.DataResult
import ua.railian.mvi.sample.feature.auth.domain.error.ForgotPasswordError
import ua.railian.mvi.sample.feature.auth.domain.error.LoginError
import ua.railian.mvi.sample.feature.auth.domain.error.SignUpError

interface AuthRepository {
    fun isUserLoggedIn(): Boolean
    suspend fun getLastLoggedInUserEmail(): String?
    suspend fun loginWithEmail(email: String, password: String): DataResult<Unit, Set<LoginError>>
    suspend fun signUpWithEmail(email: String, password: String): DataResult<Unit, Set<SignUpError>>
    suspend fun forgotPassword(email: String): DataResult<Unit, ForgotPasswordError>
    suspend fun logout(): DataResult<Unit, String>
}
