# Android Live Coding Challenge — Product Catalog

## Context

This repository contains a working Android application and a fake API that returns a list of products.

Your task is to build a product catalog feature from this starting point.

## Technical environment

- Kotlin
- Android Studio
- Jetpack Compose
- Coroutines
- A fake API provided in the project

You may use the Android Jetpack libraries already configured in the project.

## Mandatory requirements

The application must:

1. Load products when the screen opens.
2. Show a loading state while the request is running.
3. Show the product list when the request succeeds.
4. Show an empty state when no products are returned.
5. Show an error state when the request fails.
6. Allow the user to retry after a failure.
7. Keep loading and business logic outside visual UI components.
8. Include at least one automated test.

Each product must display:

- Name
- Category
- Price

## Expectations

- Explain your reasoning while you work.
- Ask questions when a requirement is unclear.
- Prioritize a working and testable solution.
- Run the application during development.
- Use official documentation and IDE autocomplete when useful.

You are not expected to create a production-complete application during the interview.

## Project setup

Requirements:

- Android Studio with Android SDK 36 installed
- JDK 17

Run the project:

1. Open the repository in Android Studio.
2. Wait for Gradle synchronization to finish.
3. Select an emulator or connected Android device.
4. Run the `app` configuration.

## Starting points

- Fake API: `data/FakeProductsApi.kt`
- API contract: `data/ProductsApi.kt`
- Product model: `data/ProductDto.kt`
- Initial screen: `ui/ProductCatalogStarterApp.kt`

The fake API supports success, empty, and error scenarios.
# AndroidLiveCodingExample
