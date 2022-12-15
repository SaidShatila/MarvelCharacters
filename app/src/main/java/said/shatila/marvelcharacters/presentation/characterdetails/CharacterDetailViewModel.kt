package said.shatila.marvelcharacters.presentation.characterdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import said.shatila.marvelcharacters.data.models.remote.response.*
import said.shatila.marvelcharacters.domain.repository.MainRepository
import said.shatila.marvelcharacters.util.NetworkResult
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(private val mainRepository: MainRepository) :
    ViewModel() {
    sealed class UIEventCharacterDetail {
        data class OnSuccessComicDetail(val listOfComic: List<CommonCharacterDetailResponse>?) :
            UIEventCharacterDetail()
        data class OnSuccessEventsDetail(val listOfEvents: List<CommonCharacterDetailResponse>?) :
            UIEventCharacterDetail()
        data class OnSuccessSeriesDetail(val listOfSeries: List<CommonCharacterDetailResponse>?) :
            UIEventCharacterDetail()
        data class OnSuccessStoriesDetail(val listOfStories: List<CommonCharacterDetailResponse>?) :
            UIEventCharacterDetail()

        data class OnError(val resourceFailure: String?) : UIEventCharacterDetail()
        data class OnLoading(val onLoading: Boolean) : UIEventCharacterDetail()
        object Nothing : UIEventCharacterDetail()
    }

    private val _uiState =
        MutableStateFlow<UIEventCharacterDetail>(UIEventCharacterDetail.Nothing)
    val uiState = _uiState.asStateFlow()

    suspend fun getComicDetails(characterId: Int) {
        viewModelScope.launch {
            mainRepository.getCharacterComics(characterId).collectLatest {
                when (it) {
                    is NetworkResult.Success -> {
                        _uiState.value = UIEventCharacterDetail.OnSuccessComicDetail(it.data?.results)
                    }
                    is NetworkResult.Error -> {
                        _uiState.value = UIEventCharacterDetail.OnError(it.message)
                    }
                    is NetworkResult.Loading -> {
                        _uiState.value = UIEventCharacterDetail.OnLoading(true)
                    }
                }
            }
        }
    }
    suspend fun getEventsDetails(characterId: Int) {
        viewModelScope.launch {
            mainRepository.getCharacterEvents(characterId).collectLatest {
                when (it) {
                    is NetworkResult.Success -> {
                        _uiState.value = UIEventCharacterDetail.OnSuccessEventsDetail(it.data?.results)
                    }
                    is NetworkResult.Error -> {
                        _uiState.value = UIEventCharacterDetail.OnError(it.message)
                    }
                    is NetworkResult.Loading -> {
                        _uiState.value = UIEventCharacterDetail.OnLoading(true)
                    }
                }
            }
        }
    }
    suspend fun getSeriesDetails(characterId: Int) {
        viewModelScope.launch {
            mainRepository.getCharacterSeries(characterId).collectLatest {
                when (it) {
                    is NetworkResult.Success -> {
                        _uiState.value = UIEventCharacterDetail.OnSuccessSeriesDetail(it.data?.results)
                    }
                    is NetworkResult.Error -> {
                        _uiState.value = UIEventCharacterDetail.OnError(it.message)
                    }
                    is NetworkResult.Loading -> {
                        _uiState.value = UIEventCharacterDetail.OnLoading(true)
                    }
                }
            }
        }
    }
    suspend fun getStoriesDetails(characterId: Int) {
        viewModelScope.launch {
            mainRepository.getCharacterStories(characterId).collectLatest {
                when (it) {
                    is NetworkResult.Success -> {
                        _uiState.value = UIEventCharacterDetail.OnSuccessStoriesDetail(it.data?.results)
                    }
                    is NetworkResult.Error -> {
                        _uiState.value = UIEventCharacterDetail.OnError(it.message)
                    }
                    is NetworkResult.Loading -> {
                        _uiState.value = UIEventCharacterDetail.OnLoading(true)
                    }
                }
            }
        }
    }
}