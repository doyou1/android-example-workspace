package com.example.fragmentinfragmentsampling

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.material.button.MaterialButton

class SettingAboutFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // This callback will only be called when MyFragment is at least Started.
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    // Handle the back button event
                    Navigation.findNavController(requireView()).navigateUp()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }


    override fun onResume() {
        super.onResume()
        view?.findViewById<MaterialButton>(R.id.btn_back)?.setOnClickListener {
            Navigation.findNavController(requireView()).navigateUp()
        }
    }



    companion object {
        private var instance: SettingAboutFragment? = null

        fun getInstance() : SettingAboutFragment {
            if(instance == null) {
                instance = SettingAboutFragment()
            }
            return instance!!
        }
    }
}