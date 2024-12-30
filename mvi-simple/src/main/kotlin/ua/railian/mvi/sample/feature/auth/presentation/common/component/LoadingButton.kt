package ua.railian.mvi.sample.feature.auth.presentation.common.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import ua.railian.mvi.sample.ui.theme.MviSampleTheme

@Composable
fun LoadingButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
) {
    Button(
        modifier = modifier.height(48.dp),
        enabled = enabled,
        onClick = onClick,
    ) {
        when {
            !loading -> Text(text = text)
            else -> CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = LocalContentColor.current,
                strokeWidth = 2.dp,
            )
        }
    }
}

//region Previews
@PreviewLightDark
@Composable
private fun LoadingButtonPreview() {
    MviSampleTheme {
        LoadingButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Login",
            onClick = {},
        )
    }
}

@PreviewLightDark
@Composable
private fun LoadingButtonLoadingPreview() {
    MviSampleTheme {
        LoadingButton(
            modifier = Modifier.fillMaxWidth(),
            loading = true,
            text = "Login",
            onClick = {},
        )
    }
}

@PreviewLightDark
@Composable
private fun LoadingButtonDisabledPreview() {
    MviSampleTheme {
        LoadingButton(
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
            text = "Login",
            onClick = {},
        )
    }
}
//endregion
