package model

import android.os.Handler
import android.util.Log
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL

import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class MovieLoader(private val movieId: Int, private val listener: MovieLoaderListener) {


    fun toInternet() {
        try {

            val url =
                URL("https://api.themoviedb.org/3/movie/$movieId?api_key=43bc1342e84cdac28c1e4d846b7d8be6&language=ru-RU")

            internetMagic(url)
        } catch (e: MalformedURLException) {
            Log.e("", "Fail URI", e)
            e.printStackTrace()
            listener.onFailed(e)
        }

    }

    fun internetMagic(url: URL) {
        val handler = Handler()
        Thread {

            var urlConnection = url.openConnection() as HttpsURLConnection
            try {

                urlConnection.requestMethod = "GET"
                urlConnection.connectTimeout = 1000
                val inf = BufferedReader(InputStreamReader(urlConnection.inputStream))
                val result = inf.lines().collect(Collectors.joining("/n"))
                val movieDTO: MovieDTO = Gson().fromJson(result, MovieDTO::class.java)
                handler.post { listener.onLoaded(movieDTO) }
            } catch (e: Exception) {
                Log.e("", "Fail connection", e)
                e.printStackTrace()
                listener.onFailed(e, movieId)
            } finally {
                urlConnection.disconnect()
            }
        }.start()
    }

    interface MovieLoaderListener {
        fun onLoaded(movieDTO: MovieDTO)
        fun onFailed(throwable: Throwable, movieId: Int)
        fun onFailed(throwable: Throwable)

    }

}




