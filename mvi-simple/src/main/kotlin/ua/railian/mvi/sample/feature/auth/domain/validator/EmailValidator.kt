package ua.railian.mvi.sample.feature.auth.domain.validator

fun interface EmailValidator {
    fun check(email: String): EmailValidatorResult
}

sealed interface EmailValidatorResult {
    data object Blank : EmailValidatorResult
    data object Invalid : EmailValidatorResult
    data object Valid : EmailValidatorResult
}

class EmailValidatorImpl : EmailValidator {
    override fun check(email: String): EmailValidatorResult {
        if (email.isBlank()) return EmailValidatorResult.Blank
        if (EMAIL_ADDRESS.matches(email)) return EmailValidatorResult.Valid
        return EmailValidatorResult.Invalid
    }
}

val EMAIL_ADDRESS = Regex(
    pattern = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+"
)
