package view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moviedb.R
import com.example.moviedb.databinding.DescriptionFragmentBinding
import model.MovieDTO
import model.MovieData
import model.MovieLoader


private const val ARG = "param1"


class DescriptionFragment : Fragment() {


    //  private var movieData: MovieData? = null

    companion object {

        fun newInstance(movieData: MovieData) =
            DescriptionFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG, movieData)
                }
            }
    }


    private var _binding: DescriptionFragmentBinding? = null
    private val binding get() = _binding!!

    private val onLoadListener: MovieLoader.MovieLoaderListener =
        object : MovieLoader.MovieLoaderListener {

            override fun onLoaded(weatherDTO: MovieDTO) {
                displayMovie(weatherDTO)
            }

            override fun onFailed(throwable: Throwable,movieID:Int) {


                val fragment = Error.newInstance(movieID)
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.container, fragment)
                    .commit()

            }

            override fun onFailed(throwable: Throwable) {
                requireActivity().supportFragmentManager.popBackStack();
                val fragment = Error.newInstance()

                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.container, fragment)
                    .commit()

            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DescriptionFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieData = arguments?.getParcelable<MovieData>(ARG)
        if (movieData != null) {
            binding.loadingLayout.visibility = View.GONE
            //  binding.Name.text = movieData.title
            binding.genre.text = movieData.genre
            val loader = MovieLoader(movieData.id, onLoadListener)
            loader.toInternet()
        }


    }

    fun displayMovie(movie: MovieDTO) {
        binding.Name.text = movie.title
        binding.synopsis.text = movie.overview
        binding.rating.text = movie.vote_average.toString()
        binding.year.text = movie.release_date.dropLast(6)
    }

//    fun toInternet() {
//
//        val url =
//            URL("https://api.themoviedb.org/3/movie/100?api_key=43bc1342e84cdac28c1e4d846b7d8be6&language=ru-RU")
//        val handler = Handler()
//        Thread(Runnable {
////        val mainHandler = Handler(Looper.getMainLooper())
////        val handlerThread = HandlerThread("HandlerThread")
////        handlerThread.start()
//
////        val backgroundHandler = Handler(handlerThread.looper, Handler.Callback {
//
//        var urlConnection = url.openConnection() as HttpsURLConnection
//        urlConnection.requestMethod = "GET"
//        urlConnection.connectTimeout = 1000
//        val inf = BufferedReader(InputStreamReader(urlConnection.inputStream))
//        val result = inf.lines().collect(Collectors.joining("/n"))
//        val movieDTO: MovieDTO = Gson().fromJson(result, MovieDTO::class.java)
//        handler.post{displayMovie(movieDTO)}
//        //   requireActivity().runOnUiThread {
//
//        //}
//
//        //           mainHandler.post {
//        //           displayMovie(movieDTO)
//    }).start()
//
//    //         true
//    //    })
//}


//    private fun renderData(data: AppAction) {
//        when (data) {
//            is AppAction.Success -> {
//                val movieData = data.movieData
//                binding.loadingLayout.visibility = View.GONE
//                binding.Name.text = movieData.title
//                binding.genre.text = movieData.genre
//                binding.synopsis.text = movieData.synopsis
//                binding.rating.text = "${(movieData.rating)} из 5"
//                binding.year.text = (movieData.year).toString()
//                Snackbar.make(binding.descriptionMovieView, "Success", Snackbar.LENGTH_LONG).show()
//            }
//            is AppAction.Loading -> {
//                binding.loadingLayout.visibility = View.VISIBLE
//            }
//            is AppAction.Error -> {
//                binding.loadingLayout.visibility = View.GONE
//                Snackbar
//                    .make(binding.descriptionMovieView, "Error", Snackbar.LENGTH_INDEFINITE)
//                    .setAction("Reload") { viewModel.getMovieFromLocalSource() }
//                    .show()
//            }
//        }
//    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}

