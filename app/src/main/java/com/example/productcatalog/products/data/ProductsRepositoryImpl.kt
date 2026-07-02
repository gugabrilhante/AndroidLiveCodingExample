package com.example.productcatalog.products.data

import com.example.productcatalog.products.data.datasource.ProductsCacheDataSource
import com.example.productcatalog.products.data.datasource.ProductsRemoteDataSource
import com.example.productcatalog.products.domain.ProductsModel
import com.example.productcatalog.products.domain.ProductsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ProductsRepositoryImpl(
    private val remoteDataSource: ProductsRemoteDataSource,
    private val cacheDataSource: ProductsCacheDataSource,
    private val ioDispatcher: CoroutineDispatcher,
) : ProductsRepository {

    override fun getAllProductsFlow(): Flow<Result<List<ProductsModel>>> = flow {
        val result = try {
            if (!cacheDataSource.isEmpty()) {
                Result.success(cacheDataSource.getAll())
            } else {
                val products = remoteDataSource.getProducts()
                cacheDataSource.saveAll(products)
                Result.success(products)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
        emit(result)
    }.flowOn(ioDispatcher)
}