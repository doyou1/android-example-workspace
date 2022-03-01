package com.example.fragmentbackstackproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"
    val fragmentManager = supportFragmentManager
    var count = 1
    var editText: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById<EditText>(R.id.edit_text)
        if (findViewById<FragmentContainerView>(R.id.main_frame) != null) {
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.main_frame, RedFragment())
            fragmentTransaction.commit()
        }

        fragmentManager.addOnBackStackChangedListener {
            for (i in 0 until fragmentManager.backStackEntryCount) {
                Log.e(TAG, fragmentManager.getBackStackEntryAt(i).name.toString())
            }
            Log.e(TAG, "--------------------------------")
        }
    }

    fun onClickRed(view: android.view.View) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.main_frame, RedFragment())
        fragmentTransaction.addToBackStack("Red - ${count++}")
        fragmentTransaction.commit()
    }
    fun onClickGreen(view: android.view.View) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.main_frame, GreenFragment())
        fragmentTransaction.addToBackStack("Green - ${count++}")
        fragmentTransaction.commit()
    }
    fun onClickBlue(view: android.view.View) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.main_frame, BlueFragment())
        fragmentTransaction.addToBackStack("Blue - ${count++}")
        fragmentTransaction.commit()
    }
    fun onClickBlack(view: android.view.View) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.main_frame, BlackFragment())
        fragmentTransaction.addToBackStack("Black - ${count++}")
        fragmentTransaction.commit()
    }

    fun onClickPop(view: android.view.View) {
        val text = editText?.text.toString()
        fragmentManager.popBackStack(text, 0)
        // flags
        // - FragmentManager.POP_BACK_STACK_INCLUSIVE(1) : 상위 스택부터 ~ 특정 fragment까지 지움
        // - 0 : 상위 스택부터 ~ 특정 fragment를 만날때까지 지움, 특정 fragment는 안지움
    }

}