package com.example.customdrawingsampling

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.customdrawingsampling.databinding.ActivityMainBinding
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG = this::class.java.simpleName
    var users = mutableListOf<String>()
    var goals = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        setRecyclerView(binding.tvCount.text.toString().toInt())
        setClickEVent()
    }

    private fun setRecyclerView(count: Int) {
        setList(count)
        setLayoutManager(count)
        binding.rvUser.adapter = RvUserAdapter(users, this)
        binding.rvGoal.adapter = RvGoalAdapter(goals, this)
    }

    private fun setList(count: Int) {
        if (users.size == goals.size) {
            val need = count - users.size
            if (need >= 0) {
                if (users.size < MAX_LADDER_SIZE) {
                    for (i in 0 until need) {
                        users.add("$TEXT_USER${users.size}")
                        goals.add("$TEXT_GOAL${goals.size}")
                    }
                }
            } else {
                if (users.size > MIN_LADDER_SIZE) {
                    users.removeAt(users.size - 1)
                    goals.removeAt(goals.size - 1)
                }
            }
        }
    }

    private fun setLayoutManager(count: Int) {
        val spanCount = if (count > MAX_SPAN_COUNT) MAX_SPAN_COUNT else count
        val userLayoutManager = GridLayoutManager(this, spanCount)
        userLayoutManager.orientation = GridLayoutManager.VERTICAL
        binding.rvUser.layoutManager = userLayoutManager
        val goalLayoutManager = GridLayoutManager(this, spanCount)
        goalLayoutManager.orientation = GridLayoutManager.VERTICAL
        binding.rvGoal.layoutManager = goalLayoutManager
    }

    private fun setClickEVent() {
        binding.btnMinus.setOnClickListener {
            val prev = binding.tvCount.text.toString().toInt()
            val result = if (prev - 1 < MIN_LADDER_SIZE) {
                2
            } else {
                prev - 1
            }
            binding.tvCount.text = result.toString()
            setRecyclerView(result)
        }

        binding.btnPlus.setOnClickListener {
            val prev = binding.tvCount.text.toString().toInt()
            val result = if (prev + 1 > MAX_LADDER_SIZE) {
                10
            } else {
                prev + 1
            }
            binding.tvCount.text = result.toString()
            setRecyclerView(result)
        }

        binding.btnGo.setOnClickListener {
            startLadderActivity()
        }

        binding.layoutWrap.setOnClickListener {
            if (it !is TextInputEditText) {
                hideKeyboard()
            }
        }
    }

    fun changeListItem(value: String, position: Int, flag: Int) {

        when (flag) {
            FLAG_USER -> {
                users[position] = value
            }
            FLAG_GOAL -> {
                goals[position] = value
            }
        }
    }

    private fun hideKeyboard() {
        val im =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        im.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        currentFocus?.clearFocus()
    }

    private fun startLadderActivity() {
        val count = binding.tvCount.text.toString().toInt()

        val intent = Intent(this, LadderActivity::class.java)
        intent.putExtra(TEXT_COUNT, count)
        intent.putExtra(TEXT_USER, users.toTypedArray())
        intent.putExtra(TEXT_GOAL, goals.toTypedArray())
        startActivity(intent)
    }
}