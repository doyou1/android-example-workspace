package com.example.multiplelocalesampling

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.multiplelocalesampling.databinding.SpinnerItemSelectLanguageBinding

class SelectLanguageAdapter (private val _list: List<SelectLanguageItem>): BaseAdapter() {
    private val list = _list

    override fun getCount(): Int = list.size

    override fun getItem(position: Int): Any = list[position]

    override fun getItemId(position: Int): Long = list[position].id

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding =
            if (convertView == null) {
                val binding: SpinnerItemSelectLanguageBinding =
                    SpinnerItemSelectLanguageBinding.inflate(
                        LayoutInflater.from(parent?.context),
                        parent,
                        false
                    )
                // tagにインスタンスをセット(convertViewが存在する場合に使い回すため)
                binding.root.tag = binding
                binding
            } else {
                convertView.tag as SpinnerItemSelectLanguageBinding
            }


        val item = list[position]
        binding.ivFlag.setImageResource(item.flag)
        binding.tvDisplayCountry.text = item.displayCountry
        return binding.root
    }
}