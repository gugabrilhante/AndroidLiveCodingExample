package com.example.productcatalog.products.data

import com.example.productcatalog.data.FakeApiScenario
import com.example.productcatalog.data.FakeProductsApi
import com.example.productcatalog.data.ProductDto
import com.example.productcatalog.data.ProductsApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.coroutineContext


@OptIn(ExperimentalCoroutinesApi::class)
class ProductsRepositoryImplTest {

    @Test
    fun `getAllProductsFlow emits success when api returns products`() = runTest{
        //Given
        val mockApi = FakeProductsApi(scenario = FakeApiScenario.SUCCESS, responseDelayMillis = 0)
        val repository = ProductsRepositoryImpl(mockApi, UnconfinedTestDispatcher())

        val result = repository.getAllProductsFlow().first()
        assertTrue(result.isSuccess)
        val products = result.getOrNull()
        assertEquals(5, products?.size)
    }

    @Test
    fun `getAllProductsFlow emits failure when api throw exception`() = runTest {
        //Given
        val mockApi = FakeProductsApi(scenario = FakeApiScenario.ERROR, responseDelayMillis = 0)
        val repository = ProductsRepositoryImpl(mockApi, UnconfinedTestDispatcher())

        val result = repository.getAllProductsFlow().first()
        assertTrue(result.isFailure)
    }

    @Test
    fun `getAllProductsFlow should call api on provided dispatcher`() = runTest {
        // Given
        val testDispatcher = StandardTestDispatcher(testScheduler)
        var capturedDispatcher: CoroutineDispatcher? = null
        val api = object : ProductsApi {
            override suspend fun getProducts(): List<ProductDto> {
                capturedDispatcher = coroutineContext[ContinuationInterceptor] as CoroutineDispatcher
                return emptyList()
            }
        }
        val repository = ProductsRepositoryImpl(api, testDispatcher)

        // When
        repository.getAllProductsFlow().first()

        // Then
        assertTrue("Dispatcher should be the one provided", testDispatcher === capturedDispatcher)
    }

}