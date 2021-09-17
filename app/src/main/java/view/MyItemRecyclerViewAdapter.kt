package view

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.moviedb.databinding.MovieItemBinding
import model.MovieData


class MyItemRecyclerViewAdapter(
    private val movieAdapterValue: List<MovieData>,
    private  val cellClickListener: CellClickListener) :
    RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MovieItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.setMovieToViewHolder(movieAdapterValue[position])
        holder.itemView.setOnClickListener {
            cellClickListener.onCellClick(movieAdapterValue[position])
        }
    }

    override fun getItemCount() = movieAdapterValue.size

    interface CellClickListener {
        fun onCellClick(movie: MovieData)
    }

    inner class ViewHolder(private val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root) {


        fun setMovieToViewHolder(movie: MovieData) {

            binding.Name.text = movie.title
            binding.genre.text = movie.genre
//      }
        }
    }
}



