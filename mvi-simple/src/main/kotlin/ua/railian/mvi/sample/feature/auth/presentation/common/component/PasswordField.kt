package ua.railian.mvi.sample.feature.auth.presentation.common.component

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.PreviewLightDark
import ua.railian.mvi.sample.ui.theme.MviSampleTheme

@Composable
internal fun PasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Password",
    enabled: Boolean = true,
    error: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    var isPasswordVisible by remember { mutableStateOf(false) }
    TextField(
        modifier = modifier,
        enabled = enabled,
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        visualTransformation = when {
            isPasswordVisible -> VisualTransformation.None
            else -> PasswordVisualTransformation()
        },
        trailingIcon = {
            PasswordVisibilityButton(
                isVisible = isPasswordVisible,
                onVisibilityChanged = { isPasswordVisible = it },
            )
        },
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

@Composable
private fun PasswordVisibilityButton(
    isVisible: Boolean,
    onVisibilityChanged: (Boolean) -> Unit,
) {
    IconButton(onClick = { onVisibilityChanged(!isVisible) }) {
        Icon(
            imageVector = when {
                isVisible -> Icons.Filled.Visibility
                else -> Icons.Filled.VisibilityOff
            },
            contentDescription = when {
                isVisible -> "Hide password"
                else -> "Show password"
            },
        )
    }
}

//region Previews
@PreviewLightDark
@Composable
private fun PasswordFieldPreview() {
    MviSampleTheme {
        var password by remember { mutableStateOf("123456") }
        PasswordField(
            value = password,
            onValueChange = { password = it },
        )
    }
}

@PreviewLightDark
@Composable
private fun PasswordFieldErrorPreview() {
    MviSampleTheme {
        var password by remember { mutableStateOf("") }
        var error by remember { mutableStateOf<String?>("password is required") }
        PasswordField(
            value = password,
            onValueChange = {
                password = it
                error = null
            },
            error = error
        )
    }
}
//endregion
