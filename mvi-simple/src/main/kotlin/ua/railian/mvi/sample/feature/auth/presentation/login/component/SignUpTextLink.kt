package ua.railian.mvi.sample.feature.auth.presentation.login.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withLink
import androidx.compose.ui.tooling.preview.PreviewLightDark
import ua.railian.mvi.sample.ui.theme.MviSampleTheme

@Composable
fun SignUpTextLink(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val linkColor = MaterialTheme.colorScheme.primary
    val currentOnLinkClick by rememberUpdatedState(onClick)

    Text(
        modifier = modifier
            .alpha(if (enabled) 1f else 0.3f),
        text = remember(enabled) {
            buildAnnotatedString {
                append("Don't have an account? ")
                withLink(
                    link = LinkAnnotation.Clickable(
                        tag = "signup",
                        styles = TextLinkStyles(
                            style = SpanStyle(
                                color = linkColor,
                                fontWeight = FontWeight.Bold,
                            ),
                        ),
                        linkInteractionListener = {
                            if (enabled) currentOnLinkClick()
                        },
                    ),
                    block = { append("Sign up!") }
                )
            }
        },
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyMedium,
    )

}

//region Previews
@PreviewLightDark
@Composable
private fun SignUpTextLinkPreview() {
    MviSampleTheme {
        SignUpTextLink(
            modifier = Modifier.fillMaxWidth(),
            onClick = {},
        )
    }
}
//endregion
