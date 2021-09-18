package viewModel

import model.MovieData

sealed class AppAction {
    data class Success(val movieData: MovieData) : AppAction()
    data class Error(val error: Throwable) : AppAction()
    object Loading : AppAction()
}