package petros.efthymiou.groovy.playlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import okhttp3.OkHttpClient
import petros.efthymiou.groovy.databinding.FragmentPlaylistBinding
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "http://192.168.0.23:3000/"

class PlaylistFragment : Fragment() {

    private lateinit var binding: FragmentPlaylistBinding
    private lateinit var viewModel: PlaylistViewModel
    private lateinit var viewModelFactory: PlaylistViewModelFactory
    private lateinit var repository: PlaylistRepository
    private lateinit var service: PlaylistService

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(PlaylistAPI::class.java)

    private val playlistAdapter = MyPlaylistRecyclerViewAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlaylistBinding.inflate(layoutInflater, container, false)

        service = PlaylistService(api)
        repository = PlaylistRepository(service)
        setupViewModel(repository)

        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.playlists.observe(viewLifecycleOwner){
            if (it.getOrNull() != null){
                playlistAdapter.values = it.getOrNull()!!
                playlistAdapter.notifyDataSetChanged()
            }else{
                TODO()
            }
        }

        binding.playlistsList.adapter = playlistAdapter

    }

    private fun setupViewModel(repository: PlaylistRepository) {
        viewModelFactory = PlaylistViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[PlaylistViewModel::class.java]
    }


}