package said.shatila.marvelcharacters.presentation.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import said.shatila.marvelcharacters.data.models.remote.response.CharacterResponse
import said.shatila.marvelcharacters.databinding.FragmentCharactersBinding

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
    }

    private fun setupRecyclerView() {
        adapter = CharactersAdapter() {

        }
        loadStateAdapter = LoadStateCharacterAdapter {
            adapter.retry()
        }
        binding.rvCharacters.adapter = adapter
        binding.rvCharacters.layoutManager =
            LinearLayoutManager(requireContext(), GridLayoutManager.VERTICAL, false)
        binding.rvCharacters.adapter = adapter.withLoadStateFooter(loadStateAdapter)
        adapter.addLoadStateListener {
            if (it.refresh is LoadState.Loading)
                showLoading()
            else {
                hideLoading()
            }
        }
    }

    private fun setupData(transaction: PagingData<CharacterResponse>) {

        adapter.submitData(lifecycle, transaction)
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