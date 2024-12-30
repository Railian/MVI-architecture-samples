package ua.railian.mvi.sample.feature.auth.domain.usecase

import ua.railian.data.result.DataResult
import ua.railian.mvi.sample.feature.auth.domain.error.LoginError
import ua.railian.mvi.sample.feature.auth.domain.repository.AuthRepository
import ua.railian.mvi.sample.feature.auth.domain.validator.EmailValidator
import ua.railian.mvi.sample.feature.auth.domain.validator.EmailValidatorResult
import ua.railian.mvi.sample.feature.auth.domain.validator.PasswordValidator
import ua.railian.mvi.sample.feature.auth.domain.validator.PasswordValidatorResult

fun interface LoginWithEmail {
    suspend operator fun invoke(
        email: String,
        password: String,
    ): DataResult<Unit, Set<LoginError>>
}

internal class LoginWithEmailUseCase(
    private val authRepository: AuthRepository,
    private val emailValidator: EmailValidator,
    private val passwordValidator: PasswordValidator,
) : LoginWithEmail {
    override suspend fun invoke(
        email: String,
        password: String
    ): DataResult<Unit, Set<LoginError>> {
        val errors = mutableSetOf<LoginError>()
        emailValidator.check(email).asLoginError()?.let(errors::add)
        passwordValidator.check(password).asLoginError()?.let(errors::add)
        if (errors.isNotEmpty()) return DataResult.failure(errors)
        return authRepository.loginWithEmail(email, password)
    }
}

private fun EmailValidatorResult.asLoginError(): LoginError.Email? {
    return when (this) {
        EmailValidatorResult.Blank -> LoginError.Email.Blank
        EmailValidatorResult.Invalid -> null
        EmailValidatorResult.Valid -> null
    }
}

private fun PasswordValidatorResult.asLoginError(): LoginError.Password? {
    return when (this) {
        PasswordValidatorResult.Blank -> LoginError.Password.Blank
        PasswordValidatorResult.Weak -> null
        PasswordValidatorResult.Valid -> null
    }
}
