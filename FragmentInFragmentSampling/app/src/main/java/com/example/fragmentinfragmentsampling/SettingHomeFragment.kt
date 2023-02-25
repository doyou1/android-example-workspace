package com.example.fragmentinfragmentsampling

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.button.MaterialButton

class SettingHomeFragment : Fragment() {

    private var num = 0

    private var _navController: NavController? = null
    private val navController get() = _navController!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_setting_home, container, false)
        _navController = Navigation.findNavController(requireActivity(), R.id.fragment_container)

        return view
    }

    override fun onResume() {
        super.onResume()
        view?.findViewById<TextView>(R.id.tv_num)?.text = "${num++}"
        setClickEvent()
    }

    private fun setClickEvent() {
        view?.findViewById<MaterialButton>(R.id.btn_notifications)?.setOnClickListener {
            navController.navigate(R.id.action_home_to_notifications)
        }

        view?.findViewById<MaterialButton>(R.id.btn_privacy_security)?.setOnClickListener {
            navController.navigate(R.id.action_home_to_privacy_security)
        }

        view?.findViewById<MaterialButton>(R.id.btn_help_support)?.setOnClickListener {
            navController.navigate(R.id.action_home_to_help_support)
        }

        view?.findViewById<MaterialButton>(R.id.btn_about)?.setOnClickListener {
            navController.navigate(R.id.action_home_to_about)
        }
    }


    companion object {
        private var instance: SettingHomeFragment? = null

        fun getInstance(): SettingHomeFragment {
            if (instance == null) {
                instance = SettingHomeFragment()
            }
            return instance!!
        }
    }
}