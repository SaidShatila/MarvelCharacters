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
import said.shatila.marvelcharacters.data.models.remote.response.*
import said.shatila.marvelcharacters.databinding.FragmentCharacterDetailBinding
import said.shatila.marvelcharacters.presentation.characterdetails.adapters.ComicDetailAdapter
import said.shatila.marvelcharacters.presentation.characterdetails.adapters.EventDetailAdapter
import said.shatila.marvelcharacters.presentation.characterdetails.adapters.SeriesDetailAdapter
import said.shatila.marvelcharacters.presentation.characterdetails.adapters.StoriesDetailAdapter
import kotlin.properties.Delegates

@AndroidEntryPoint
class CharacterDetailFragment : Fragment() {

    private val viewBinding: FragmentCharacterDetailBinding by lazy {
        FragmentCharacterDetailBinding.inflate(
            layoutInflater
        )
    }

    private val navArgs: CharacterDetailFragmentArgs by navArgs()
    private val characterDetailViewModel: CharacterDetailViewModel by viewModels()

    private var characterId by Delegates.notNull<Int>()

    private lateinit var comicAdapter: ComicDetailAdapter
    private lateinit var eventsAdapter: EventDetailAdapter
    private lateinit var seriesAdapter: SeriesDetailAdapter
    private lateinit var storiesAdapter: StoriesDetailAdapter

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
            getCharacterEvents()
            getCharacterSeries()
            getCharacterStories()
        }
        handleUiState()
    }

    private suspend fun getCharacterComics() =characterDetailViewModel.getComicDetails(characterId)
    private suspend fun getCharacterEvents() = characterDetailViewModel.getEventsDetails(characterId)
    private suspend fun getCharacterSeries() = characterDetailViewModel.getSeriesDetails(characterId)
    private suspend fun getCharacterStories() = characterDetailViewModel.getStoriesDetails(characterId)

    private fun handleUiState() {
        characterDetailViewModel.viewModelScope.launch {
            characterDetailViewModel.uiState.collect { uiEvent ->
                when (uiEvent) {
                    is CharacterDetailViewModel.UIEventCharacterDetail.OnSuccessComicDetail -> {
                        uiEvent.listOfComic?.let { setupRecyclerViewComic(it) }
                    }
                    is CharacterDetailViewModel.UIEventCharacterDetail.OnSuccessEventsDetail -> {
                        uiEvent.listOfEvents?.let { setupRecyclerViewEvent(it) }
                    }
                    is CharacterDetailViewModel.UIEventCharacterDetail.OnSuccessSeriesDetail -> {
                        uiEvent.listOfSeries?.let { setupRecyclerViewSeries(it) }
                    }
                    is CharacterDetailViewModel.UIEventCharacterDetail.OnSuccessStoriesDetail -> {
                        uiEvent.listOfStories?.let { setupRecyclerViewStories(it) }
                    }
                    is CharacterDetailViewModel.UIEventCharacterDetail.OnError -> {
                        uiEvent.resourceFailure?.let { showError(it) }
                    }
                    is CharacterDetailViewModel.UIEventCharacterDetail.OnLoading -> {
                        showLoadingComic()
                        showLoadingEvents()
                        showLoadingSeries()
                        showLoadingStories()
                    }
                    else -> {

                    }
                }
            }
        }
    }

    private fun setTitle() {
        this.characterId = navArgs.characterId
        val characterId = String.format(getString(R.string.character_id), navArgs.characterId)
        val fullTitle = "${navArgs.characterName} / $characterId"
        viewBinding.tvCharacterId.text = fullTitle
    }

    private fun showError(error: String) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
    }

    private fun showLoadingComic() {
        with(viewBinding) {
            pbComics.isVisible = true
            rvCharacterComics.isVisible = false
        }
    }

    private fun showLoadingEvents() {
        with(viewBinding) {
            pbEvents.isVisible = true
            rvCharacterEvents.isVisible = false
        }
    }

    private fun showLoadingSeries() {
        with(viewBinding) {
            pbSeries.isVisible = true
            rvCharacterSeries.isVisible = false
        }
    }

    private fun showLoadingStories() {
        with(viewBinding) {
            pbStories.isVisible = true
            rvCharacterStories.isVisible = false
        }
    }

    private fun setupRecyclerViewComic(comicList: List<CommonCharacterDetailResponse>) {
        comicAdapter = ComicDetailAdapter()
        with(viewBinding) {
            rvCharacterComics.apply {
                adapter = this@CharacterDetailFragment.comicAdapter
            }
            if (comicList.isNotEmpty()) {
                comicAdapter.submitList(comicList)
                pbComics.isVisible = false
                rvCharacterComics.isVisible = true
                tvNoDataFoundComics.isVisible = false

            } else {
                tvNoDataFoundComics.isVisible = true
                pbComics.isVisible = false
                rvCharacterComics.isVisible = false
            }
        }
    }

    private fun setupRecyclerViewEvent(eventsList: List<CommonCharacterDetailResponse>) {
        eventsAdapter = EventDetailAdapter()
        with(viewBinding) {
            rvCharacterEvents.apply {
                adapter = this@CharacterDetailFragment.eventsAdapter
            }
            if (eventsList.isNotEmpty()) {
                eventsAdapter.submitList(eventsList)
                pbEvents.isVisible = false
                rvCharacterEvents.isVisible = true
                tvNoDataFoundEvents.isVisible = false

            } else {
                tvNoDataFoundEvents.isVisible = true
                pbEvents.isVisible = false
                rvCharacterEvents.isVisible = false
            }
        }
    }

    private fun setupRecyclerViewSeries(seriesList: List<CommonCharacterDetailResponse>) {
        seriesAdapter = SeriesDetailAdapter()
        with(viewBinding) {
            rvCharacterSeries.apply {
                adapter = this@CharacterDetailFragment.seriesAdapter
            }
            if (seriesList.isNotEmpty()) {
                seriesAdapter.submitList(seriesList)
                pbSeries.isVisible = false
                rvCharacterSeries.isVisible = true
                tvNoDataFoundSeries.isVisible = false

            } else {
                tvNoDataFoundSeries.isVisible = true
                pbSeries.isVisible = false
                rvCharacterSeries.isVisible = false
            }
        }
    }

    private fun setupRecyclerViewStories(storiesList: List<CommonCharacterDetailResponse>) {
        storiesAdapter = StoriesDetailAdapter()
        with(viewBinding) {
            rvCharacterStories.apply {
                adapter = this@CharacterDetailFragment.storiesAdapter
            }
            if (storiesList.isNotEmpty()) {
                storiesAdapter.submitList(storiesList)
                pbStories.isVisible = false
                rvCharacterStories.isVisible = true
                tvNoDataFoundStories.isVisible = false

            } else {
                tvNoDataFoundStories.isVisible = true
                pbStories.isVisible = false
                rvCharacterStories.isVisible = false
            }
        }
    }
}