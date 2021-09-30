package view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.moviedb.R
import com.example.moviedb.databinding.DescriptionFragmentBinding
import model.*
import viewModel.AppAction
import java.lang.Thread.sleep
import java.time.Year
import android.text.method.ScrollingMovementMethod


const val DETAILS_INTENT_FILTER = "DETAILS INTENT FILTER"
const val DETAILS_LOAD_RESULT_EXTRA = "LOAD RESULT"
const val DETAILS_INTENT_EMPTY_EXTRA = "INTENT IS EMPTY"
const val DETAILS_RESPONSE_EMPTY_EXTRA = "RESPONSE IS EMPTY"
const val DETAILS_REQUEST_ERROR_EXTRA = "REQUEST ERROR"
const val DETAILS_REQUEST_ERROR_MESSAGE_EXTRA = "REQUEST ERROR MESSAGE"
const val DETAILS_URL_MALFORMED_EXTRA = "URL MALFORMED"
const val DETAILS_RESPONSE_SUCCESS_EXTRA = "RESPONSE SUCCESS"
const val DETAILS_OVERWIEW_EXTRA = "OVERWIEW"
const val DETAILS_RATING_EXTRA = "RATING"
const val DETAILS_YEAR_EXTRA = "YEAR"
const val DETAILS_TITLE_EXTRA = "TITLE"
const val DETAILS_ID_EXTRA = "ID"
private const val PROCESS_ERROR = "Обработка ошибки"

private const val ARG = "ARG"


class DescriptionFragment : Fragment() {

    private val loadResultsReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {

            when (intent.getStringExtra(DETAILS_LOAD_RESULT_EXTRA)) {
                DETAILS_INTENT_EMPTY_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_RESPONSE_EMPTY_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_REQUEST_ERROR_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_REQUEST_ERROR_MESSAGE_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_URL_MALFORMED_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_RESPONSE_SUCCESS_EXTRA -> renderData(

                    MovieDTO(

                        intent.getStringExtra(DETAILS_TITLE_EXTRA),
                        intent.getDoubleExtra(DETAILS_RATING_EXTRA, 0.0),
                        intent.getStringExtra(DETAILS_OVERWIEW_EXTRA),
                        intent.getStringExtra(DETAILS_YEAR_EXTRA),
                        intent.getIntExtra(DETAILS_ID_EXTRA, 0)


                    )
                )

                else -> TODO(PROCESS_ERROR)
            }
        }
    }
    private lateinit var movieData: MovieData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        context?.let {
            LocalBroadcastManager.getInstance(it)
                .registerReceiver(loadResultsReceiver, IntentFilter(DETAILS_INTENT_FILTER))
        }
    }

    private fun getMovie() {
        binding.descriptionMovieView.visibility = View.GONE
        binding.loadingLayout.visibility = View.VISIBLE
        context?.let {
            it.startService(Intent(it, LoadingInternetService::class.java).apply {
                putExtra(
                    MOVIEID_EXTRA,
                    movieData.id
                )

            })
        }
    }

    private fun renderData(movie: MovieDTO) {
        binding.descriptionMovieView.visibility = View.VISIBLE
        binding.loadingLayout.visibility = View.GONE

        val title = movie.title
        val overview = movie.overview
        val rating = movie.vote_average
        val year = movie.release_date?.dropLast(6)
        if (title == null ||
            overview == null ||
            rating == 0.0 ||
            year == null
        ) {
            TODO("Обработка ошибки")
        } else {
           binding.synopsis.movementMethod = ScrollingMovementMethod()
            binding.Name.text = title
            binding.synopsis.text = overview
            binding.rating.text = rating.toString()
            binding.year.text = year
        }

    }

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

        movieData = arguments?.getParcelable(ARG) ?: MovieData()
        //if (movieData != null) {
        //  binding.loadingLayout.visibility = View.GONE
        //binding.genre.text = movieData.genre
        //    val loader = MovieLoader(movieData.id, onLoadListener)
        //     loader.toInternet()
        getMovie()
        //}


    }

//    fun displayMovie(movie: MovieDTO) {
//        binding.Name.text = movie.title
//        binding.synopsis.text = movie.overview
//        binding.rating.text = movie.vote_average.toString()
//        binding.year.text = movie.release_date?.dropLast(6)
//    }


    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null

    }

    override fun onDestroy() {
        context?.let {
            LocalBroadcastManager.getInstance(it).unregisterReceiver(loadResultsReceiver)
        }
        super.onDestroy()
    }
}

