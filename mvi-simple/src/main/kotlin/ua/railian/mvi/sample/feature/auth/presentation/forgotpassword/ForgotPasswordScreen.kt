package ua.railian.mvi.sample.feature.auth.presentation.forgotpassword

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import ua.railian.architecture.mvi.compose.collectMviActions
import ua.railian.architecture.mvi.compose.renderMviState
import ua.railian.mvi.sample.feature.auth.domain.error.ForgotPasswordError
import ua.railian.mvi.sample.feature.auth.presentation.common.component.EmailField
import ua.railian.mvi.sample.feature.auth.presentation.common.component.LoadingButton
import ua.railian.mvi.sample.feature.auth.presentation.common.component.MviSampleLargeTopAppBar
import ua.railian.mvi.sample.feature.auth.presentation.forgotpassword.ForgotPasswordMviModel.Action
import ua.railian.mvi.sample.feature.auth.presentation.forgotpassword.ForgotPasswordMviModel.Intent
import ua.railian.mvi.sample.feature.auth.presentation.forgotpassword.ForgotPasswordMviModel.State
import ua.railian.mvi.sample.ui.dialog.DialogAction
import ua.railian.mvi.sample.ui.dialog.DialogHost
import ua.railian.mvi.sample.ui.dialog.DialogHostState
import ua.railian.mvi.sample.ui.dialog.DialogPreview
import ua.railian.mvi.sample.ui.dialog.DialogVisuals
import ua.railian.mvi.sample.ui.theme.MviSampleTheme

@Composable
fun ForgotPasswordScreen(
    onNavigateBack: () -> Unit,
    mviModel: ForgotPasswordMviModel = koinViewModel(),
) {
    ForgotPasswordScreen(
        state = mviModel.renderMviState(),
        process = mviModel::processAsync,
        onNavigateBack = onNavigateBack,
    )
    val dialogHostState = remember { DialogHostState() }
    mviModel.collectMviActions { action ->
        when (action) {
            is Action.InstructionsSent -> dialogHostState.showDialog(
                instructionsSentDialog(onNavigateBack)
            )
        }
    }
    DialogHost(dialogHostState)
}

@Suppress("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun ForgotPasswordScreen(
    state: State,
    process: (Intent) -> Unit = {},
    onNavigateBack: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            MviSampleLargeTopAppBar(
                title = "Forgot password",
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
            EmailField(
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.loading,
                error = state.emailError?.message,
                value = state.email,
                onValueChange = { process(Intent.ChangeEmail(it)) },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = { process(Intent.SendInstructions) },
                ),
            )
            LoadingButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Send instructions",
                enabled = !state.loading,
                loading = state.loading,
                onClick = { process(Intent.SendInstructions) },
            )
        }
    }
}

//region Dialogs
private fun instructionsSentDialog(
    onConfirm: () -> Unit = {},
) = DialogVisuals.Alert(
    title = "Forgot password",
    text = "Instructions to reset your password have been sent to your email.",
    confirm = DialogAction(
        text = "Ok",
        action = onConfirm,
    ),
)
//endregion

//region UI mappers
private val ForgotPasswordError.Email.message: String
    get() = when (this) {
        ForgotPasswordError.Email.Blank -> "Email cannot be blank."
        ForgotPasswordError.Email.DoesNotExist -> "Email does not exist."
    }
//endregion

//region Previews
@PreviewLightDark
@Composable
private fun ForgotPasswordScreenPreview() {
    MviSampleTheme {
        ForgotPasswordScreen(
            state = State(email = "jhon.doe@gmail.com")
        )
    }
}

@PreviewLightDark
@Composable
private fun InstructionsSentDialogPreview() {
    MviSampleTheme {
        DialogPreview(instructionsSentDialog())
    }
}
//endregion
