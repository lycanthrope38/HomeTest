package com.hometest.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.hometest.R
import com.hometest.theme.HomeTestTheme


@Composable
fun UserItem(
    avatarUrl: String,
    name: String,
    onClick: () -> Unit = {},
    info: @Composable () -> Unit = {},
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        )
    ) {
        Row(
            modifier = Modifier
                .background(color = Color.White)
                .padding(16.dp)
                .fillMaxWidth()
                .clickable {
                    onClick()
                }
        ) {

            Box(
                modifier = Modifier
                    .size(96.dp)
                    .background(
                        shape = RoundedCornerShape(10.dp),
                        color = colorResource(R.color.cl_D3D3D3),
                    ),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(avatarUrl)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(shape = CircleShape)
                )
            }

            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(
                    text = name,
                    style = HomeTestTheme.typography.title,
                )

                HorizontalDivider(modifier = Modifier.padding(top = 8.dp), thickness = 0.5.dp)

                info()
            }

        }
    }

}


@Preview(showBackground = true)
@Composable
private fun UserItemPreview() {
    HomeTestTheme {
        UserItem(
            name = "login",
            avatarUrl = "avatarUrl",
        )
    }
}