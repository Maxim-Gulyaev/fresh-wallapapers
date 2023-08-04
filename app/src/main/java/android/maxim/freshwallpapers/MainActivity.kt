package android.maxim.freshwallpapers

import android.content.SharedPreferences
import android.maxim.freshwallpapers.utils.DARK_MODE
import android.maxim.freshwallpapers.utils.LIGHT_MODE
import android.maxim.freshwallpapers.utils.MODE_KEY
import android.maxim.freshwallpapers.utils.SYSTEM_MODE
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate.*
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var analytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val darkMode = sharedPreferences.getInt(MODE_KEY, SYSTEM_MODE)
        when (darkMode) {
            LIGHT_MODE -> setDefaultNightMode(MODE_NIGHT_NO)
            DARK_MODE -> setDefaultNightMode(MODE_NIGHT_YES)
            SYSTEM_MODE ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM);
                } else {
                    setDefaultNightMode(MODE_NIGHT_AUTO_BATTERY);
                }
        }

        analytics = Firebase.analytics
    }
}