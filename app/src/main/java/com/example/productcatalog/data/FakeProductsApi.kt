package com.example.productcatalog.data

import java.io.IOException
import kotlinx.coroutines.delay

class FakeProductsApi(
    private var scenario: FakeApiScenario = FakeApiScenario.SUCCESS,
    private val responseDelayMillis: Long = 1_200L,
) : ProductsApi {

    override suspend fun getProducts(): List<ProductDto> {
        delay(responseDelayMillis)

        return when (scenario) {
            FakeApiScenario.SUCCESS -> sampleProducts
            FakeApiScenario.EMPTY -> emptyList()
            FakeApiScenario.ERROR -> throw IOException("Unable to load products")
        }
    }

    fun changeScenario(newScenario: FakeApiScenario) {
        scenario = newScenario
    }

    private companion object {
        val sampleProducts = listOf(
            ProductDto(
                id = "product-1",
                name = "Mechanical Keyboard",
                category = "Accessories",
                priceInCents = 59_990,
            ),
            ProductDto(
                id = "product-2",
                name = "USB-C Hub",
                category = "Accessories",
                priceInCents = 24_990,
            ),
            ProductDto(
                id = "product-3",
                name = "27-inch Monitor",
                category = "Displays",
                priceInCents = 189_990,
            ),
            ProductDto(
                id = "product-4",
                name = "Ergonomic Mouse",
                category = "Accessories",
                priceInCents = 32_500,
            ),
            ProductDto(
                id = "product-5",
                name = "Laptop Stand",
                category = "Office",
                priceInCents = 18_900,
            ),
        )
    }
}
