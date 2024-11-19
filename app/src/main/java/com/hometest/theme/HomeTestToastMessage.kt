package com.hometest.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hometest.R

@Composable
fun HomeTestSnackBarHost(state: SnackbarHostState) {
    SnackbarHost(hostState = state) { data ->
        val customVisuals = data.visuals as HomeTestSnackbarVisuals
        NcToastMessage(type = customVisuals.type, customVisuals.message)
    }
}

data class HomeTestSnackbarVisuals(
    override val message: String,
    override val actionLabel: String? = null,
    override val withDismissAction: Boolean = false,
    override val duration: SnackbarDuration = if (actionLabel == null) SnackbarDuration.Short else SnackbarDuration.Indefinite,
    val type: HomeTestToastType = HomeTestToastType.SUCCESS,
) : SnackbarVisuals

@Composable
fun NcToastMessage(type: HomeTestToastType = HomeTestToastType.SUCCESS, message: String) {
    val color = when (type) {
        HomeTestToastType.SUCCESS -> colorResource(id = R.color.cl_A7F0BA)
        HomeTestToastType.ERROR -> colorResource(id = R.color.cl_D2232A)
    }
    val iconResId = when (type) {
        HomeTestToastType.SUCCESS -> R.drawable.ic_info
        HomeTestToastType.ERROR -> R.drawable.ic_info_white
    }
    val textColor = when (type) {
        HomeTestToastType.SUCCESS -> colorResource(id = R.color.cl_000000)
        HomeTestToastType.ERROR -> colorResource(id = R.color.cl_FFFFFF)
    }
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .background(color = color, shape = RoundedCornerShape(8.dp))
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier.size(36.dp),
            painter = painterResource(id = iconResId),
            contentDescription = "Icon",
        )
        Text(
            text = message,
            color = textColor,
            modifier = Modifier.padding(start = 8.dp),
            style = HomeTestTheme.typography.titleSmall,
        )
    }
}

enum class HomeTestToastType {
    SUCCESS,
    ERROR,
}