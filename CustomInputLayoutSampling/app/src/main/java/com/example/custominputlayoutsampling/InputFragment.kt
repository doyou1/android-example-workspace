package com.example.custominputlayoutsampling

import android.app.Dialog
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.custominputlayoutsampling.databinding.ActivityMainBinding
import com.example.custominputlayoutsampling.databinding.FragmentInputBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class InputFragment(private val activityBinding: ActivityMainBinding, private val flag: Int): BottomSheetDialogFragment() {

    private lateinit var binding: FragmentInputBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInputBinding.inflate(inflater, container, false)
        return binding.root
    }

    // config layout full size
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        return dialog
    }

    override fun onResume() {
        super.onResume()
        setRecyclerView()
    }

    private fun setRecyclerView() {
        when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {
                val layoutManager = GridLayoutManager(requireContext(), 4)
                layoutManager.orientation =
                    LinearLayoutManager.HORIZONTAL
                binding.rvContent.layoutManager = layoutManager
            }
            Configuration.ORIENTATION_LANDSCAPE -> {
                val layoutManager = GridLayoutManager(requireContext(), 4)
                layoutManager.orientation =
                    LinearLayoutManager.VERTICAL
                binding.rvContent.layoutManager = layoutManager
            }
            else -> throw NotImplementedError()
        }

        val list = arrayListOf(
            "현금",
            "은행",
            "카드"
        )
        list.add("추가")
        binding.rvContent.adapter = InputContentRVAdapter(list, activityBinding, this, flag)
    }

}