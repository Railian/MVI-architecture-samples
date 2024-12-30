package ua.railian.mvi.sample.feature.auth.domain.error

sealed interface LoginError {

    sealed interface Email : LoginError {
        data object Blank : Email
    }

    sealed interface Password : LoginError {
        data object Blank : Password
    }

    sealed interface Common : LoginError {
        data object EmailOrPasswordIncorrect : Common
    }
}
