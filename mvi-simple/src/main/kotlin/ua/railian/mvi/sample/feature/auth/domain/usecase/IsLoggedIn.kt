package ua.railian.mvi.sample.feature.auth.domain.usecase

import ua.railian.mvi.sample.feature.auth.domain.repository.AuthRepository

fun interface IsUserLoggedIn {
    suspend operator fun invoke(): Boolean
}

internal class IsUserLoggedInUseCase(
    private val authRepository: AuthRepository,
) : IsUserLoggedIn {
    override suspend fun invoke(): Boolean {
        return authRepository.isUserLoggedIn()
    }
}
