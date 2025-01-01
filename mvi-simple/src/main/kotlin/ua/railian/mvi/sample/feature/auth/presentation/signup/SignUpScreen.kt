package ua.railian.mvi.sample.feature.auth.presentation.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import ua.railian.architecture.mvi.compose.collectMviActions
import ua.railian.architecture.mvi.compose.renderMviState
import ua.railian.mvi.sample.feature.auth.domain.error.SignUpError
import ua.railian.mvi.sample.feature.auth.presentation.common.component.EmailField
import ua.railian.mvi.sample.feature.auth.presentation.common.component.LoadingButton
import ua.railian.mvi.sample.feature.auth.presentation.common.component.MviSampleLargeTopAppBar
import ua.railian.mvi.sample.feature.auth.presentation.common.component.PasswordField
import ua.railian.mvi.sample.feature.auth.presentation.signup.SignUpMviModel.Action
import ua.railian.mvi.sample.feature.auth.presentation.signup.SignUpMviModel.Intent
import ua.railian.mvi.sample.feature.auth.presentation.signup.SignUpMviModel.State
import ua.railian.mvi.sample.ui.dialog.DialogAction
import ua.railian.mvi.sample.ui.dialog.DialogPreview
import ua.railian.mvi.sample.ui.dialog.DialogVisuals
import ua.railian.mvi.sample.ui.dialog.LocalDialogHostDelegate
import ua.railian.mvi.sample.ui.theme.MviSampleTheme

@Composable
fun SignUpScreen(
    onNavigateBack: () -> Unit,
    onSignedUp: () -> Unit,
    mviModel: SignUpMviModel = koinViewModel(),
) {
    SignUpScreen(
        state = mviModel.renderMviState(),
        process = mviModel::processAsync,
        onNavigateBack = onNavigateBack,
    )
    val dialogHostDelegate = LocalDialogHostDelegate.current
    mviModel.collectMviActions { action ->
        when (action) {
            Action.SignedUp -> dialogHostDelegate.showDialog(
                signedUpSuccessfullyDialog(onSignedUp),
            )

            is Action.Error -> TODO()
        }
    }
}

@Composable
private fun SignUpScreen(
    state: State,
    process: (Intent) -> Unit = {},
    onNavigateBack: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            MviSampleLargeTopAppBar(
                title = "Sign Up",
                onNavigateBack = onNavigateBack,
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .imePadding()
                .padding(16.dp)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(space = 16.dp),
        ) {
            val focusManager = LocalFocusManager.current
            EmailField(
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.loading,
                error = state.emailError?.message,
                value = state.email,
                onValueChange = { process(Intent.ChangeEmail(it)) },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                ),
            )
            PasswordField(
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.loading,
                error = state.passwordError?.message,
                value = state.password,
                onValueChange = { process(Intent.ChangePassword(it)) },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) },
                ),
            )
            PasswordField(
                modifier = Modifier.fillMaxWidth(),
                label = "Confirm password",
                enabled = !state.loading,
                error = state.passwordConfirmationError?.message,
                value = state.passwordConfirmation,
                onValueChange = { process(Intent.ChangePasswordConfirmation(it)) },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = { process(Intent.SignUp) },
                ),
            )
            LoadingButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Sign Up",
                enabled = !state.loading,
                loading = state.loading,
                onClick = { process(Intent.SignUp) },
            )
        }
    }
}

//region Dialogs
private fun signedUpSuccessfullyDialog(
    onConfirm: () -> Unit = {},
) = DialogVisuals.Alert(
    title = "Sign Up",
    text = "You have successfully signed up.",
    confirm = DialogAction(
        text = "Let's go",
        action = onConfirm,
    ),
)
//endregion

//region UI mappers
private val SignUpError.Email.message: String
    get() = when (this) {
        SignUpError.Email.Blank -> "Email cannot be blank."
        SignUpError.Email.Invalid -> "Email is invalid."
        SignUpError.Email.AlreadyExists -> "Email already exists."
    }

private val SignUpError.Password.message: String
    get() = when (this) {
        SignUpError.Password.Blank -> "Password cannot be blank."
        SignUpError.Password.Weak -> "Password is weak."
    }

private val SignUpError.PasswordConfirmation.message: String
    get() = when (this) {
        SignUpError.PasswordConfirmation.NotEqual -> "Passwords are not equal."
    }
//endregion

//region Previews
@PreviewLightDark
@Composable
private fun SignUpScreenPreview() {
    MviSampleTheme {
        SignUpScreen(
            state = State(
                email = "jhon.doe@gmail.com",
                password = "123456",
                passwordConfirmation = "123456",
            ),
        )
    }
}

@PreviewLightDark
@Composable
private fun SignedUpSuccessfullyDialogPreview() {
    MviSampleTheme {
        DialogPreview(signedUpSuccessfullyDialog())
    }
}
//endregion
