package model


interface Repository {
    fun getMovieFromServer(): MovieData
    fun getMovieFromLocalStorage(): MovieData
}





