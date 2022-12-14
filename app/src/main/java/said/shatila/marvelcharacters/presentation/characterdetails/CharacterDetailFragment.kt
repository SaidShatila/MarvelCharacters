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
import said.shatila.marvelcharacters.data.models.remote.response.EventsDetailResponse
import said.shatila.marvelcharacters.data.models.remote.response.SeriesDetailResponse
import said.shatila.marvelcharacters.data.models.remote.response.StoriesDetailResponse
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

    private suspend fun getCharacterComics() {
        characterDetailViewModel.getComicDetails(characterId)
    }

    private suspend fun getCharacterEvents() {
        characterDetailViewModel.getEventsDetails(characterId)
    }

    private suspend fun getCharacterSeries() {
        characterDetailViewModel.getSeriesDetails(characterId)
    }

    private suspend fun getCharacterStories() {
        characterDetailViewModel.getStoriesDetails(characterId)
    }


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

    private fun setupRecyclerViewComic(comicList: List<ComicDetailsResponse>) {
        comicAdapter = ComicDetailAdapter()
        viewBinding.rvCharacterComics.apply {
            adapter = this@CharacterDetailFragment.comicAdapter
        }
        comicAdapter.submitList(comicList)
        viewBinding.pbComics.isVisible = false
    }

    private fun setupRecyclerViewEvent(eventsList: List<EventsDetailResponse>) {
        eventsAdapter = EventDetailAdapter()
        viewBinding.rvCharacterEvents.apply {
            adapter = this@CharacterDetailFragment.eventsAdapter
        }
        eventsAdapter.submitList(eventsList)
        viewBinding.pbEvents.isVisible = false
    }

    private fun setupRecyclerViewSeries(seriesList: List<SeriesDetailResponse>) {
        seriesAdapter = SeriesDetailAdapter()
        viewBinding.rvCharacterSeries.apply {
            adapter = this@CharacterDetailFragment.seriesAdapter
        }
        seriesAdapter.submitList(seriesList)
        viewBinding.pbSeries.isVisible = false
    }

    private fun setupRecyclerViewStories(storiesList: List<StoriesDetailResponse>) {
        storiesAdapter = StoriesDetailAdapter()
        viewBinding.rvCharacterStories.apply {
            adapter = this@CharacterDetailFragment.storiesAdapter
        }
        storiesAdapter.submitList(storiesList)
        viewBinding.pbStories.isVisible = false
    }

}