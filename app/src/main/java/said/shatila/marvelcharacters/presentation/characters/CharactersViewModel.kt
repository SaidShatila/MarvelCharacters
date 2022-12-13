package said.shatila.marvelcharacters.presentation.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import said.shatila.marvelcharacters.data.models.remote.response.CharacterResponse
import said.shatila.marvelcharacters.data.remote.model.Resource
import said.shatila.marvelcharacters.domain.repository.MainRepository
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(private val mainRepository: MainRepository) :
    ViewModel() {

    private val _uiState =
        MutableStateFlow<UIEventCharacters>(UIEventCharacters.Nothing)
    val uiState = _uiState.asStateFlow()


  /*  suspend fun getCharacters() {
        viewModelScope.launch {
            mainRepository.getCharacters().collectLatest { uiState ->
                when (uiState) {
                    is Resource.Failure -> {
                        _uiState.value = UIEventCharacters.OnLoading(false)
                        _uiState.value = UIEventCharacters.ShowErrorDialog(uiState)
                    }
                    Resource.Loading -> {
                        _uiState.value = UIEventCharacters.OnLoading(true)
                    }
                    is Resource.Success -> {
                        _uiState.value = UIEventCharacters.OnLoading(false)
                        _uiState.value = UIEventCharacters.OnSuccess(uiState.value)
                    }
                }
            }
        }
    }*/


    sealed interface UIEventCharacters {
        object Nothing : UIEventCharacters
        data class OnSuccess(val transactionsResponse: PagingData<CharacterResponse>) :
            UIEventCharacters

        data class OnLoading(val isLoading: Boolean) : UIEventCharacters
        data class ShowErrorDialog(val error: Throwable) : UIEventCharacters
    }
}