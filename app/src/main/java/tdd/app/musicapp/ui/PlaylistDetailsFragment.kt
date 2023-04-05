package tdd.app.musicapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import tdd.app.musicapp.R
import tdd.app.musicapp.databinding.FragmentPlaylistDetailsBinding
import tdd.app.musicapp.viewmodelfactories.PlaylistDetailsViewModelFactory
import tdd.app.musicapp.viewmodels.PlaylistDetailsViewModel
import javax.inject.Inject

@AndroidEntryPoint
class PlaylistDetailsFragment : Fragment() {

    private lateinit var binding: FragmentPlaylistDetailsBinding
    val args: PlaylistDetailsFragmentArgs by navArgs()

    lateinit var viewModel: PlaylistDetailsViewModel

    @Inject
    lateinit var viewModelFactory: PlaylistDetailsViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_playlist_details, container, false
        )
        binding.setLoader(true)
        binding.playlistDetailData = null
        setupViewModel()
        return binding.root
    }

    private fun setupViewModel() {
        val id: String = args.playlistId
        viewModel = ViewModelProvider(this, viewModelFactory)[PlaylistDetailsViewModel::class.java]
        viewModel.playlistDetailDataLiveData.observe(viewLifecycleOwner) {
            binding.playlistDetailData = it.getOrNull()
        }
        viewModel.loader.observe(viewLifecycleOwner) {
            binding.setLoader(it)
        }

        viewModel.getPlaylistDetailById(id)
    }

    override fun onDestroyView() {
        binding.unbind()
        super.onDestroyView()
    }

    companion object {
        @JvmStatic
        fun newInstance() = PlaylistDetailsFragment()
    }
}