package said.shatila.marvelcharacters.presentation.acitivties

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import said.shatila.marvelcharacters.databinding.ActivityFullscreenBinding
import said.shatila.marvelcharacters.presentation.acitivties.characters.CharactersViewModel

@AndroidEntryPoint
class FullscreenActivity : AppCompatActivity() {

    private val binding: ActivityFullscreenBinding by lazy {
        ActivityFullscreenBinding.inflate(
            layoutInflater
        )
    }

    private val charactersViewModel by viewModels<CharactersViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        lifecycle.coroutineScope.launch{
            charactersViewModel.getCharacters()
        }
    }
}