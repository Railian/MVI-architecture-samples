package ua.railian.mvi.sample.feature.auth.domain.error

sealed interface ForgotPasswordError {

    sealed interface Email : ForgotPasswordError {
        data object Blank : Email
        data object DoesNotExist : Email
    }
}
