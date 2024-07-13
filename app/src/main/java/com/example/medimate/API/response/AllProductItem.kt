package com.example.medimate.API.response

data class AllProductItem(
    val category: String,
    val certified: Int,
    val id: Int,
    val name: String,
    val price: Double,
    val stock: Int
)