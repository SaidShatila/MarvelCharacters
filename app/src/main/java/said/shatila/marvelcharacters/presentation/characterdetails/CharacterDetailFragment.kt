package said.shatila.marvelcharacters.presentation.characterdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import said.shatila.marvelcharacters.R
import said.shatila.marvelcharacters.data.models.remote.response.ComicDetailsResponse
import said.shatila.marvelcharacters.databinding.FragmentCharacterDetailBinding

@AndroidEntryPoint
class CharacterDetailFragment : Fragment() {

    private val viewBinding: FragmentCharacterDetailBinding by lazy {
        FragmentCharacterDetailBinding.inflate(
            layoutInflater
        )
    }

    private val navArgs: CharacterDetailFragmentArgs by navArgs()
    private val characterDetailViewModel: CharacterDetailViewModel by viewModels()

    private lateinit var adapter: CharacterDetailAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle()

        characterDetailViewModel.viewModelScope.launch {
            getCharacterComics()
        }
        handleUiState()
    }

    private suspend fun getCharacterComics() {
        characterDetailViewModel.getComicDetails(navArgs.characterId)
    }

    private fun handleUiState() {
        characterDetailViewModel.viewModelScope.launch {
            characterDetailViewModel.uiState.collect { uiEvent ->
                when (uiEvent) {
                    is CharacterDetailViewModel.UIEventCharacterDetail.OnSuccessComicDetail -> {
                        uiEvent.listOfComic?.let { setupRecyclerView(it) }
                    }
                    is CharacterDetailViewModel.UIEventCharacterDetail.OnError -> {
                        uiEvent.resourceFailure?.let { showError(it) }
                    }
                    is CharacterDetailViewModel.UIEventCharacterDetail.OnLoading -> {
                        showLoading()
                    }
                    else -> {

                    }
                }
            }
        }
    }

    private fun setTitle() {
        val characterId = String.format(getString(R.string.character_id), navArgs.characterId)
        val fullTitle = "${navArgs.characterName} / $characterId"
        viewBinding.tvCharacterId.text = fullTitle
    }

    private fun showError(error: String) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading() {
        with(viewBinding) {
            pbComics.isVisible = true
            rvCharacterComics.isVisible = false
        }
    }

    private fun setupRecyclerView(list: List<ComicDetailsResponse>) {
        adapter = CharacterDetailAdapter()
        viewBinding.rvCharacterComics.apply {
            adapter = this@CharacterDetailFragment.adapter
        }
        adapter.submitList(list)
        viewBinding.pbComics.isVisible = false
    }
}