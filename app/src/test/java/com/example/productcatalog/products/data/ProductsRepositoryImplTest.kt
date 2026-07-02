package com.example.productcatalog.products.data

import com.example.productcatalog.products.data.datasource.FakeProductsCacheDataSource
import com.example.productcatalog.products.data.datasource.FakeProductsRemoteDataSource
import com.example.productcatalog.products.data.datasource.ProductsRemoteDataSource
import com.example.productcatalog.products.domain.ProductsModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.coroutineContext

@OptIn(ExperimentalCoroutinesApi::class)
class ProductsRepositoryImplTest {

    private val sampleProducts = listOf(
        ProductsModel(id = "1", name = "Keyboard", category = "Accessories", priceInCents = 5999),
        ProductsModel(id = "2", name = "Mouse", category = "Accessories", priceInCents = 2999),
    )

    @Test
    fun `getAllProductsFlow emits success when cache is empty and remote returns products`() = runTest {
        val remote = FakeProductsRemoteDataSource(products = sampleProducts)
        val cache = FakeProductsCacheDataSource()
        val repository = ProductsRepositoryImpl(remote, cache, UnconfinedTestDispatcher())

        val result = repository.getAllProductsFlow().first()

        assertTrue(result.isSuccess)
        assertEquals(2, result.getOrNull()?.size)
    }

    @Test
    fun `getAllProductsFlow saves remote products to cache when cache is empty`() = runTest {
        val remote = FakeProductsRemoteDataSource(products = sampleProducts)
        val cache = FakeProductsCacheDataSource()
        val repository = ProductsRepositoryImpl(remote, cache, UnconfinedTestDispatcher())

        repository.getAllProductsFlow().first()

        assertFalse(cache.isEmpty())
        assertEquals(2, cache.getAll().size)
    }

    @Test
    fun `getAllProductsFlow returns cached products without calling remote`() = runTest {
        val remote = FakeProductsRemoteDataSource(products = emptyList())
        val cache = FakeProductsCacheDataSource().also { it.saveAll(sampleProducts) }
        val repository = ProductsRepositoryImpl(remote, cache, UnconfinedTestDispatcher())

        val result = repository.getAllProductsFlow().first()

        assertTrue(result.isSuccess)
        assertEquals(0, remote.callCount)
        assertEquals(2, result.getOrNull()?.size)
    }

    @Test
    fun `getAllProductsFlow emits failure when remote throws exception`() = runTest {
        val remote = FakeProductsRemoteDataSource(shouldThrow = true)
        val cache = FakeProductsCacheDataSource()
        val repository = ProductsRepositoryImpl(remote, cache, UnconfinedTestDispatcher())

        val result = repository.getAllProductsFlow().first()

        assertTrue(result.isFailure)
    }

    @Test
    fun `getAllProductsFlow should run on provided dispatcher`() = runTest {
        val testDispatcher = StandardTestDispatcher(testScheduler)
        var capturedDispatcher: CoroutineDispatcher? = null
        val remote = object : ProductsRemoteDataSource {
            override suspend fun getProducts(): List<ProductsModel> {
                capturedDispatcher = coroutineContext[ContinuationInterceptor] as CoroutineDispatcher
                return emptyList()
            }
        }
        val cache = FakeProductsCacheDataSource()
        val repository = ProductsRepositoryImpl(remote, cache, testDispatcher)

        repository.getAllProductsFlow().first()

        assertTrue("Expected testDispatcher", testDispatcher === capturedDispatcher)
    }
}
