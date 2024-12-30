package ua.railian.mvi.sample.ui.dialog

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import ua.railian.mvi.sample.ui.theme.MviSampleTheme

@Composable
fun Dialog(
    dialogData: DialogData,
    modifier: Modifier = Modifier,
) {
    when (val visuals = dialogData.visuals) {
        is DialogVisuals.Alert -> AlertDialog(
            modifier = modifier,
            title = { Text(visuals.title) },
            text = { Text(visuals.text) },
            onDismissRequest = { dialogData.dismiss() },
            confirmButton = {
                TextButton(
                    onClick = {
                        visuals.confirm.action()
                        dialogData.dismiss()
                    },
                    content = { Text(visuals.confirm.text) },
                )
            },
            dismissButton = visuals.dismiss?.let {
                {
                    TextButton(
                        onClick = {
                            visuals.dismiss.action()
                            dialogData.dismiss()
                        },
                        content = { Text(visuals.dismiss.text) },
                    )
                }
            }
        )
    }
}

@Composable
fun DialogPreview(
    visuals: DialogVisuals,
    modifier: Modifier = Modifier,
    background: @Composable () -> Unit = {},
) {
    Surface(modifier = modifier.fillMaxSize()) {
        background()
        Dialog(
            dialogData = remember(visuals) {
                object : DialogData {
                    override val visuals = visuals
                    override fun dismiss() = Unit
                }
            }
        )
    }
}

//region Previews
@PreviewLightDark
@Composable
private fun AlertDialogPreview() {
    MviSampleTheme {
        DialogPreview(
            DialogVisuals.Alert(
                title = "Well done!",
                text = "You successfully read this important message.",
                confirm = DialogAction(text = "Ok"),
            )
        )
    }
}
//endregion
