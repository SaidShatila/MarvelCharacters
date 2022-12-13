package said.shatila.marvelcharacters.presentation.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import said.shatila.marvelcharacters.data.models.remote.response.CharacterResponse
import said.shatila.marvelcharacters.databinding.FragmentCharactersBinding

@AndroidEntryPoint
class CharactersFragment : Fragment() {
    private val binding: FragmentCharactersBinding by lazy {
        FragmentCharactersBinding.inflate(
            layoutInflater
        )
    }

    private lateinit var adapter: CharactersAdapter
    private lateinit var loadStateAdapter: LoadStateCharacterAdapter

    private val charactersViewModel by viewModels<CharactersViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setupRecyclerView()
        charactersViewModel.viewModelScope.launch {
            getCharactersData()
        }
        handleCharactersData()
    }

    private suspend fun getCharactersData() {
        charactersViewModel.getCharacters()
    }

    private fun handleCharactersData() {
        charactersViewModel.viewModelScope.launch {
            charactersViewModel.uiState.collect { uiEvent ->
                when (uiEvent) {
                    is CharactersViewModel.UIEventCharacters.OnSuccess -> {
                        setupData(uiEvent.transactionsResponse)
                    }
                    is CharactersViewModel.UIEventCharacters.OnLoading -> {
                        binding.animationView.isVisible = uiEvent.isLoading
                    }
                    is CharactersViewModel.UIEventCharacters.ShowErrorDialog -> {
                        binding.animationView.isVisible = false
                    }
                    is CharactersViewModel.UIEventCharacters.Nothing -> {
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = CharactersAdapter() {

        }
        loadStateAdapter = LoadStateCharacterAdapter {
            adapter.retry()
        }
        binding.rvCharacters.adapter = adapter
        binding.rvCharacters.layoutManager =
            GridLayoutManager(requireContext(), 2)
        binding.rvCharacters.adapter = adapter.withLoadStateFooter(loadStateAdapter)
        adapter.addLoadStateListener {
            if (it.refresh is LoadState.Loading) showLoading()
            else {
                hideLoading()
            }
        }
    }

    private fun setupData(characters: PagingData<CharacterResponse>) {

        adapter.submitData(lifecycle, characters)
        adapter.addLoadStateListener {
            binding.rvCharacters.isVisible =
                !(adapter.itemCount == 0 && it.refresh != LoadState.Loading)
        }
    }

    private fun showLoading() {
        binding.animationView.isVisible = true
    }

    private fun hideLoading() {
        binding.animationView.isVisible = false
    }
}