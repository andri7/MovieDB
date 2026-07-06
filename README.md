# MovieDB App

MovieDB is a native Android application that displays movie information using The Movie Database (TMDB) API.

This project was created as a mobile technical assessment using modern Android development.

## Features

- Show official movie genres
- Discover movies by selected genre
- Movie detail information
- Movie user reviews
- Youtube movie trailer
- Endless scrolling movie list
- Endless scrolling reviews
- Offline first support
- Error handling
- Unit testing


## Tech Stack

- Kotlin
- Jetpack Compose
- Material 3
- MVVM Architecture
- Clean Architecture
- Kotlin Coroutines
- Flow
- Ktor Client
- Kotlin Serialization
- Room Database
- Paging 3
- RemoteMediator
- Koin Dependency Injection
- Coil Image Loader
- JUnit
- MockK


## Architecture


Presentation Layer

- Jetpack Compose
- ViewModel
- UI State


Domain Layer

- Model
- Repository Interface
- UseCase


Data Layer

- Repository Implementation
- Remote Data Source
- Local Database
- Mapper


Flow:


Compose UI

↓

ViewModel

↓

UseCase

↓

Repository

↓

Remote API + Room Database


## Offline First

This application uses Room database as a single source of truth.


Online:

TMDB API

↓

Save into Room

↓

Observe local database

↓

Update UI


Offline:

Network Error

↓

Load cached data from Room

↓

Display existing movies



## API

Data provided by:

The Movie Database (TMDB)

https://www.themoviedb.org/


Used endpoints:

- /genre/movie/list
- /discover/movie
- /movie/{movie_id}
- /movie/{movie_id}/reviews
- /movie/{movie_id}/videos


## Project Structure


com.atf.moviedb


core

- database
- network
- di
- utils


data

- remote
- local
- mapper
- repository


domain

- model
- repository
- usecase


presentation

- genre
- movie
- detail
- navigation



## Screens

### Genre

Display official movie genres.


### Movie List

Display movies based on selected genre with pagination.


### Detail

Display:

- Poster
- Title
- Rating
- Genre
- Synopsis
- Trailer
- Reviews



## Testing

Test coverage:

- UseCase positive case
- UseCase negative case
- ViewModel state test


Libraries:

- JUnit
- MockK
- Coroutine Test
- Turbine



## How To Run

1. Clone repository


git clone YOUR_REPOSITORY_URL


2. Open project using Android Studio


3. Add TMDB API Key


core/utils/Constant.kt


Change:


const val API_KEY = "YOUR_API_KEY"


4. Run Application



## Author

Created by Andrian Triono