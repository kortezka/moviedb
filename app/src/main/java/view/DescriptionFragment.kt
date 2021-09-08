package view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.moviedb.databinding.DescriptionFragmentBinding
import viewModel.AppAction
import com.google.android.material.snackbar.Snackbar
import viewModel.MainViewModel

class DescriptionFragment : Fragment() {

    companion object {
        fun newInstance() = DescriptionFragment()
    }

    private lateinit var viewModel: MainViewModel
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

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getMovieFromLocalSource()

    }


    private fun renderData(data: AppAction) {
        when (data) {
            is AppAction.Success -> {
                val movieData = data.movieData
                binding.loadingLayout.visibility = View.GONE
                binding.Name.text = movieData.name
                binding.genre.text = movieData.genre
                binding.synopsis.text = movieData.synopsis
                binding.rating.text = "${(movieData.rating)} из 5"
                binding.year.text = (movieData.year).toString()
                Snackbar.make(binding.descriptionMovieView, "Success", Snackbar.LENGTH_LONG).show()
            }
            is AppAction.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppAction.Error -> {
                binding.loadingLayout.visibility = View.GONE
                Snackbar
                    .make(binding.descriptionMovieView, "Error", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Reload") { viewModel.getMovieFromLocalSource() }
                    .show()
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}

