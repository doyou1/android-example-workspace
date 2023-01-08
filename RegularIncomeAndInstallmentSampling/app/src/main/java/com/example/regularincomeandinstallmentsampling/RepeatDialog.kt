package com.example.regularincomeandinstallmentsampling


import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.regularincomeandinstallmentsampling.databinding.DialogRepeatBinding
import com.example.regularincomeandinstallmentsampling.regularincome.RegularIncomeActivity
import com.example.regularincomeandinstallmentsampling.util.REPEAT_ITEM_LIST

class RepeatDialog : DialogFragment() {

    private lateinit var binding: DialogRepeatBinding
    private val TAG = this::class.java.simpleName
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogRepeatBinding.inflate(requireActivity().layoutInflater)
        val builder = AlertDialog.Builder(activity)
        builder.setView(binding.root)

        return builder.create()
    }

    override fun onResume() {
        super.onResume()
        setRecyclerView()
    }

    private fun setRecyclerView() {
        setLayoutManager()
        binding.recyclerView.adapter = RepeatRVAdapter(REPEAT_ITEM_LIST, this)
    }

    private fun setLayoutManager() {
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerView.layoutManager = layoutManager
    }

    fun itemClick(item: String) {

        (requireActivity() as RegularIncomeActivity).periodItemClick(item)
        dismiss()
    }
}