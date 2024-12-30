package ua.railian.mvi.sample.ui.dialog

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Stable
sealed interface DialogVisuals {

    data class Alert(
        val title: String,
        val text: String,
        val confirm: DialogAction,
        val dismiss: DialogAction? = null,
    ) : DialogVisuals

    companion object
}

@Immutable
data class DialogAction(
    val text: String,
    val action: () -> Unit = {},
)

fun DialogVisuals.Companion.error(
    text: String,
    title: String = "Error",
    confirm: DialogAction = DialogAction(text = "Ok"),
) = DialogVisuals.Alert(
    title = title,
    text = text,
    confirm = confirm,
)
