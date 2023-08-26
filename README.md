# Photo Gallery Demo App

## Download

Download the latest APK from [Releases](https://github.com/andreformosa/photogallery/releases).

## Tech stack and libraries

The app is
following [Modern App Architecture](https://developer.android.com/topic/architecture#modern-app-architecture)
recommendations and guidelines. Due to the simplicity of this app, there was no need for an actual
Domain layer, however this would be encouraged as the app's complexity grows.

- [Android Jetpack](https://developer.android.com/jetpack/androidx/explorer?case=all):
    - [Compose](https://developer.android.com/jetpack/compose) - Android's recommended modern
      toolkit for building native UI.
    - [Lifecycle Compose](https://developer.android.com/jetpack/androidx/releases/lifecycle) -
      Lifecycle-aware components that perform actions in response to a change in the lifecycle
      status of another component.
    - [Navigation Compose](https://developer.android.com/jetpack/compose/navigation) - Provides
      support for navigating between composables while taking advantage of the Navigation
      component's
      infrastructure and features.
    - [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) -
      Helps load and display pages of data from a larger dataset from local storage or over a
      network.
    - [Preferences DataStore](https://developer.android.com/topic/libraries/architecture/datastore) -
      Helps store data asynchronously, consistently, and transactionally.
    - [Room](https://developer.android.com/jetpack/androidx/releases/room) - Helps create, store,
      and manage persistent data backed by a SQLite database.
    - [ViewModel Compose](https://developer.android.com/topic/libraries/architecture/viewmodel#jetpack-compose) -
      A business logic or screen level state holder. It exposes state to the UI and encapsulates
      related business logic.
- [Accompanist Placeholder](https://google.github.io/accompanist/placeholder/) - A library which
  provides a modifier to a display placeholder UI while content is loading.
- [Coil](https://github.com/coil-kt/coil) - An image loading library for Android apps.
- [Chucker](https://github.com/ChuckerTeam/chucker) - Simplifies the inspection of HTTP
  requests/responses fired by an Android app.
- [Hilt](https://dagger.dev/hilt) - Handles dependency injection in Android apps.
- [Kotlin Coroutines & Flow](https://github.com/Kotlin/kotlinx.coroutines) - A library for
  asynchronous and reactive code in Kotlin.
- [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization) - A library for Kotlin
  multi-format reflectionless serialization.
- [Retrofit](https://github.com/square/retrofit) - A type-safe HTTP client for Android and Java.
- [Sandwich](https://github.com/skydoves/Sandwich) - A lightweight and easy-to-use Kotlin library
  for handling Android Retrofit responses.
- [Timber](https://github.com/JakeWharton/timber) - A logger with a small, extensible API providing
  utility on top of Android's normal `Log` class.
- [Zoomable](https://github.com/usuiat/Zoomable) - A Jetpack Compose Modifier extension that enables
  content to be zoomable by pinch gesture, by double-tap, or by double-tap and drag gesture.

## Testing Libraries

- [JUnit](https://github.com/junit-team/junit4) - A simple framework to write repeatable tests.
- [Truth](https://github.com/google/truth) - A fluent assertion library for testing.
- [Turbine](https://github.com/cashapp/turbine) - A testing library for testing
  kotlinx.coroutines `Flow`.

## Wishlist

This is a wishlist of things I would have liked to add if I had more time or if this was an "actual"
app.

- Modularization: Being that this app is quite simple, it is not modularized. However, it is
  structured in a way that modules could easily be extracted.
- Shared test resources: Test resources such as factories and fakes (test doubles) are currently
  duplicated in Unit and Instrumentation tests. A `common-test` module would be a great place to
  have these to enable easy re-use.
- Mapping of Data Models to UI Models: The view layer is using the data entity models directly.
  These would ideally be mapped to UI models to further separate concerns and customize the models
  to fit the UI layer further.
- Advanced Cache Policy: The current caching mechanism is quite basic and does not support an
  advanced cache policy. In an actual app, this would of course be essential.
- Paging support for `PhotosScreen`: For the sake of efficiency and simplicity, the Paging library
  was only used for the `AlbumsScreen`. Of course, a similar implementation for this screen would
  have been a great idea.
- Device Network State Handling: Support for showing and reacting to states where network is down or
  slow would be an essential feature.
- Baseline Profiles: This further improves app performance, especially in Compose.
- More tests: The "essential" tests are provided, which showcase testing different parts of the
  app/layers. Of course, more tests in general and tests for other supporting classes would be
  required in a production app.
- Anti-bikeshedding tools such as Ktlint and Detekt

## Testing

The implemented tests do not use mocks or any mocking libraries and instead rely on fakes (test
doubles) which is
the [recommended](https://developer.android.com/training/testing/fundamentals/test-doubles#types)
practice by Google.

Unit Tests

- `OfflineFirstAlbumsRepositoryTest`
- `PhotosViewModelTest`

To run, simply execute:

`./gradlew testDebugUnitTest`

Instrumentation Tests

- `AlbumsFlowTest`

To run, make sure you're connected to a running emulator/device, and then execute:

`./gradlew connectedAndroidTest`

## Closing Remarks

- The development of this app is estimated to be around 20 hours.
- The main focus was on building a solid foundation that can be further improved upon. Due to time
  constraints, many things that I would otherwise include in an app had to be omitted, but I have
  referred to these in the above wishlist section.
- In terms of design and animations, there was not much room for me to be creative due to the
  dataset.
    - If the response images did not have the same dimensions, a `LazyVerticalStaggeredGrid` would
      have been a better fit for the photos screen.
    - The photo details screen is extremely basic, but there was also not enough data available to
      add functionality such as expanded photo details (similar to Google Photos).
