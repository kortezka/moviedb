package view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moviedb.databinding.ErrorFragmentBinding
import java.net.URL
import android.content.Intent






// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [Error.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val ARG_ID = "id"

class Error : Fragment() {

    private var movieID: Int? = null


    private var _binding: ErrorFragmentBinding? = null
    private val binding get() = _binding!!




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieID = it.getInt(ARG_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = ErrorFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener{
            requireActivity().supportFragmentManager.popBackStack()
              }
        binding.button2.setOnClickListener{
            requireActivity().supportFragmentManager.popBackStack()
            requireActivity().supportFragmentManager.popBackStack()
        }

    }




    companion object {

        fun newInstance(movieID:Int) =
            Error().apply {
                arguments = Bundle().apply {
                  //  stringUrl=url.toString()
                    putInt(ARG_ID, movieID)
                }
            }
        fun newInstance() =
            Error().apply {
                arguments = Bundle().apply {
                }
            }
    }


}