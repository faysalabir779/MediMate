package com.example.medimate.API.response

data class GetSellHistoryByUserIdItem(
    val date_of_sell: String,
    val id: Int,
    val price: Double,
    val product_id: String,
    val product_name: String,
    val quantity: Int,
    val remaining_stock: Int,
    val sell_id: String,
    val total_amount: Double,
    val user_id: String,
    val user_name: String
)