package com.example.medimate.navigation

object Routes {
    var SignUp = "SignUp"
    var SignIn = "SignIn"
    var SignUpComplete = "SignUpComplete"
    var Home = "Dashboard"
}

sealed class BottomNavRoutes(var name: String){
    object Dashboard :BottomNavRoutes("Dashboard")
    object Sell :BottomNavRoutes("Sell")
    object Inventory :BottomNavRoutes("Inventory")
    object Profile :BottomNavRoutes("Profile")
}



object OrderRoutes{
    var Approved = "Approved"
    var DeclinedOrder = "DisApproved"
    var NewOrder = "NewOrder"
    var Pending = "Pending"
}

