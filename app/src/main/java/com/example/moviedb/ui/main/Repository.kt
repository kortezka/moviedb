package com.example.moviedb.ui.main


interface Repository {
    fun getMovieFromServer(): MovieData
    fun getMovieFromLocalStorage(): MovieData
}