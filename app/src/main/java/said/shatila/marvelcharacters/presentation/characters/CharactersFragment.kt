package said.shatila.marvelcharacters.presentation.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import said.shatila.marvelcharacters.databinding.FragmentCharactersBinding

class CharactersFragment : Fragment() {
    private val binding: FragmentCharactersBinding by lazy {
        FragmentCharactersBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }
}