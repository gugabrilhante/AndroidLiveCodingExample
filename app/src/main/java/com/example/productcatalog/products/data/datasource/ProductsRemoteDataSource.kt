package com.example.productcatalog.products.data.datasource

import com.example.productcatalog.products.domain.ProductsModel

interface ProductsRemoteDataSource {
    suspend fun getProducts(): List<ProductsModel>
}
