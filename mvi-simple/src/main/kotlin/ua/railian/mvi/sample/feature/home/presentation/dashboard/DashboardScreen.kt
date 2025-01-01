package ua.railian.mvi.sample.feature.home.presentation.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import ua.railian.architecture.mvi.compose.collectMviActions
import ua.railian.architecture.mvi.compose.renderMviState
import ua.railian.mvi.sample.feature.auth.presentation.common.component.LoadingButton
import ua.railian.mvi.sample.feature.auth.presentation.common.component.MviSampleLargeTopAppBar
import ua.railian.mvi.sample.feature.home.presentation.dashboard.DashboardMviModel.Action
import ua.railian.mvi.sample.feature.home.presentation.dashboard.DashboardMviModel.Intent
import ua.railian.mvi.sample.feature.home.presentation.dashboard.DashboardMviModel.State
import ua.railian.mvi.sample.ui.dialog.DialogAction
import ua.railian.mvi.sample.ui.dialog.DialogPreview
import ua.railian.mvi.sample.ui.dialog.DialogVisuals
import ua.railian.mvi.sample.ui.dialog.LocalDialogHostDelegate
import ua.railian.mvi.sample.ui.dialog.error
import ua.railian.mvi.sample.ui.theme.MviSampleTheme

@Composable
fun DashboardScreen(
    onLoggedOut: () -> Unit,
    mviModel: DashboardMviModel = koinViewModel(),
) {
    val dialogHostDelegate = LocalDialogHostDelegate.current
    DashboardScreen(
        state = mviModel.renderMviState(),
        onLogout = {
            dialogHostDelegate.showDialog(
                logoutConfirmationDialog(
                    onConfirm = { mviModel.processAsync(Intent.Logout) }
                ),
            )
        }
    )
    mviModel.collectMviActions { action ->
        when (action) {
            is Action.LoggedOut -> onLoggedOut()
            is Action.Error -> dialogHostDelegate.showDialog(
                DialogVisuals.error(action.message)
            )
        }
    }
}

@Composable
private fun DashboardScreen(
    state: State,
    onLogout: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            MviSampleLargeTopAppBar(title = "Dashboard")
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .navigationBarsPadding(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    space = 16.dp,
                    alignment = Alignment.Bottom,
                ),
            ) {
                LoadingButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Logout",
                    enabled = !state.loading,
                    loading = state.loading,
                    onClick = onLogout,
                )
            }
        }
    ) { innerPadding ->
        Spacer(modifier = Modifier.padding(innerPadding))
    }
}

//region Dialogs
private fun logoutConfirmationDialog(
    onConfirm: () -> Unit = {},
) = DialogVisuals.Alert(
    title = "Logout",
    text = "Are you sure you want to logout?",
    confirm = DialogAction(
        text = "Logout",
        action = onConfirm,
    ),
    dismiss = DialogAction(
        text = "Cancel",
    )
)
//endregion

//region Previews
@PreviewLightDark
@Composable
private fun DashboardScreenPreview() {
    MviSampleTheme {
        DashboardScreen(
            state = State(),
        )
    }
}

@PreviewLightDark
@Composable
private fun LogoutConfirmationDialogPreview() {
    MviSampleTheme {
        DialogPreview(logoutConfirmationDialog())
    }
}
//endregion
