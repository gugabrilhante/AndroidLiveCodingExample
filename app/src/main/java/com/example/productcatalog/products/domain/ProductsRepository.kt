package com.example.productcatalog.products.domain

import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    fun getAllProductsFlow(): Flow<Result<List<ProductsModel>>>
}