package ua.railian.mvi.sample.feature.auth.domain.usecase

import ua.railian.data.result.DataResult
import ua.railian.mvi.sample.feature.auth.domain.error.ForgotPasswordError
import ua.railian.mvi.sample.feature.auth.domain.repository.AuthRepository
import ua.railian.mvi.sample.feature.auth.domain.validator.EmailValidator
import ua.railian.mvi.sample.feature.auth.domain.validator.EmailValidatorResult

fun interface ForgotPassword {
    suspend operator fun invoke(
        email: String,
    ): DataResult<Unit, ForgotPasswordError>
}

internal class ForgotPasswordUseCase(
    private val authRepository: AuthRepository,
    private val emailValidator: EmailValidator,
) : ForgotPassword {
    override suspend fun invoke(
        email: String,
    ): DataResult<Unit, ForgotPasswordError> {
        val error = emailValidator.check(email).asForgotPasswordError()
        if (error != null) return DataResult.failure(error)
        return authRepository.forgotPassword(email)
    }
}

private fun EmailValidatorResult.asForgotPasswordError(): ForgotPasswordError.Email? {
    return when (this) {
        EmailValidatorResult.Blank -> ForgotPasswordError.Email.Blank
        EmailValidatorResult.Invalid -> null
        EmailValidatorResult.Valid -> null
    }
}
