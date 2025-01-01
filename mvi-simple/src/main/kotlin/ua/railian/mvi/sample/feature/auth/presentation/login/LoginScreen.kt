package ua.railian.mvi.sample.feature.auth.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import ua.railian.architecture.mvi.compose.collectMviActions
import ua.railian.architecture.mvi.compose.renderMviState
import ua.railian.mvi.sample.feature.auth.domain.error.LoginError
import ua.railian.mvi.sample.feature.auth.presentation.common.component.EmailField
import ua.railian.mvi.sample.feature.auth.presentation.common.component.LoadingButton
import ua.railian.mvi.sample.feature.auth.presentation.common.component.MviSampleLargeTopAppBar
import ua.railian.mvi.sample.feature.auth.presentation.common.component.PasswordField
import ua.railian.mvi.sample.feature.auth.presentation.login.LoginMviModel.Action
import ua.railian.mvi.sample.feature.auth.presentation.login.LoginMviModel.Intent
import ua.railian.mvi.sample.feature.auth.presentation.login.LoginMviModel.State
import ua.railian.mvi.sample.feature.auth.presentation.login.component.ForgotPasswordButton
import ua.railian.mvi.sample.feature.auth.presentation.login.component.SignUpTextLink
import ua.railian.mvi.sample.ui.dialog.DialogPreview
import ua.railian.mvi.sample.ui.dialog.DialogVisuals
import ua.railian.mvi.sample.ui.dialog.LocalDialogHostDelegate
import ua.railian.mvi.sample.ui.dialog.error
import ua.railian.mvi.sample.ui.theme.MviSampleTheme

@Composable
fun LoginScreen(
    onNavigateToForgotPassword: (email: String) -> Unit,
    onNavigateToSignUp: (email: String, password: String) -> Unit,
    onLoggedIn: () -> Unit,
    mviModel: LoginMviModel = koinViewModel(),
) {
    val state = mviModel.renderMviState()
    LoginScreen(
        state = state,
        process = mviModel::processAsync,
        onNavigateToForgotPassword = { onNavigateToForgotPassword(state.email) },
        onNavigateToSignUp = { onNavigateToSignUp(state.email, state.password) },
    )
    val dialogHostDelegate = LocalDialogHostDelegate.current
    mviModel.collectMviActions { action ->
        when (action) {
            is Action.LoggedIn -> onLoggedIn()
            is Action.Error -> dialogHostDelegate.showDialog(
                action.error.dialogVisuals,
            )
        }
    }
}

@Composable
private fun LoginScreen(
    state: State,
    process: (Intent) -> Unit = {},
    onNavigateToForgotPassword: () -> Unit = {},
    onNavigateToSignUp: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            MviSampleLargeTopAppBar(title = "Login")
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
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = { process(Intent.Login) },
                ),
            )
            LoadingButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Login",
                enabled = !state.loading,
                loading = state.loading,
                onClick = { process(Intent.Login) },
            )
            ForgotPasswordButton(
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.loading,
                onClick = onNavigateToForgotPassword,
            )
            Spacer(
                modifier = Modifier.weight(1f),
            )
            SignUpTextLink(
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.loading,
                onClick = onNavigateToSignUp,
            )
        }
    }
}

//region Dialogs
private fun emailOrPasswordIncorrectDialog() = DialogVisuals.error(
    text = "Email or password is incorrect or account doesn't exist.",
)
//endregion

//region UI mappers
private val LoginError.Email.message: String
    get() = when (this) {
        LoginError.Email.Blank -> "Email cannot be blank."
    }

private val LoginError.Password.message: String
    get() = when (this) {
        LoginError.Password.Blank -> "Password cannot be blank."
    }

private val LoginError.Common.dialogVisuals: DialogVisuals
    get() = when (this) {
        LoginError.Common.EmailOrPasswordIncorrect ->
            emailOrPasswordIncorrectDialog()
    }
//endregion

//region Previews
@PreviewLightDark
@Composable
private fun LoginScreenPreview() {
    MviSampleTheme {
        LoginScreen(
            state = State(
                email = "jhon.doe@gmail.com",
                password = "123456",
            ),
        )
    }
}

@PreviewLightDark
@Composable
private fun LoginScreenLoadingPreview() {
    MviSampleTheme {
        LoginScreen(
            state = State(
                email = "jhon.doe@gmail.com",
                password = "123456",
                loading = true,
            ),
        )
    }
}

@PreviewLightDark
@Composable
private fun LoginScreenWithErrorsPreview() {
    MviSampleTheme {
        LoginScreen(
            state = State(
                email = "",
                password = "",
                emailError = LoginError.Email.Blank,
                passwordError = LoginError.Password.Blank,
            ),
        )
    }
}

@PreviewLightDark
@Composable
private fun EmailOrPasswordIncorrectDialogPreview() {
    MviSampleTheme {
        DialogPreview(emailOrPasswordIncorrectDialog())
    }
}
//endregion
