package said.shatila.marvelcharacters.presentation.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import said.shatila.marvelcharacters.data.models.remote.response.CharacterResponse
import said.shatila.marvelcharacters.domain.repository.MainRepository
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(private val mainRepository: MainRepository) :
    ViewModel() {

    private val _uiState =
        MutableStateFlow<UIEventCharacters>(UIEventCharacters.Nothing)
    val uiState = _uiState.asStateFlow()


    suspend fun getCharacters() {
        viewModelScope.launch {
            mainRepository.characters().collect {
                _uiState.value = UIEventCharacters.OnSuccess(it)
            }
        }
    }

    sealed interface UIEventCharacters {
        object Nothing : UIEventCharacters
        data class OnSuccess(val transactionsResponse: PagingData<CharacterResponse>) :
            UIEventCharacters

        data class OnLoading(val isLoading: Boolean) : UIEventCharacters
        data class ShowError(val error: Throwable) : UIEventCharacters
    }
}