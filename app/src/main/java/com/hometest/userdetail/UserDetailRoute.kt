package com.hometest.userdetail

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.hometest.R
import com.hometest.domain.model.UserDetail
import com.hometest.theme.HomeTestLoadingDialog
import com.hometest.theme.HomeTestScaffold
import com.hometest.theme.HomeTestSnackbarVisuals
import com.hometest.theme.HomeTestTheme
import com.hometest.theme.HomeTestToastType
import com.hometest.ui.UserItem

const val userDetailRoute = "user_detail_route/{userName}"

fun NavGraphBuilder.userDetail(
    navController: NavController,
    snackState: SnackbarHostState
) {
    composable(userDetailRoute) {

        val viewModel = hiltViewModel<UserDetailViewModel>()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        LaunchedEffect(uiState.errorMessage) {
            if (uiState.errorMessage.isNotEmpty()) {
                snackState.showSnackbar(
                    HomeTestSnackbarVisuals(
                        type = HomeTestToastType.ERROR,
                        message = uiState.errorMessage,
                    )
                )
                viewModel.onErrorMessageEventConsumed()
            }
        }

        if (uiState.isLoading) {
            HomeTestLoadingDialog(
                onDismiss = {
                    viewModel.onLoadingEventConsumed()
                },
            )
        }

        UserDetailScreen(
            state = uiState,
            snackState = snackState,
        )
    }
}

fun NavController.navigateToUserDetail(
    navOptions: NavOptions? = null,
    userName: String
) {
    navigate("user_detail_route/$userName", navOptions)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailScreen(
    state: UserDetailState,
    snackState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    val onBackPressOwner = LocalOnBackPressedDispatcherOwner.current

    HomeTestScaffold(modifier = modifier.systemBarsPadding(),
        snackState = snackState,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew, contentDescription = "",
                        modifier = Modifier.clickable { onBackPressOwner?.onBackPressedDispatcher?.onBackPressed() }
                    )
                },
                title = {
                    Text(
                        text = stringResource(id = R.string.user_details),
                        modifier = Modifier.fillMaxWidth(),
                        style = HomeTestTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )
                },
            )
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
        ) {

            UserItem(avatarUrl = state.userDetail?.avatarUrl.orEmpty(),
                name = state.userDetail?.login.orEmpty(),
                info = {
                    Row(
                        modifier = Modifier.padding(top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn, contentDescription = "",
                            modifier = Modifier.size(16.dp)
                        )

                        Text(
                            text = state.userDetail?.location.orEmpty(),
                            style = HomeTestTheme.typography.caption,
                        )
                    }
                })

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier.background(
                            color = colorResource(R.color.cl_4DD3D3D3),
                            shape = CircleShape
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Group,
                            contentDescription = "",
                            modifier = Modifier.padding(8.dp)
                        )
                    }

                    Text(
                        text = state.userDetail?.followers?.toString() ?: "0",
                        style = HomeTestTheme.typography.captionTitle,
                    )

                    Text(
                        text = stringResource(id = R.string.following),
                        style = HomeTestTheme.typography.caption,
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier.background(
                            color = colorResource(R.color.cl_4DD3D3D3),
                            shape = CircleShape
                        )
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_following),
                            contentDescription = "",
                            modifier = Modifier
                                .padding(8.dp)
                                .size(24.dp)
                        )
                    }

                    Text(
                        text = state.userDetail?.followers?.toString() ?: "0",
                        style = HomeTestTheme.typography.captionTitle,
                    )

                    Text(
                        text = stringResource(id = R.string.followers),
                        style = HomeTestTheme.typography.caption,
                    )
                }
            }

            Text(
                modifier = Modifier.padding(top = 24.dp),
                text = stringResource(id = R.string.blog),
                style = HomeTestTheme.typography.title,
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = state.userDetail?.htmlUrl.orEmpty(),
                style = HomeTestTheme.typography.bodySmall,
            )

        }
    }
}

@Preview
@Composable
private fun UserDetailScreenPreview() {
    HomeTestTheme {
        UserDetailScreen(
            snackState = SnackbarHostState(),
            state = UserDetailState(
                userDetail = UserDetail(
                    login = "login",
                    avatarUrl = "avatarUrl",
                    location = "location",
                    id = 1,
                    htmlUrl = "htmlUrl",
                    followers = 1,
                    following = 1,
                ),
            )
        )
    }
}
