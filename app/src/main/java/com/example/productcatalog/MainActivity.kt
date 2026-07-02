package com.example.productcatalog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.productcatalog.data.ProductsApi
import com.example.productcatalog.ui.ProductCatalogStarterApp
import com.example.productcatalog.ui.theme.ProductCatalogTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val productsApi: ProductsApi by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ProductCatalogTheme {
                ProductCatalogStarterApp(
                    productsApi = productsApi,
                )
            }
        }
    }
}
