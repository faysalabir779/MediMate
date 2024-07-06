package com.example.medimate.API.response

data class GetAllAvailableProductsItem(
    val category: String,
    val id: Int,
    val price: Double,
    val product_id: String,
    val product_name: String,
    val stock: Int,
    val user_id: String
)