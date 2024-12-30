package ua.railian.mvi.sample.feature.auth.presentation.login.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import ua.railian.mvi.sample.ui.theme.MviSampleTheme

@Composable
 fun ForgotPasswordButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    TextButton(
        modifier = modifier,
        enabled = enabled,
        onClick = onClick,
    ) {
        Text("Forgot password?")
    }
}

//region Previews
@PreviewLightDark
@Composable
private fun ForgotPasswordButtonPreview() {
    MviSampleTheme {
        ForgotPasswordButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {},
        )
    }
}

@PreviewLightDark
@Composable
private fun ForgotPasswordButtonDisabledPreview() {
    MviSampleTheme {
        ForgotPasswordButton(
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
            onClick = {},
        )
    }
}
//endregion
