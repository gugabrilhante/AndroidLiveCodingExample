package com.example.productcatalog.products.data.datasource

import com.example.productcatalog.products.domain.ProductsModel
import java.io.IOException

class FakeProductsRemoteDataSource(
    private val products: List<ProductsModel> = emptyList(),
    private val shouldThrow: Boolean = false,
) : ProductsRemoteDataSource {

    var callCount = 0
        private set

    override suspend fun getProducts(): List<ProductsModel> {
        callCount++
        if (shouldThrow) throw IOException("Network error")
        return products
    }
}
