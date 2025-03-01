package com.example.medimate.navigation

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.medimate.AllViewModel
import com.example.medimate.R
import com.example.medimate.screen.DashBoard.Home
import com.example.medimate.screen.SignInPage.SignInScreen
import com.example.medimate.screen.SignUpCompleteMsg
import com.example.medimate.screen.SignUpPage.SignUpScreen

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun NavGraph(
    navController: NavHostController,
    allViewModel: AllViewModel,
    applicationContext: Context
) {

    val specificUser = allViewModel.specificUser.value
    val allUser = allViewModel.allUser.value

    val savedData by allViewModel.preferenceData.collectAsState()

    Log.d("NavGraph", "Initial savedData.userId: ${savedData.userId}")

    val isUserExist = allUser.any{it.user_id == savedData.userId}

    var startDestination = if (savedData.userId.isNullOrEmpty() || !isUserExist) {
        Routes.SignIn
    } else {
        Routes.SignUpComplete
    }
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Routes.SignUp) {
            SignUpScreen(navController, allViewModel)
        }
        composable(Routes.SignIn) {
            SignInScreen(navController, allViewModel, applicationContext)
        }
        composable(Routes.SignUpComplete) {
            if (specificUser.isNotEmpty()) {
                specificUser.map {
                    if (it.isApproved == 2) {
                        SignUpCompleteMsg(note = stringResource(id = R.string.waiting_approval))
                    }
                    if (it.isApproved == 1) {
                        Home(allViewModel = allViewModel, applicationContext = applicationContext)
                    }
                    if (it.isApproved == 0) {
                        SignUpCompleteMsg(note = stringResource(id = R.string.blocked_success_text))
                    }
                }
            } else {
                SignUpCompleteMsg(note = "Error")
            }
        }
        composable(Routes.Home) {
            Home(navController, allViewModel, applicationContext)
        }
    }
}
