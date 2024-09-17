package petros.efthymiou.groovy.playlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import petros.efthymiou.groovy.R
import petros.efthymiou.groovy.databinding.FragmentPlaylistBinding
import javax.inject.Inject

const val BASE_URL = "http://192.168.0.23:3000/"

@AndroidEntryPoint
class PlaylistFragment : Fragment() {

    private lateinit var binding: FragmentPlaylistBinding

    private lateinit var viewModel: PlaylistViewModel

    @Inject
    lateinit var viewModelFactory: PlaylistViewModelFactory


    private val playlistAdapter = MyPlaylistRecyclerViewAdapter { id ->
        val bundle = Bundle()
        bundle.putString("id", id)

        findNavController(this).navigate(R.id.action_playlistFragment_to_playlistDetailsFragment,bundle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlaylistBinding.inflate(layoutInflater, container, false)

        setupViewModel()


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.playlists.observe(viewLifecycleOwner) {
            if (it.getOrNull() != null) {
                playlistAdapter.values = it.getOrNull()!!
                playlistAdapter.notifyDataSetChanged()
                println(playlistAdapter.values)
            } else {
                TODO()
            }
        }
        viewModel.loader.observe(viewLifecycleOwner) { loading ->
            when (loading) {
                true -> binding.loader.visibility = View.VISIBLE
                else -> binding.loader.visibility = View.GONE
            }
        }

        binding.playlistsList.adapter = playlistAdapter

    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)[PlaylistViewModel::class.java]
    }


}