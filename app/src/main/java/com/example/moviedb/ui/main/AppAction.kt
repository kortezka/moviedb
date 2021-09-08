package com.example.moviedb.ui.main

sealed class AppAction {
        data class Success(val movieData: MovieData) : AppAction()
        data class Error(val error: Throwable) : AppAction()
        object Loading : AppAction()
}