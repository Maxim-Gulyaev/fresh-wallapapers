package android.maxim.freshwallpapers.ui.settings

import android.content.res.Configuration
import android.maxim.freshwallpapers.R
import android.maxim.freshwallpapers.databinding.FragmentSettingsBinding
import android.maxim.freshwallpapers.utils.DARK_MODE
import android.maxim.freshwallpapers.utils.LIGHT_MODE
import android.maxim.freshwallpapers.utils.SYSTEM_MODE
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsController
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment: Fragment(R.layout.fragment_settings) {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)

        settingsViewModel.getCurrentMode()

        binding.settingsToolbar.apply {
            setNavigationIcon(R.drawable.baseline_arrow_back_ios_24)
            setNavigationOnClickListener {
                findNavController().navigate(R.id.collectionsFragment)
            }
        }

        binding.llAppearance.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_appearanceDialogFragment)
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        if (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            == Configuration.UI_MODE_NIGHT_YES) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                requireActivity().window.insetsController?.setSystemBarsAppearance(
                    0,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
            }
        }
    }

    override fun onResume() {
        settingsViewModel.currentMode.observe(viewLifecycleOwner, Observer { currentMode ->
            when (currentMode) {
                LIGHT_MODE -> binding.tvCurrentMode.text = resources.getString(R.string.light)
                DARK_MODE -> binding.tvCurrentMode.text = resources.getString(R.string.dark)
                SYSTEM_MODE -> binding.tvCurrentMode.text = resources.getString(R.string.system)
            }
        })
        super.onResume()
    }

    override fun onStop() {
        if (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            == Configuration.UI_MODE_NIGHT_YES) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                requireActivity().window.insetsController?.setSystemBarsAppearance(
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
            }
        }
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}