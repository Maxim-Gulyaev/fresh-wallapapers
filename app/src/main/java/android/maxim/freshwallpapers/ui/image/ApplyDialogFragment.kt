package android.maxim.freshwallpapers.ui.image

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.maxim.freshwallpapers.R
import android.maxim.freshwallpapers.databinding.FragmentDialogApplyBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

class ApplyDialogFragment: DialogFragment(R.layout.fragment_dialog_apply), OnClickListener {

    private var _binding: FragmentDialogApplyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialogApplyBinding.inflate(layoutInflater, container, false)

        //transparent background to make visible rounded corners
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.btnApplyDialogCancel.setOnClickListener {
            this.dismiss()
        }
        binding.btnApplyDialogOk.setOnClickListener(this)

        return binding.root
    }

    override fun onClick(v: View?) {
        when {
            binding.rbHomeScreen.isChecked -> {}
            binding.rbHomeScreen.isChecked -> {}
            binding.rbBothScreens.isChecked -> {}
        }
    }
}