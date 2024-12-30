package ua.railian.mvi.sample.feature.auth.domain.validator

fun interface PasswordValidator {
    fun check(password: String): PasswordValidatorResult
}

sealed interface PasswordValidatorResult {
    data object Blank : PasswordValidatorResult
    data object Weak : PasswordValidatorResult
    data object Valid : PasswordValidatorResult
}

class PasswordValidatorImpl : PasswordValidator {
    override fun check(password: String): PasswordValidatorResult {
        if (password.isBlank()) return PasswordValidatorResult.Blank
        if (password.length < 8) return PasswordValidatorResult.Weak
        return PasswordValidatorResult.Valid
    }
}
