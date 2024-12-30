package ua.railian.mvi.sample.ui.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf

@Stable
class DialogHostState {

    private val dialogDataStack = mutableStateListOf<DialogData>()

    val currentDialogData: DialogData?
        get() = dialogDataStack.firstOrNull()

    fun showDialog(visuals: DialogVisuals) {
        dialogDataStack += DialogData(visuals) {
            dialogDataStack -= this
        }
    }
}

@Composable
fun DialogHost(
    hostState: DialogHostState,
    dialog: @Composable (DialogData) -> Unit = { Dialog(it) },
) {
    hostState.currentDialogData?.let { dialog(it) }
}

@Stable
interface DialogData {
    val visuals: DialogVisuals
    fun dismiss()
}

fun DialogData(
    visuals: DialogVisuals,
    onDismiss: DialogData.() -> Unit,
): DialogData = DialogDataImpl(
    visuals = visuals,
    onDismiss = onDismiss,
)

private class DialogDataImpl(
    override val visuals: DialogVisuals,
    private val onDismiss: DialogData.() -> Unit
) : DialogData {
    override fun dismiss() = onDismiss()
}
