package com.example.viewpagersampling

class SharedPreferenceUtil {

    companion object {
        fun getDayOfMonth() : String? {
            return BaseApplication.sharedPreferences.getString(TEXT_DAY_OF_MONTH, TEXT_EMPTY)
        }

        fun setDayOfMonth(dayOfMonth: Int) {
            BaseApplication.sharedPreferences.edit().putString(TEXT_DAY_OF_MONTH, String.format("%02d", dayOfMonth)).apply()
        }
    }

}