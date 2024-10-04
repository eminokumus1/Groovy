package petros.efthymiou.groovy.playlistdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_playlist_details.details_loader
import petros.efthymiou.groovy.databinding.FragmentPlaylistDetailsBinding
import javax.inject.Inject

@AndroidEntryPoint
class PlaylistDetailsFragment : Fragment() {
    private lateinit var binding: FragmentPlaylistDetailsBinding

    lateinit var viewModel: PlaylistDetailsViewModel

    @Inject
    lateinit var viewModelFactory: PlaylistDetailsViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlaylistDetailsBinding.inflate(layoutInflater, container, false)

        val id = arguments?.getString("id")

        setupViewModel()

        if (id != null) {
            viewModel.getPlaylistDetails(id)
        }

        observePlaylistDetails()
        observeLoader()


        return binding.root
    }

    private fun observeLoader(){
        viewModel.loader.observe(viewLifecycleOwner){loading->
            when(loading){
                true -> details_loader.visibility = View.VISIBLE
                false -> details_loader.visibility = View.GONE
            }

        }
    }

    private fun observePlaylistDetails() {
        viewModel.playlistDetails.observe(viewLifecycleOwner) { newPlaylistDetails ->
            if (newPlaylistDetails.getOrNull() != null) {
                binding.playlistName.text = newPlaylistDetails.getOrNull()!!.name
                binding.playlistDetails.text = newPlaylistDetails.getOrNull()!!.details
            }
        }
    }

    private fun setupViewModel() {
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(PlaylistDetailsViewModel::class.java)
    }

}