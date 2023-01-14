package com.example.roomdbimagesampling

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdbimagesampling.databinding.ActivityImageBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Part

class ImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImageBinding
    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        setImages()
    }

    private fun setImages() {
        lifecycleScope.launch(Dispatchers.IO) {
            val list = (application as BaseApplication).imageDao.getAll()
            lifecycleScope.launch(Dispatchers.Main) {
                setRecyclerView(list)
            }
        }
    }


    private fun setRecyclerView(list: List<Image>) {
        val names = arrayListOf<String>()
        for (item in list) {
            names.add(item.name)
        }

        val map = getImagesByNames(names)

        setLayoutManager()
        binding.recyclerView.adapter = ImageRVAdapter(list)
    }

    private fun getImagesByNames(list: List<String>): HashMap<String, ByteArray> {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val imageService = retrofit.create(ImageService::class.java)

        val call: Call<HashMap<String, ByteArray>> = imageService.get(list)

        call.enqueue(object : Callback<HashMap<String, ByteArray>> {
            override fun onResponse(
                call: Call<HashMap<String, ByteArray>>,
                response: Response<HashMap<String, ByteArray>>
            ) {
                Log.e(TAG, "${response.body()}")
            }

            override fun onFailure(call: Call<HashMap<String, ByteArray>>, t: Throwable) {
                //Handle failure
                Log.e(TAG, "fail")
                t.printStackTrace()
            }
        })

        return hashMapOf()
    }

    private fun setLayoutManager() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerView.layoutManager = layoutManager
    }

}