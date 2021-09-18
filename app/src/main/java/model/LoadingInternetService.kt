package model

import android.app.IntentService
import android.content.Intent

import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.gson.Gson
import view.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

// TODO: Rename actions, choose action names that describe tasks that this
// IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
const val MOVIEID_EXTRA = "movie.id"
private const val REQUEST_GET = "GET"
private const val REQUEST_TIMEOUT = 10000


class LoadingInternetService : IntentService("LoadingInternetService") {

    private val broadcastIntent = Intent(DETAILS_INTENT_FILTER)

    override fun onHandleIntent(intent: Intent?) {

        if (intent == null) {
            onEmptyIntent()
        } else {
            val movie = intent.getIntExtra(MOVIEID_EXTRA, 0)
            loadMovie(movie)
        }
    }

    private fun loadMovie(movie: Int) {

        try {

            val url =
                URL("https://api.themoviedb.org/3/movie/$movie?api_key=43bc1342e84cdac28c1e4d846b7d8be6&language=ru-RU")


            var urlConnection = url.openConnection() as HttpsURLConnection
            try {

                urlConnection.requestMethod = REQUEST_GET
                urlConnection.connectTimeout = REQUEST_TIMEOUT
                val inf = BufferedReader(InputStreamReader(urlConnection.inputStream))
                val result = inf.lines().collect(Collectors.joining("/n"))
                val movieDTO: MovieDTO = Gson().fromJson(result, MovieDTO::class.java)
                onResponse(movieDTO)

            } catch (e: Exception) {
                onErrorRequest(e.message ?: "Empty error")
            } finally {
                urlConnection.disconnect()
            }

        } catch (e: MalformedURLException) {
            onMalformedURL()
        }

    }

    private fun onResponse(movieDTO: MovieDTO) {
        if (movieDTO == null) {
            onEmptyResponse()
        } else {
            onSuccessResponse(
                movieDTO.overview,
                movieDTO.release_date,
                movieDTO.title,
                movieDTO.vote_average,
                movieDTO.id
            )
        }
    }

    private fun onSuccessResponse(
        overview: String?,
        releaseDate: String?,
        title: String?,
        voteAverage: Double?,
        id: Int?
    ) {

        putLoadResult(DETAILS_RESPONSE_SUCCESS_EXTRA)

        broadcastIntent.putExtra(DETAILS_TITLE_EXTRA, title)
        broadcastIntent.putExtra(DETAILS_OVERWIEW_EXTRA, overview)
        broadcastIntent.putExtra(DETAILS_YEAR_EXTRA, releaseDate)
        broadcastIntent.putExtra(DETAILS_RATING_EXTRA, voteAverage)
        broadcastIntent.putExtra(DETAILS_ID_EXTRA, id)

        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)

    }


    private fun onMalformedURL() {
        putLoadResult(DETAILS_URL_MALFORMED_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onErrorRequest(error: String) {
        putLoadResult(DETAILS_REQUEST_ERROR_EXTRA)
        broadcastIntent.putExtra(DETAILS_REQUEST_ERROR_MESSAGE_EXTRA, error)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyResponse() {
        putLoadResult(DETAILS_RESPONSE_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyIntent() {
        putLoadResult(DETAILS_INTENT_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }


    private fun putLoadResult(result: String) {
        broadcastIntent.putExtra(DETAILS_LOAD_RESULT_EXTRA, result)

    }





}
