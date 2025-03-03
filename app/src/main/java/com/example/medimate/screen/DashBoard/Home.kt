package com.example.medimate.screen.DashBoard

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.medimate.AllViewModel
import com.example.medimate.R
import com.example.medimate.navigation.BottomNavRoutes

@Composable
fun Home(
    navHostController: NavHostController = rememberNavController(),
    allViewModel: AllViewModel,
    applicationContext: Context
) {
    var bottomTabs by remember { mutableStateOf(BottomNavRoutes.Dashboard.name) }

    data class BottomNavigation(
        val title: String,
        val icon: Int,
        val routes: String
    )

    val items = listOf(
        BottomNavigation(
            "Dashboard",
            R.drawable.dashboard,
            BottomNavRoutes.Dashboard.name
        ),
        BottomNavigation(
            "Sell",
            R.drawable.coin,
            BottomNavRoutes.Sell.name
        ),
        BottomNavigation(
            "Inventory",
            R.drawable.inventory,
            BottomNavRoutes.Inventory.name
        ),
        BottomNavigation(
            "Profile",
            R.drawable.user,
            BottomNavRoutes.Profile.name
        )
    )
    Scaffold(
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                NavigationBar(
                    modifier = Modifier
                        .fillMaxWidth().navigationBarsPadding().height(50.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        items.forEachIndexed { index, item ->
                            NavigationBarItem(
                                selected = bottomTabs == item.routes,
                                onClick = { bottomTabs = item.routes },
                                modifier = Modifier.background(color = Color.White),
                                colors = NavigationBarItemColors(
                                    selectedIconColor = Color(0xFFFFFFFF),
                                    unselectedIconColor = Color(0xFF000000),
                                    selectedTextColor = Color(0xFF111111),
                                    unselectedTextColor = Color(0xFF111111),
                                    disabledIconColor = Color(0xFF111111),
                                    disabledTextColor = Color(0xFF111111),
                                    selectedIndicatorColor = Color(0xFF000000)
                                ),
                                alwaysShowLabel = true,
                                icon = {
                                    Icon(
                                        painter = painterResource(id = item.icon),
                                        modifier = Modifier.size(30.dp),
                                        contentDescription = item.title
                                    )
                                },
                            )
                        }
                    }
                }
            }
        }
    ) {
        Column (modifier = Modifier.padding(it)){
            Box(
                modifier = Modifier.fillMaxSize()
                    .background(color = Color.White)
            ) {
                when (bottomTabs) {
                    BottomNavRoutes.Dashboard.name -> {
                        Dashboard(allViewModel, applicationContext)
                    }

                    BottomNavRoutes.Sell.name -> {
                        Sell(allViewModel)
                    }

                    BottomNavRoutes.Inventory.name -> {
                        Inventory(allViewModel, applicationContext)
                    }

                    BottomNavRoutes.Profile.name -> {
                        Profile(allViewModel, applicationContext)
                    }
                }
            }
        }
    }
}

