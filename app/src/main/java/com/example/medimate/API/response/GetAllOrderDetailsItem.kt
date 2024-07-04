package com.example.medimate.API.response

data class GetAllOrderDetailsItem(
    val category: String,
    val date_of_craete_order: String,
    val id: Int,
    val isApproved: String,
    val phone: String,
    val product_id: String,
    val product_name: String,
    val quantity: Int,
    val status: Int,
    val total_amount: Double,
    val user_address: String,
    val user_id: String,
    val user_name: String
)