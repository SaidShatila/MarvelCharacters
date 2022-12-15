package said.shatila.marvelcharacters.presentation.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
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
        btnRetry()
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
                    is CharactersViewModel.UIEventCharacters.ShowError -> {
                        showEmptyView()
                    }
                    is CharactersViewModel.UIEventCharacters.Nothing -> {
                        showEmptyView()
                    }
                }
            }
        }
    }

    private fun btnRetry() {
        binding.btnRetry.setOnClickListener {
            charactersViewModel.viewModelScope.launch {
                getCharactersData()
            }
            showLoading()
        }
    }

    private fun setupRecyclerView() {
        adapter = CharactersAdapter() { characterResponse ->
            findNavController().navigate(
                CharactersFragmentDirections.actionCharactersFragmentToCharacterDetailFragment(
                    characterResponse.id, characterResponse.name
                )
            )
        }
        loadStateAdapter = LoadStateCharacterAdapter {
            adapter.retry()
        }
        binding.rvCharacters.adapter = adapter
        binding.rvCharacters.layoutManager = GridLayoutManager(requireContext(), 2)
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
            val isListEmpty = it.refresh is LoadState.NotLoading && adapter.itemCount == 0
            binding.rvCharacters.isVisible = !isListEmpty
            showEmptyView(!isListEmpty)
        }
    }

    private fun showLoading() {
        binding.animationView.isVisible = true
    }

    private fun hideLoading() {
        binding.animationView.isVisible = false
    }

    private fun showEmptyView(isEmpty: Boolean = true) {
        with(binding) {
            animationView.isVisible = !isEmpty
            rvCharacters.isVisible = !isEmpty
            tvNoData.isVisible = isEmpty
            btnRetry.isVisible = isEmpty
        }
    }
}