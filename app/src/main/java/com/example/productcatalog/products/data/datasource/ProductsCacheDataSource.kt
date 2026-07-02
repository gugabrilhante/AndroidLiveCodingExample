package com.example.productcatalog.products.data.datasource

import com.example.productcatalog.products.domain.ProductsModel

interface ProductsCacheDataSource {
    fun getAll(): List<ProductsModel>
    fun saveAll(products: List<ProductsModel>)
    fun isEmpty(): Boolean
    fun clear()
}
