package com.example.commutesampling

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.commutesampling.databinding.FragmentHomeBinding
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val TAG = this::class.java.simpleName
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    override fun onResume() {
        super.onResume()
        setClickEvent()
    }

    private fun setClickEvent() {
        binding.btnSetting.setOnClickListener {
            activity?.let { context ->
                val popup = PopupMenu(context, binding.btnSetting)
                popup.setOnMenuItemClickListener { item ->
                    when(item.title) {
                        context.resources.getString(R.string.text_set_default_time) -> {
                            context.startActivity(Intent(context, DefaultTimeSettingActivity::class.java))
                        }
                    }
                    false
                }
                popup.menu.add(R.string.text_set_default_time)
                popup.show()
            }
        }

        binding.btnIn.setOnClickListener {
            binding.tvIn.text = binding.tvCurrentTime.text
        }

        binding.btnOut.setOnClickListener {
            binding.tvOut.text = binding.tvCurrentTime.text
        }
    }

    private fun initUI() {
        // init time
        binding.tvCurrentTime.text = Util.getCurrentTime()

        activity?.let { context ->
            val pref = context.getSharedPreferences(TEXT_SETTING, Context.MODE_PRIVATE)
            val strIn = pref.getString(TEXT_WORK_START_TIME, "09:00")
            val strOut = pref.getString(TEXT_WORK_END_TIME, "18:00")
            binding.tvIn.text = "${Util.getCurrentDate()} $strIn"
            binding.tvOut.text = "${Util.getCurrentDate()} $strOut"
        }
        handler.removeCallbacksAndMessages(null)
        handler.postDelayed(::repeatSetCurrentTime, 900)
    }

    private fun repeatSetCurrentTime() {
        if (binding.tvCurrentTime.text.toString() != Util.getCurrentTime()) {
            binding.tvCurrentTime.text = Util.getCurrentTime()
        }
        handler.postDelayed(::repeatSetCurrentTime, 300)
    }

    companion object {
        private var instance: HomeFragment? = null
        fun getInstance(): HomeFragment {
            if (instance == null) {
                instance = HomeFragment()
            }
            return instance!!
        }
    }
}