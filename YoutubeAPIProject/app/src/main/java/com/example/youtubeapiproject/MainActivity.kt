package com.example.youtubeapiproject

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {

    private val TAG = this::class.java.simpleName.toString()
    private val API_KEY = resources.getString(R.string.api_key)
    private val baseUrl = "https://www.googleapis.com/"

    private val part = "snippet"
    private val channelId = "UCTTmtS2ljy1vyl_s-d_LEHQ"
    private val order = "date"
    private val query = Uri.parse("시선집중")
    private val maxResults = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val youtubeService = retrofit.create(YoutubeService::class.java)

        val call = youtubeService.getResult(part, channelId, order, query, maxResults, API_KEY)

        call.enqueue(object: Callback<YoutubeResponseModel> {
            override fun onResponse(
                call: Call<YoutubeResponseModel>,
                response: Response<YoutubeResponseModel>
            ) {
                if (response.code() == 200) {
//                    Log.e(TAG, "response.body: ${response.body()}")

                    val resBody = response.body() as YoutubeResponseModel

                    Log.e(TAG, "items: ${resBody.items}")
                }
            }

            override fun onFailure(call: Call<YoutubeResponseModel>, t: Throwable) {
                Log.e(TAG, "실패 : $t")
            }
        })
//        CoroutineScope(Dispatchers.Main).launch {
//            CoroutineScope(Dispatchers.Default).async {
//                CallYoutube(application).onCallYoutubeChannel()
//            }
//        }
    }
}

interface YoutubeService {

    @GET("youtube/v3/search")
    fun getResult(
        @Query("part") part: String,
        @Query("channelId") channelId: String,
        @Query("order") order: String,
        @Query("q") query: Uri,
        @Query("maxResults") maxResults: Int,
        @Query("key") key: String
    )
    : Call<YoutubeResponseModel>
}

data class YoutubeResponseModel(
    val kind: String?,
    val etag: String?,
    val nextPageToken: String?,
    val regionCode: String?,
    val pageInfo: HashMap<String, Long>?,
    val items: ArrayList<YoutubeVideo>
)

data class YoutubeVideo(
    val kind: String,
    val etag: String,
    val id: HashMap<String, String>?,
    val snippet: Snippet
)

data class Snippet(
    val publishedAt: String,
    val channelId: String,
    val title: String,
    val description: String,
    val thumbnails: HashMap<String, Thumbnail>,
    val channelTitle: String,
    val liveBroadCastContent: String,
    val publishTime: String
)

data class Thumbnail(
    val url: String,
    val width: Int,
    val height: Int
)

//class CallYoutube(private var application: Application) {
//
//    private val API_KEY = "AIzaSyBDvC8lfhx-uG94koTYGQGqrqBIp7bPoZA"
//    private val TAG = this::class.java.simpleName.toString()
//
//    private val HTTP_TRANSPORT: HttpTransport = NetHttpTransport()
//    private val JSON_FACTORY: JsonFactory = GsonFactory()
//    private val NUMBER_OF_VIDEOS_RETURNED: Long = 50
//
//    fun onCallYoutubeChannel() {
//        try {
//            val query = "kpop+music"
//
//            val youtube = YouTube.Builder(
//                HTTP_TRANSPORT, JSON_FACTORY
//            ) { }.setApplicationName("youtube-search-sample").build()
//            val search = youtube.search().list("id,snippet")
//            val searchResultList = search.setKey(API_KEY)
//                .setQ(query)
////                .setChannelId("UCXTyJZJW0kjz5lCI7g8wUKQ")
//                .setOrder("date")
//                .setType("video")
//                .setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url,snippet)")
//                .setMaxResults(NUMBER_OF_VIDEOS_RETURNED)
//                .execute()
//                .items
//            if (searchResultList != null) {
//                Log.e(TAG, "searchResultList: $searchResultList")
//            }
//        } catch (e: Throwable) {
//            Log.e("Exception Occurred : ", e.toString())
//        }
//    }
//
//}