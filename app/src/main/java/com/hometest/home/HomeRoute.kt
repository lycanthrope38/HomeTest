package com.hometest.home

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.hometest.R
import com.hometest.domain.model.UserInfo
import com.hometest.theme.HomeTestScaffold
import com.hometest.theme.HomeTestSnackbarVisuals
import com.hometest.theme.HomeTestTheme
import com.hometest.theme.HomeTestToastType
import com.hometest.ui.UserItem
import com.hometest.userdetail.navigateToUserDetail
import kotlinx.coroutines.flow.flowOf

const val homeRoute = "home_route"

fun NavGraphBuilder.home(
    navController: NavController,
    snackState: SnackbarHostState
) {
    composable(homeRoute) {

        val viewModel = hiltViewModel<HomeViewModel>()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val usersPaging = viewModel.users.collectAsLazyPagingItems()

        LaunchedEffect(key1 = usersPaging.loadState) {
            viewModel.onLoadStateUpdate(usersPaging.loadState)
        }

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

        HomeScreen(
            state = uiState,
            snackState = snackState,
            usersPaging = usersPaging,
        ) {
            navController.navigateToUserDetail(userName = it)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeState,
    snackState: SnackbarHostState,
    usersPaging: LazyPagingItems<UserInfo>,
    modifier: Modifier = Modifier,
    onUserClick: (String) -> Unit = {},
) {
    val onBackPressOwner = LocalOnBackPressedDispatcherOwner.current

    HomeTestScaffold(
        modifier = modifier.systemBarsPadding(),
        snackState = snackState,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "",
                        modifier = Modifier.clickable {
                            onBackPressOwner?.onBackPressedDispatcher?.onBackPressed()
                        }
                    )
                },
                title = {
                    Text(
                        text = "Github Users", modifier = Modifier.fillMaxWidth(),
                        style = HomeTestTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )
                },
            )
        },
        bottomBar = {

        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
        ) {
            if (usersPaging.loadState.refresh is LoadState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(usersPaging.itemCount) {
                        usersPaging[it]?.let { user ->
                            UserItem(
                                avatarUrl = user.avatarUrl,
                                name = user.login,
                                onClick = {
                                    onUserClick(user.login)
                                },
                            ) {
                                Text(
                                    modifier = Modifier.padding(top = 8.dp),
                                    text = user.htmlUrl,
                                    style = HomeTestTheme.typography.body.copy(
                                        color = colorResource(R.color.cl_0000EE),
                                    ),
                                    textDecoration = TextDecoration.Underline,
                                )
                            }
                        }
                    }

                    item {
                        if (usersPaging.loadState.append is LoadState.Loading) {
                            Box(modifier = Modifier.fillMaxWidth()) {
                                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    val users = flowOf(
        PagingData.from(
            listOf(
                UserInfo(
                    id = 1,
                    login = "login",
                    avatarUrl = "avatarUrl",
                    htmlUrl = "htmlUrl"
                ),
            )
        )
    ).collectAsLazyPagingItems()
    HomeTestTheme {
        HomeScreen(
            state = HomeState(),
            snackState = SnackbarHostState(),
            usersPaging = users
        )
    }
}