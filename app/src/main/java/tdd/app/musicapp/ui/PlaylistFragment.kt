package tdd.app.musicapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import tdd.app.musicapp.adapters.MyPlaylistRecyclerViewAdapter
import tdd.app.musicapp.databinding.FragmentPlaylistBinding
import tdd.app.musicapp.models.Playlist
import tdd.app.musicapp.ui.PlaylistFragmentDirections.Companion.actionPlaylistFragmentToPlaylistDetailsFragment
import tdd.app.musicapp.utils.PlaylistViewModelFactory
import tdd.app.musicapp.viewmodels.PlaylistViewModel
import javax.inject.Inject

/**
 * A fragment representing a list of Items.
 */
@AndroidEntryPoint
class PlaylistFragment : Fragment() {

    @Inject
    lateinit var playlistViewModelFactory: PlaylistViewModelFactory

    lateinit var playlistViewModel: PlaylistViewModel
    private lateinit var fragmentPlaylistBinding: FragmentPlaylistBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        fragmentPlaylistBinding = FragmentPlaylistBinding.inflate(inflater, container, false)

        setupViewModel()

        return fragmentPlaylistBinding.root
    }

    private fun setupViewModel() {
        //instantiate view model
        playlistViewModel =
            ViewModelProvider(this, playlistViewModelFactory)[PlaylistViewModel::class.java]

        //observe playlists
        playlistViewModel.playlists.observe(this as LifecycleOwner) { playlists ->
            if (playlists.getOrNull() != null) {
                // Set the adapter
                with(fragmentPlaylistBinding.rvPlaylist) {
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    adapter =
                        MyPlaylistRecyclerViewAdapter(playlists.getOrNull() as List<Playlist>) { id ->
                            val action: NavDirections =
                                PlaylistFragmentDirections.actionPlaylistFragmentToPlaylistDetailsFragment(
                                    id
                                )
                            findNavController().navigate(action)
                        }
                }
            } else {
                //todo
            }
        }

        //observe loader state
        playlistViewModel.loader.observe(this as LifecycleOwner) { loading ->
            if (loading) {
                fragmentPlaylistBinding.loader.visibility = View.VISIBLE
            } else {
                fragmentPlaylistBinding.loader.visibility = View.GONE
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            PlaylistFragment().apply {
            }
    }
}