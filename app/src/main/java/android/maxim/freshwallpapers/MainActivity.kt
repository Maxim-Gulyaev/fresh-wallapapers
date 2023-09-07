package android.maxim.freshwallpapers

import android.content.SharedPreferences
import android.maxim.freshwallpapers.data.repository.WallpapersRepository
import android.maxim.freshwallpapers.di.DarkModePrefs
import android.maxim.freshwallpapers.utils.*
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate.*
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), WallpapersRepository.ErrorCallback {

    @Inject
    @DarkModePrefs
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var analytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        when (sharedPreferences.getInt(MODE_KEY, SYSTEM_MODE)) {
            LIGHT_MODE -> setDefaultNightMode(MODE_NIGHT_NO)
            DARK_MODE -> setDefaultNightMode(MODE_NIGHT_YES)
            SYSTEM_MODE ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM)
                } else {
                    setDefaultNightMode(MODE_NIGHT_AUTO_BATTERY)
                }
        }

        analytics = Firebase.analytics
    }

    override fun onError(errorMessage: String) {
        runOnUiThread {
            SnackbarUtils().showSnackbar(
                findViewById(R.id.fragment_container),
                errorMessage,
                resources.getString(R.string.got_it)
            )
        }
    }
}