package said.shatila.marvelcharacters

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import said.shatila.marvelcharacters.databinding.ActivityFullscreenBinding


class FullscreenActivity : AppCompatActivity() {

    private val binding: ActivityFullscreenBinding by lazy {
        ActivityFullscreenBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }
}