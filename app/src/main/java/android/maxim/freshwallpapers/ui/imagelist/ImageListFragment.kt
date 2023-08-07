package android.maxim.freshwallpapers.ui.imagelist

import android.content.res.Configuration
import android.maxim.freshwallpapers.R
import android.maxim.freshwallpapers.data.models.Image
import android.maxim.freshwallpapers.databinding.FragmentImageListBinding
import android.maxim.freshwallpapers.utils.COLLECTION_KEY
import android.maxim.freshwallpapers.utils.Constants.KEY_RECYCLER_STATE
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsController
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ImageListFragment: Fragment(R.layout.fragment_image_list) {

    private var _binding: FragmentImageListBinding? = null
    private val binding get() = _binding!!
    private val imageListViewModel: ImageListViewModel by viewModels()
    private var collection: String? = null
    private var recyclerStateBundle: Bundle? = null
    private var recyclerState: Parcelable? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageListBinding.inflate(layoutInflater, container, false)

        collection = arguments?.getString(COLLECTION_KEY)
        imageListViewModel.getImageList(collection!!)

        imageListViewModel.imageList.observe(viewLifecycleOwner, Observer { imageList ->
            if (recyclerStateBundle != null) restoreRecyclerState()
            initRecycler(imageList)
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbarTitle = collection?.replaceFirstChar(Char::titlecase)
        binding.imageListToolbar.apply {
            title = toolbarTitle
            setNavigationIcon(R.drawable.baseline_arrow_back_ios_black_24)
            setNavigationOnClickListener {
                findNavController().navigate(R.id.collectionsFragment)
            }
        }
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

    override fun onDestroyView() {
        super.onDestroyView()
        recyclerStateBundle = Bundle()
        val mListState = binding.recyclerImageList.layoutManager?.onSaveInstanceState()
        recyclerStateBundle!!.putParcelable(KEY_RECYCLER_STATE, mListState)
        _binding = null
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

    private fun initRecycler(imageList:  List<Image>) {
        binding.recyclerImageList.layoutManager = GridLayoutManager(
            activity,
            3,
            GridLayoutManager.VERTICAL,
            false)
        binding.recyclerImageList.adapter = ImageListAdapter(imageList)
    }

    private fun restoreRecyclerState() {
        lifecycleScope.launch(Dispatchers.Main) {
            //TODO Replace getParcelable method
            recyclerState = recyclerStateBundle!!.getParcelable(KEY_RECYCLER_STATE)
            binding.recyclerImageList.layoutManager?.onRestoreInstanceState(recyclerState)
        }
    }
}