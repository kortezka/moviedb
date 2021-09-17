package model

import model.MovieData
import model.Repository

class RepositoryImpl: Repository {
    override fun getMovieFromServer(): MovieData {
     return MovieData()
    }

    override fun getMovieFromLocalStorage(): MovieData {
        return MovieData()
    }
}
