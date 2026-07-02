package com.example.productcatalog.products.data.datasource

import com.example.productcatalog.products.domain.ProductsModel

class ProductsMemoryCacheDataSource : ProductsCacheDataSource {

    private val cache = HashMap<String, ProductsModel>()

    override fun getAll(): List<ProductsModel> = cache.values.toList()

    override fun saveAll(products: List<ProductsModel>) {
        products.forEach { cache[it.id] = it }
    }

    override fun isEmpty(): Boolean = cache.isEmpty()

    override fun clear() = cache.clear()
}
