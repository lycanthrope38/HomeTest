# HomeTest

## Introduction
This project follows **Clean Architecture** and is built with **Jetpack Compose**

## Module Architecture
### App
This module manages the user interface components of the application
- Implements the Model-View-ViewModel (MVVM) pattern to clearly separate concerns and provide an efficient data-binding solution (StateFlow)
- Integrates Paging3 to handle data loading in manageable chunks
- Uses the Navigation Component from Jetpack Compose to seamlessly manage app navigation
- Implements a single-activity architecture with multiple fragments, aligning with modern Android development practices

### Domain
This module contains the business logic models of the application
- Entities: Core data structures representing the business models within the application
    - UserDetail
    - UserInfo
- Use Cases: Encapsulate the application-specific use cases and are responsible for implementing the business logic
    - GetUserDetailUseCase
    - GetUsersUseCase
- Repositories: Define interfaces for obtaining and managing data, regardless of the source (e.g., remote APIs, local databases)
    - UserRepository

### Data
This module contains repository implementations
- Data Source (Remote + Local): Responsible for fetching data from network APIs and handling local data storage for offline access and caching
    - UserDataSource
    - UserDataSourceImpl
- Repository Implementations: Concrete implementations of the repository interfaces defined in the Domain module
    - UserRepositoryImpl

## Main Libraries Used
- **Jetpack Compose**: Modern toolkit for building native UIs
- **Paging3**: Load and display small chunks of data at a time
- **Hilt**: Provides an easier way to incorporate Dependency Injection into the Android application
- **Room**: Accesses the app's SQLite database
  ...

## Unit Test
Unit tests for some common cases: ViewModels, Data layer, and Domain layer
- ViewModels: HomeViewModelTest, UserDetailViewModelTest
- Data layer:
    - / androidTest: UserDaoTest, UserRepositoryImplTest (includes tests for PagingSource)
    - / test: UserDataSourceImplTest, UserMapperTest, UserRemoteMediatorTest, UserRepositoryImplTest
- Domain layer:
    - GetUserDetailUseCaseTest, GetUsersUseCaseTest

## Demo App
![](screenshot/demo.gif)  