package com.example.moviedb.ui.main

class RepositoryImpl:Repository {
    override fun getMovieFromServer(): MovieData {
     return MovieData()
    }

    override fun getMovieFromLocalStorage(): MovieData {
        return MovieData()
    }
}
