package com.example.productcatalog.products.data.datasource

import com.example.productcatalog.data.ProductDto
import com.example.productcatalog.data.ProductsApi
import com.example.productcatalog.products.domain.ProductsModel

class ProductsRemoteDataSourceImpl(
    private val api: ProductsApi,
) : ProductsRemoteDataSource {

    override suspend fun getProducts(): List<ProductsModel> =
        api.getProducts().map { it.toModel() }

    private fun ProductDto.toModel() = ProductsModel(
        id = id,
        name = name,
        category = category,
        priceInCents = priceInCents,
    )
}
