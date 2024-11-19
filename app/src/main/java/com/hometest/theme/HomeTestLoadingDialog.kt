package com.hometest.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun HomeTestLoadingDialog(
    title: String = "Loading...",
    onDismiss: () -> Unit = {},
) {
    var isShow by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        isShow = true
    }
    if (isShow) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = false)
        ) {
            Column(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.background,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = CenterHorizontally,
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .width(48.dp),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.background,
                )

                Text(
                    modifier = Modifier.padding(top = 24.dp),
                    text = title, style = HomeTestTheme.typography.body,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeTestLoadingDialogPreview() {
    HomeTestTheme {
        HomeTestLoadingDialog(
            onDismiss = {}
        )
    }
}