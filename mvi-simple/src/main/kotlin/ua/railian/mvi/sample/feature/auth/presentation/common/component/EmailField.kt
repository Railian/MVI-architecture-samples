package ua.railian.mvi.sample.feature.auth.presentation.common.component

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import ua.railian.mvi.sample.ui.theme.MviSampleTheme

@Composable
internal fun EmailField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    error: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    TextField(
        modifier = modifier,
        enabled = enabled,
        value = value,
        onValueChange = onValueChange,
        label = { Text("Email") },
        isError = error != null,
        supportingText = {
            if (error != null) {
                Text(text = error)
            }
        },
        singleLine = true,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
    )
}

//region Previews
@PreviewLightDark
@Composable
private fun EmailFieldPreview() {
    MviSampleTheme {
        var email by remember { mutableStateOf("yevhen.railian@gmail.com") }
        EmailField(
            value = email,
            onValueChange = { email = it },
        )
    }
}

@PreviewLightDark
@Composable
private fun EmailFieldErrorPreview() {
    MviSampleTheme {
        var email by remember { mutableStateOf("") }
        var error by remember { mutableStateOf<String?>("email is required") }
        EmailField(
            value = email,
            onValueChange = {
                email = it
                error = null
            },
            error = error
        )
    }
}
//endregion
