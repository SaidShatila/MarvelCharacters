package said.shatila.marvelcharacters.presentation.acitivties.characters

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import said.shatila.marvelcharacters.data.remote.repository.MainRepository
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor( private val mainRepository: MainRepository) :
    ViewModel() {

    fun getCharacters() = mainRepository.getCharacters()
}