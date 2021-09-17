package view

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedb.R
import model.MovieData
import model.getSomeMovies
import model.getTopThree
import viewModel.MainViewModel
import androidx.lifecycle.Observer
import com.example.moviedb.databinding.DescriptionFragmentBinding
import com.example.moviedb.databinding.MovieItemListBinding
import com.google.android.material.snackbar.Snackbar
import viewModel.AppAction


class ListFragment : Fragment(),MyItemRecyclerViewAdapter.CellClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    private lateinit var viewModel: MainViewModel
    private var _binding: MovieItemListBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = MovieItemListBinding.inflate(inflater, container, false)
        val view = binding.root
        val layoutManager1 =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        val layoutManager2 =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

        val movie: List<MovieData> = getSomeMovies()
        val movie2: List<MovieData> = getTopThree()

        val someMovie: RecyclerView = view.findViewById(R.id.list);
        val bestMovie: RecyclerView = view.findViewById(R.id.list2);

        someMovie.layoutManager = layoutManager1
        bestMovie.layoutManager = layoutManager2


        someMovie.adapter = MyItemRecyclerViewAdapter(movie,this)
        bestMovie.adapter = MyItemRecyclerViewAdapter(movie2,this)

        someMovie.isNestedScrollingEnabled = false
        bestMovie.isNestedScrollingEnabled = false

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

                binding.loadingLayout.visibility = View.GONE

                Snackbar.make(binding.itemContainer, "Success", Snackbar.LENGTH_LONG).show()
            }
            is AppAction.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppAction.Error -> {
                binding.loadingLayout.visibility = View.GONE
                Snackbar
                    .make(binding.itemContainer, "Error", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Reload") { viewModel.getMovieFromLocalSource() }
                    .show()
            }
        }
    }

    companion object {


        fun newInstance() =
            ListFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onCellClick(movieData: MovieData) {

        val fragment = DescriptionFragment.newInstance(movieData)
        requireActivity().supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.container, fragment)
            .commit()

    }
}