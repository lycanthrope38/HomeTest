package com.hometest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.hometest.home.home
import com.hometest.home.homeRoute
import com.hometest.theme.HomeTestTheme
import com.hometest.userdetail.userDetail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        enableEdgeToEdge()
        setContent {
            HomeTestTheme {
                val navigationController = rememberNavController()
                val snackState: SnackbarHostState = remember { SnackbarHostState() }

                NavHost(
                    navController = navigationController,
                    startDestination = homeRoute
                ) {
                    home(navController = navigationController, snackState = snackState)

                    userDetail(navController = navigationController, snackState = snackState)
                }
            }
        }
    }
}