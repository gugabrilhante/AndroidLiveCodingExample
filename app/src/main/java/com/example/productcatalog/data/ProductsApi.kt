package com.example.productcatalog.data

interface ProductsApi {
    suspend fun getProducts(): List<ProductDto>
}
