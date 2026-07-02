package com.example.productcatalog.products.data

import com.example.productcatalog.data.ProductsApi
import com.example.productcatalog.products.domain.ProductsModel
import com.example.productcatalog.products.domain.ProductsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ProductsRepositoryImpl(
    private val api: ProductsApi,
    private val ioDispatcher: CoroutineDispatcher
): ProductsRepository {
    override fun getAllProductsFlow(): Flow<Result<List<ProductsModel>>> = flow {
        val result = try {
            val products = api.getProducts().map { dto ->
                ProductsModel(
                    id = dto.id,
                    name = dto.name,
                    category = dto.category,
                    priceInCents = dto.priceInCents
                )
            }
            Result.success(products)
        } catch (e: Exception) {
            Result.failure(e)
        }
        emit(result)
    }.flowOn(ioDispatcher)
}