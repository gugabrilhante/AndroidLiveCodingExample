package com.example.productcatalog.products.presentation

import com.example.productcatalog.products.domain.ProductsModel
import com.example.productcatalog.products.domain.ProductsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductListViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private val sampleProducts = listOf(
        ProductsModel(id = "1", name = "Keyboard", category = "Accessories", priceInCents = 5999),
        ProductsModel(id = "2", name = "Mouse", category = "Accessories", priceInCents = 2999),
    )

    @Test
    fun `initial state should have isLoading true`() = runTest {
        val repository = object : ProductsRepository {
            override fun getAllProductsFlow(): Flow<Result<List<ProductsModel>>> = flow { /* never emits */ }
        }

        val viewModel = ProductListViewModel(repository)

        assertTrue(viewModel.state.value.isLoading)
    }

    @Test
    fun `state should not be loading after success`() = runTest {
        val viewModel = ProductListViewModel(FakeProductsRepository(Result.success(sampleProducts)))

        advanceUntilIdle()

        assertFalse(viewModel.state.value.isLoading)
    }

    @Test
    fun `state should contain mapped products on success`() = runTest {
        val viewModel = ProductListViewModel(FakeProductsRepository(Result.success(sampleProducts)))

        advanceUntilIdle()

        val products = viewModel.state.value.products
        assertEquals(2, products.size)
        assertEquals("1", products[0].id)
        assertEquals("Keyboard", products[0].name)
        assertEquals("Accessories", products[0].category)
        assertEquals(5999, products[0].priceInCents)
    }

    @Test
    fun `state should not be loading after failure`() = runTest {
        val viewModel = ProductListViewModel(FakeProductsRepository(Result.failure(RuntimeException("Error"))))

        advanceUntilIdle()

        assertFalse(viewModel.state.value.isLoading)
    }

    @Test
    fun `state should have empty products list on failure`() = runTest {
        val viewModel = ProductListViewModel(FakeProductsRepository(Result.failure(RuntimeException("Error"))))

        advanceUntilIdle()

        assertEquals(emptyList<ProductUiItem>(), viewModel.state.value.products)
    }

    private class FakeProductsRepository(
        private val result: Result<List<ProductsModel>>,
    ) : ProductsRepository {
        override fun getAllProductsFlow(): Flow<Result<List<ProductsModel>>> = flow {
            emit(result)
        }
    }
}
