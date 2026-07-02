package com.example.productcatalog.products.domain

data class ProductsModel(
    val id: String,
    val name: String,
    val category: String,
    val priceInCents: Int,
)