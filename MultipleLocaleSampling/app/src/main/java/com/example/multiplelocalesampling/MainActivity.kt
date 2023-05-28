package com.example.multiplelocalesampling

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import com.example.multiplelocalesampling.databinding.ActivityMainBinding
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val SELECT_LANGUAGE_LIST = listOf(
        SelectLanguageItem(
            id = 0,
            displayCountry = "Korean",
            countryCode = "ko",
            flag = R.drawable.flag_ko
        ),
        SelectLanguageItem(
            id = 1,
            displayCountry = "English",
            countryCode = "en",
            flag = R.drawable.flag_en
        ),
        SelectLanguageItem(
            id = 2,
            displayCountry = "Japanese",
            countryCode = "ja",
            flag = R.drawable.flag_ja
        ),
        SelectLanguageItem(
            id = 3,
            displayCountry = "Simplified Chinese",
            countryCode = "zh-CN",
            flag = R.drawable.flag_zh
        ),
        SelectLanguageItem(
            id = 4,
            displayCountry = "Traditional Chinese",
            countryCode = "zh-TW",
            flag = R.drawable.flag_zh
        ),
        SelectLanguageItem(
            id = 5,
            displayCountry = "Vietnamese",
            countryCode = "vi",
            flag = R.drawable.flag_vi
        ),
        SelectLanguageItem(
            id = 6,
            displayCountry = "Indonesian",
            countryCode = "id",
            flag = R.drawable.flag_id
        ),
        SelectLanguageItem(
            id = 7,
            displayCountry = "Thai",
            countryCode = "th",
            flag = R.drawable.flag_th
        ),
        SelectLanguageItem(
            id = 8,
            displayCountry = "German",
            countryCode = "de",
            flag = R.drawable.flag_de
        ),
        SelectLanguageItem(
            id = 9,
            displayCountry = "Russian",
            countryCode = "ru",
            flag = R.drawable.flag_ru
        ),
        SelectLanguageItem(
            id = 10,
            displayCountry = "Spanish",
            countryCode = "es",
            flag = R.drawable.flag_es
        ),
        SelectLanguageItem(
            id = 11,
            displayCountry = "Italian",
            countryCode = "it",
            flag = R.drawable.flag_it
        ),
        SelectLanguageItem(
            id = 12,
            displayCountry = "French",
            countryCode = "fr",
            flag = R.drawable.flag_fr
        ),
    )

    private val TAG = this::class.java.simpleName
    private var locale: Locale? = null
    private val handler = Handler(Looper.getMainLooper())
    private var isReady = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvAppName.text = resources.getString(R.string.app_name)
        binding.spinnerSelectLanguage.adapter = SelectLanguageAdapter(SELECT_LANGUAGE_LIST)
        binding.spinnerSelectLanguage.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (isReady) {
                        setLocale(SELECT_LANGUAGE_LIST[position].countryCode)
                        finish()
                        startActivity(intent)
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
        initSpinner()
        handler.postDelayed({
            isReady = true
        }, 1000)
    }

    private fun initSpinner() {
        val locale = baseContext.resources.configuration.locale
        val filter = SELECT_LANGUAGE_LIST.filter { v -> v.countryCode == locale.language }
        if (filter.isNotEmpty()) {
            binding.spinnerSelectLanguage.setSelection(filter[0].id.toInt())
        }
    }

    private fun setLocale(lang: String) {
        val config = baseContext.resources.configuration
        locale = Locale(lang)
        Locale.setDefault(locale)
        setSystemLocale(config, locale!!)
        updateConfiguration(config)
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (locale != null) {
            setSystemLocale(newConfig, locale!!)
            Locale.setDefault(locale)
            updateConfiguration(newConfig)
        }
    }

    private fun getSystemLocale(config: Configuration): Locale? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.locales[0]
        } else {
            config.locale
        }
    }

    private fun setSystemLocale(config: Configuration, locale: Locale) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale)
        } else {
            config.locale = locale
        }
    }

    private fun updateConfiguration(config: Configuration) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            baseContext.createConfigurationContext(config)
        } else {
            baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
        }
    }
}