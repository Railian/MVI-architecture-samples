package ua.railian.mvi.sample.feature.auth.domain.usecase

import ua.railian.data.result.DataResult
import ua.railian.mvi.sample.feature.auth.domain.error.SignUpError
import ua.railian.mvi.sample.feature.auth.domain.repository.AuthRepository
import ua.railian.mvi.sample.feature.auth.domain.validator.EmailValidator
import ua.railian.mvi.sample.feature.auth.domain.validator.EmailValidatorResult
import ua.railian.mvi.sample.feature.auth.domain.validator.PasswordValidator
import ua.railian.mvi.sample.feature.auth.domain.validator.PasswordValidatorResult

fun interface SignUp {
    suspend operator fun invoke(
        email: String,
        password: String,
        passwordConfirmation: String,
    ): DataResult<Unit, Set<SignUpError>>
}

internal class SignUpUseCase(
    private val emailValidator: EmailValidator,
    private val passwordValidator: PasswordValidator,
    private val authRepository: AuthRepository,
) : SignUp {
    override suspend fun invoke(
        email: String,
        password: String,
        passwordConfirmation: String,
    ): DataResult<Unit, Set<SignUpError>> {
        val errors = mutableSetOf<SignUpError>()
        emailValidator.check(email).asSignUpError()?.let(errors::add)
        passwordValidator.check(password).asSignUpError()?.let(errors::add)
        if (passwordConfirmation != password) {
            errors += SignUpError.PasswordConfirmation.NotEqual
        }
        if (errors.isNotEmpty()) return DataResult.failure(errors)
        return authRepository.signUpWithEmail(email, password)
    }
}

private fun EmailValidatorResult.asSignUpError(): SignUpError.Email? {
    return when (this) {
        EmailValidatorResult.Blank -> SignUpError.Email.Blank
        EmailValidatorResult.Invalid -> SignUpError.Email.Invalid
        EmailValidatorResult.Valid -> null
    }
}

private fun PasswordValidatorResult.asSignUpError(): SignUpError.Password? {
    return when (this) {
        PasswordValidatorResult.Blank -> SignUpError.Password.Blank
        PasswordValidatorResult.Weak -> SignUpError.Password.Weak
        PasswordValidatorResult.Valid -> null
    }
}
