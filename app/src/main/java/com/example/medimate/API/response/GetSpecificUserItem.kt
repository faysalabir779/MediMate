package com.example.medimate.API.response

data class GetSpecificUserItem(
    val Address: String,
    val Date_of_account_creation: String,
    val Email: String,
    val Id: Int,
    val Level: Int,
    val Name: String,
    val Password: String,
    val Phone: String,
    val PinCode: String,
    val User_id: String,
    val isApproved: String
)