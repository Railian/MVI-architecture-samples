package ua.railian.mvi.sample.feature.auth.domain.error

sealed interface SignUpError {

    sealed interface Email : SignUpError {
        data object Blank : Email
        data object Invalid : Email
        data object AlreadyExists : Email
    }

    sealed interface Password : SignUpError {
        data object Blank : Password
        data object Weak : Password
    }

    sealed interface PasswordConfirmation : SignUpError {
        data object NotEqual : PasswordConfirmation
    }

    sealed interface Common : SignUpError
}
