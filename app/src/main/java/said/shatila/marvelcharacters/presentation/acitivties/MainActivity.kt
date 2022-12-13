package said.shatila.marvelcharacters.presentation.acitivties

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.coroutineScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import said.shatila.marvelcharacters.databinding.ActivityMainBinding
import said.shatila.marvelcharacters.presentation.characters.CharactersViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(
            layoutInflater
        )
    }

    private val charactersViewModel by viewModels<CharactersViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(binding.root)

        lifecycle.coroutineScope.launch {
            charactersViewModel.getCharacters()
        }
    }
}