package com.example.customdrawingsampling.roulette

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.customdrawingsampling.*
import com.example.customdrawingsampling.databinding.ActivityRouletteBinding
import com.example.customdrawingsampling.ladder.RvUserAdapter
import kotlin.random.Random

class RouletteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRouletteBinding
    private val TAG = this::class.java.simpleName
    var roulettes = mutableListOf<Roulette>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRouletteBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        setRoulette(binding.tvCount.text.toString().toInt())
        setClickEvent()
    }

    private fun setRoulette(count: Int) {
        setList(count)
        setRecyclerView()
        binding.rouletteView.setRoulette(roulettes)
    }

    private fun setClickEvent() {
        binding.btnMinus.setOnClickListener {
            val prev = binding.tvCount.text.toString().toInt()
            val result = if (prev - 1 < MIN_ROULETTE_SIZE) {
                MIN_ROULETTE_SIZE
            } else {
                prev - 1
            }
            binding.tvCount.text = result.toString()
            if (prev != result) setRoulette(result)
        }

        binding.btnPlus.setOnClickListener {
            val prev = binding.tvCount.text.toString().toInt()
            val result = if (prev + 1 > MAX_ROULETTE_SIZE) {
                MAX_ROULETTE_SIZE
            } else {
                prev + 1
            }
            binding.tvCount.text = result.toString()
            if (prev != result) setRoulette(result)
        }

        binding.btnRotate.setOnClickListener {
            val rotateListener = object : RotateListener {
                override fun onRotateStart() {
                    binding.tvResult.text = "Result: "
                }
                override fun onRotateEnd(result: String) {
                    binding.tvResult.text = "Result: $result"
                }
            }

            val toDegrees = (2000..10000).random().toFloat()
            binding.rouletteView.rotateRoulette(toDegrees, DELAY_ROULETTE, rotateListener)
        }

        binding.btnStopRotate.setOnClickListener {
            binding.rouletteView.stopRotateRoulette()
        }
    }

    private fun setList(count: Int) {
        val need = count - roulettes.size
        if (need >= 0) {
            if (roulettes.size < MAX_ROULETTE_SIZE) {
                for (i in 0 until need) {
                    val color = getColor()
//                    roulettes.add(Roulette("$TEXT_ROULETTE${roulettes.size}", color))
                    roulettes.add(Roulette("${roulettes.size}", color))
                }
            }
        } else {
            if (roulettes.size > MIN_ROULETTE_SIZE) {
                roulettes.removeAt(roulettes.size - 1)
            }
        }
    }

    private fun setRecyclerView() {
        setLayoutManager()
        binding.rvRoulette.adapter = RvRouletteAdapter(roulettes, this)
    }

    private fun setLayoutManager() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.rvRoulette.layoutManager = layoutManager
    }

    fun changeListItem(value: String, position: Int) {
        roulettes[position].text = value
        binding.rouletteView.setRoulette(roulettes)
    }

    private fun getColor(): String {
        var color = ""
        while (true) {
            color = getRandomColor()
            val filter = roulettes.filter { item -> item.color == color }
            if (filter.isEmpty()) break
        }
        return color
    }

    private fun getRandomColor(): String {
        val r = String.format("%02X", Random.nextInt(0, 256))
        val g = String.format("%02X", Random.nextInt(0, 256))
        val b = String.format("%02X", Random.nextInt(0, 256))
        return "#$r$g$b"
    }
}