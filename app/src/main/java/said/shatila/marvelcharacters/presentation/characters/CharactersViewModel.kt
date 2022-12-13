package said.shatila.marvelcharacters.presentation.characters

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import said.shatila.marvelcharacters.domain.repository.MainRepository
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor( private val mainRepository: MainRepository) :
    ViewModel() {

    suspend fun getCharacters() = mainRepository.getCharacters()
}