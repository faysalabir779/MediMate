package com.example.medimate.API.response

data class GetAllProductItem(
    val category: String,
    val certified: Int,
    val id: Int,
    val name: String,
    val price: Double,
    val products_id: String,
    val stock: Int
)