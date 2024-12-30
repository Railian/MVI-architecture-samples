package ua.railian.mvi.sample.feature.auth.presentation.common.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MviSampleLargeTopAppBar(
    title: String,
    onNavigateBack: (() -> Unit)? = null,
) {
    LargeTopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            onNavigateBack?.let {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "Navigate back",
                    )
                }
            }
        }
    )
}
