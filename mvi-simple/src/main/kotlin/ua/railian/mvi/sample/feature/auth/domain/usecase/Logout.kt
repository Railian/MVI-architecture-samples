package ua.railian.mvi.sample.feature.auth.domain.usecase

import ua.railian.data.result.DataResult
import ua.railian.mvi.sample.feature.auth.domain.repository.AuthRepository

fun interface Logout {
    suspend operator fun invoke(): DataResult<Unit, String>
}

internal class LogoutUseCase(
    private val authRepository: AuthRepository,
) : Logout {
    override suspend fun invoke(): DataResult<Unit, String> {
        return authRepository.logout()
    }
}
