package said.shatila.marvelcharacters

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.facebook.drawee.backends.pipeline.Fresco
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        forceWhiteTheme()
        fresco()
    }

    private fun forceWhiteTheme() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    private fun fresco() {
         Fresco.initialize(this)
    }

}