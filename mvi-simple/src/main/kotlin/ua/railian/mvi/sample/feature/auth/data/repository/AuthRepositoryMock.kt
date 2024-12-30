package ua.railian.mvi.sample.feature.auth.data.repository

import dev.whyoleg.cryptography.CryptographyProvider
import dev.whyoleg.cryptography.algorithms.SHA512
import kotlinx.coroutines.delay
import kotlinx.io.bytestring.encodeToByteString
import ua.railian.data.result.DataResult
import ua.railian.mvi.sample.feature.auth.domain.error.ForgotPasswordError
import ua.railian.mvi.sample.feature.auth.domain.error.LoginError
import ua.railian.mvi.sample.feature.auth.domain.error.SignUpError
import ua.railian.mvi.sample.feature.auth.domain.repository.AuthRepository
import kotlin.time.Duration.Companion.seconds

class AuthRepositoryMock : AuthRepository {

    private val encoder = CryptographyProvider.Default.get(SHA512).hasher()
    private val credentialsStore = mutableMapOf(
        "admin" to encoder.hashBlocking("admin".encodeToByteString())
    )

    private var loggedInUser: String? = null
    private var lastLoggedInUser: String? = null

    override fun isUserLoggedIn(): Boolean {
        return loggedInUser != null
    }

    override suspend fun getLastLoggedInUserEmail(): String? {
        return lastLoggedInUser
    }

    override suspend fun loginWithEmail(
        email: String,
        password: String,
    ): DataResult<Unit, Set<LoginError>> {
        delay(3.seconds)
        val hashedPassword = encoder.hash(password.encodeToByteString())
        if (email !in credentialsStore || credentialsStore[email] != hashedPassword) {
            return DataResult.failure(setOf(LoginError.Common.EmailOrPasswordIncorrect))
        }
        loggedInUser = email
        lastLoggedInUser = email
        return DataResult.success(Unit)
    }

    override suspend fun signUpWithEmail(
        email: String,
        password: String,
    ): DataResult<Unit, Set<SignUpError>> {
        delay(3.seconds)
        if (email in credentialsStore) {
            return DataResult.failure(setOf(SignUpError.Email.AlreadyExists))
        }
        credentialsStore[email] = encoder.hash(password.encodeToByteString())
        return DataResult.success(Unit)
    }

    override suspend fun forgotPassword(
        email: String,
    ): DataResult<Unit, ForgotPasswordError> {
        delay(1.seconds)
        if (email !in credentialsStore) {
            return DataResult.failure(ForgotPasswordError.Email.DoesNotExist)
        }
        return DataResult.success(Unit)
    }

    override suspend fun logout(): DataResult<Unit, String> {
        delay(1.seconds)
        loggedInUser = null
        return DataResult.success(Unit)
    }
}
