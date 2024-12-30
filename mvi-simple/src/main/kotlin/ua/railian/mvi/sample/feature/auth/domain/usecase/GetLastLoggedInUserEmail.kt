package ua.railian.mvi.sample.feature.auth.domain.usecase

import ua.railian.mvi.sample.feature.auth.domain.repository.AuthRepository

fun interface GetLastLoggedInUserEmail {
    suspend operator fun invoke(): String?
}

internal class GetLastLoggedInUserEmailUseCase(
    private val authRepository: AuthRepository,
) : GetLastLoggedInUserEmail {
    override suspend fun invoke(): String? {
        return authRepository.getLastLoggedInUserEmail()
    }
}
