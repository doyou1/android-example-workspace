package com.example.qnaproject.fragment

import android.os.Bundle
import android.provider.MediaStore.MediaColumns.ORIENTATION
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.qnaproject.*
import com.example.qnaproject.databinding.FragmentSwipeBinding
import com.example.qnaproject.databinding.FragmentViewPagerItemBinding
import com.example.qnaproject.domain.*
import com.example.qnaproject.responseModel.ProductResponseModel
import com.example.qnaproject.responseModel.QnaResponseModel
import com.example.qnaproject.service.ProductService
import com.example.qnaproject.service.QnaService
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 *  ProductActivity 세번째 화면
 */
class SwipeFragment : Fragment() {

    private lateinit var binding: FragmentSwipeBinding
    private lateinit var adapter: PageApater


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_swipe, container, false)
        adapter = PageApater(this)
        binding.viewPager.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TabLayout과 ViewPager2 연동
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when(position) {
                0 -> tab.text = "팝업 리스트"
                1 -> tab.text = "평가 리스트"
                else -> tab.text = "에러"
            }
        }.attach()
    }
}

class PageApater(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2    // 팝업 리스트, 평가 리스트

    override fun createFragment(position: Int): Fragment {
        val fragment  = when(position) {
            0 -> PopupFragment()
            1 -> EstimationFragment()
            else -> PopupFragment() // 추후 에러 페이지 추가 고려
        }
        return fragment

    }
}


/**
 * PopupFragment 대처 이벤트
 * 1. 최초 접근                     : 리사이클러뷰 초기화, 인터페이스 호출, 클릭 및 리프레쉬 이벤트 설정
 * 2. Estimation to Popup         : 리사이클러뷰 초기화, 인터페이스 호출, 클릭 및 리프레쉬 이벤트 재설정
 * 3. pull to refresh             : 리사이클러뷰 아이템 removeAll, 인터페이스 호출
 * 4. 바텀 스크롤 X
 */
class PopupFragment() : Fragment() {

    // 안드로이드는 기본적으로 http 통신을 보안상 금지하고 있음
    // 관련한 허용코드는 AndroidManifest.xml -> networkSecurityConfig 속성에서 확인 가능
    private val baseUrl = "http://211.254.212.85:8080/"
    private val TAG = "PopUpFragment"
//    val categoryCd = "ALL"
//    val memId = 60
//    var page = 1

    private lateinit var binding: FragmentViewPagerItemBinding
    private var list = arrayListOf<Popup>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_view_pager_item, container, false)
        Log.e(tag, "onCreateView")

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        Log.e(tag, "onResume")

        setRecyclerView()
        setClickEvent()
        setRefreshEvent()
        setPopupList()
    }

    /**
     * 리사이클러뷰 초기화
     */
    private fun setRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = PopupAdapter(list)
        // 아이템별 구분선 추가
        binding.recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager(requireContext()).orientation))
    }

    /**
     * 클릭이벤트 설정
     */
    private fun setClickEvent() {
        Log.e(tag, "setClickEvent")
    }

    /**
     * pull to refresh 이벤트 발생시
     * 이전 리스트 제거 및 새로운 리스트 호출 기능
     */
    private fun setRefreshEvent() {
        binding.pullToRefresh.setOnRefreshListener {
            //새로고침 내용
            setPopupList()  // 새로운 리스트 셋팅
            // 새로고침 중지
            // 없으면 새로고침 애니메이션 끝나지 않음
            binding.pullToRefresh.isRefreshing = false
        }
    }

    /**
     * 기존 리스트 초기화 및 새로운 리스트 갱신 메서드
     * (최초 onResume에서도 동일하게 사용 가능)
     */
    private fun setPopupList() {
        list.clear()
        binding.recyclerView.adapter?.notifyDataSetChanged()
        addPopupList()
    }

    /**
     * 인터페이스 URL 호출 및 새로운 리스트 추가 메서드
     */
    private fun addPopupList() {
        Log.e(tag, "setPopupList")

        // Retrofit 객체 생성
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create()) // JSON 변환 객체 등록
            .build()

        // Retrofit 객체로 service 인터페이스 구현
        val networkDataService = retrofit.create(NetworkDataService::class.java)

        // List 데이터에 접근하는 call 객체 생성
        // params(MEM_ID, ITM_ONLY_VIEW, PAGE)
        val call: Call<PopupListResponseModel> = networkDataService.getPopupList()
        // 선언한 call 객체에 queue 추가
        call.enqueue(object : Callback<PopupListResponseModel> {
            override fun onResponse(
                call: Call<PopupListResponseModel>,
                response: Response<PopupListResponseModel>
            ) { // Response Success
                // ResponseBody의 형태에 따라 Custom ResponseModel로 변환
                val resBody = response.body() as PopupListResponseModel
                Log.e(tag, resBody.toString())
                val newList = resBody.data
                Log.e(tag, newList.toString())

//                java.lang.IndexOutOfBoundsException: Inconsistency detected. 에러 문제로 새로운 로직 구현
//                if (newList.size > 0) {
//                    list.addAll(newList)
//                    binding.recyclerView.adapter?.notifyDataSetChanged()
//                }

                if (newList.size > 0) { // 서버로부터의 데이터가 있다면
                    val positionStart = list.size   // notifyItemRangeChanged 시작 지점
                    val itemCount = newList.size    // 새로운 item의 수

                    list.addAll(newList)
                    binding.recyclerView.adapter?.notifyItemRangeChanged(positionStart, itemCount)
                }

                binding.tvCount.text = list.size.toString()

            }

            override fun onFailure(call: Call<PopupListResponseModel>, t: Throwable) {   // Response Fail
                Log.d(tag, "실패 : $t")
            }
        })
    }

}

/**
 * EstimationFragment 대처 이벤트
 * 1. 최초 접근(Popup to Estimation로 동일하나 onCreateView, onResume을 거침) : 리사이클러뷰 초기화, 인터페이스 호출, page=1, 클릭 및 리프레쉬 이벤트 설정
 * 2. Popup to Estimation(onResume)         : 리사이클러뷰 아이템 clear, 인터페이스 호출, page=1, 클릭 및 리프레쉬 이벤트 재설정(onPause 활용)
 * 3. pull to refresh             : 리사이클러뷰 아이템 clear, 인터페이스 호출, page=1
 * 4. 바텀 스크롤                   : 인터페이스 호출, page++
 */
class EstimationFragment() : Fragment() {

    private val baseUrl = "http://211.254.212.85:8080/"
    private val TAG = "EstimationFragment"
    val categoryCd = "ALL"
    val memId = 60
    var page = -1

    private var isCreateView = false    // onCreateView 진입여부 플래그
    private var isAdding = false        // 인터페이스 추가 호출 방지 목적 플래그(인테페이스 호출 및 리스트 추가 처리 중엔 true)
    private var isLast = false          // sizePerPage보다 작은 리스트 사이즈일 경우, 마지막 페이지이니 바텀 스크롤 이벤트를 제거
    private var sizePerPage = 20

    private lateinit var binding: FragmentViewPagerItemBinding
    private var list = arrayListOf<Estimation>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_view_pager_item, container, false)
        Log.e(tag, "onCreateView")
        isCreateView = true
        return binding.root
    }

    /**
    * 1. 최초 접근(Popup to Estimation로 동일하나 onCreateView를 거침)
    *   : 리사이클러뷰 초기화, 인터페이스 호출, page=1, 클릭 및 리프레쉬 이벤트 설정
    *
    * 2. Popup to Estimation
    *   : 리사이클러뷰 아이템 clear, 인터페이스 호출, page=1, 클릭 및 리프레쉬 이벤트 재설정
     *   (onPause 활용하려했으나, removeListner가 없거나 이벤트를 add가 아닌 set하는 부분이라 중복 set외에는 크게 문제없어보임)
    */
    override fun onResume() {
        super.onResume()
        Log.e(tag, "onResume")
        if (isCreateView) { // onCreateView를 거칠때에만 리사이클러뷰 초기화 생성
            isCreateView = false
            setRecyclerView()
        } else {
            list.clear()
            binding.recyclerView.adapter?.notifyDataSetChanged()
        }

        setRecyclerViewScrollEvent()
        setRefreshEvent()
        page = 1
        isLast = false
        addEstimationList()
    }
    
    private fun setRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = EstimationAdapter(list)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager(requireContext()).orientation))
    }


    // 3. pull to refresh : 리사이클러뷰 아이템 clear, 인터페이스 호출, page=1
    private fun setRefreshEvent() {
        Log.e(tag, "setRefreshEvent")
        binding.pullToRefresh.setOnRefreshListener {
            // 새로고침 중지
            list.clear()                // 모든 리스트 클리어
            binding.recyclerView.adapter?.notifyDataSetChanged()
            // 최신 데이터를 가져오기 위해 페이지 `1`로 변경
            page = 1
            addEstimationList()

            /**
             * 지속적으로 오류가 생기던 부분
             * 바텀스크롤을 모두 맨밑으로 내려 스크롤 이벤트를 clear하고 (350:353)
             * 리프레쉬 이벤트를 발생시키면 스크롤 이벤트가 없어진채 1페이지만 진행
             * 그 후 추가적인 데이터를 받아올 수 없는 문제 발생
             * 때문에 isLast 플래그 설정 및 스크롤 이벤트 재설정
             */
            if(isLast) {
                isLast = false
                setRecyclerViewScrollEvent()
            }
            // 없으면 새로고침 애니메이션 끝나지 않음
            binding.pullToRefresh.isRefreshing = false
        }
    }

    // 4. 바텀 스크롤 : 인터페이스 호출, page++
    private fun setRecyclerViewScrollEvent() {
        binding.recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!isLast) {
                    super.onScrolled(recyclerView, dx, dy)
                    // 현재 보여지는 아이템 중 가장 마지막 아이템의 position
                    val lastVisibleItemPosition = (recyclerView.layoutManager as? LinearLayoutManager)?.findLastCompletelyVisibleItemPosition() ?: -1
                    // 아이템의 전체 개수(position은 항상 하나씩 앞으로 밀리기때문에 `-1`)
                    val itemTotalCount = recyclerView.adapter?.itemCount?.minus(1) ?: -1
                    if (lastVisibleItemPosition > 0 && itemTotalCount > 0   // Refresh 이벤트시 lastVisibleItemPosition, itemTotalCount가 `-1`이 되는 문제 해결
                        && lastVisibleItemPosition == itemTotalCount    // 리사이클러뷰의 최하단에 위치했을때
                        && !isAdding) {                                 // 데이터 접근 중이 아닐때

                        isAdding = true
                        page++
                        addEstimationList()
                    }
                }
            }
        })
    }

    private fun addEstimationList() {
        Log.e(tag, "addEstimationList")

        // Retrofit 객체 생성
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create()) // JSON 변환 객체 등록
            .build()

        // Retrofit 객체로 service 인터페이스 구현
        val networkDataService = retrofit.create(NetworkDataService::class.java)

        // List 데이터에 접근하는 call 객체 생성
        // params(MEM_ID, ITM_ONLY_VIEW, PAGE)
        val call: Call<EstimationListResponseModel> = networkDataService.getEstimationList(categoryCd, memId, page)
        // 선언한 call 객체에 queue 추가
        call.enqueue(object : Callback<EstimationListResponseModel> {
            override fun onResponse(
                call: Call<EstimationListResponseModel>,
                response: Response<EstimationListResponseModel>
            ) { // Response Success
                // ResponseBody의 형태에 따라 Custom ResponseModel로 변환
                val resBody = response.body() as EstimationListResponseModel
                Log.e(tag, resBody.toString())
                val newList = resBody.data
                Log.e(tag, newList.toString())


                // 한 페이지의 개수가 20개
                // 새로운 리스트가 20개가 아니라면 마지막 페이지
                // 이전 조건인 newList.size > 0는 마지막 페이지 이후 한번 더 호출을 해
                // 한타임 느리게 scroll 이벤트를 제거함(한타임 버벅이는 문제 발생)
                if (newList.size < sizePerPage) {
                    isLast = true
                    binding.recyclerView.clearOnScrollListeners()
                }

                val positionStart = list.size   // notifyItemRangeChanged 시작 지점
                val itemCount = newList.size    // 새로운 item의 수
                list.addAll(newList)
                binding.recyclerView.adapter?.notifyItemRangeChanged(positionStart, itemCount)

                binding.tvCount.text = list.size.toString()
                isAdding = false
            }

            override fun onFailure(call: Call<EstimationListResponseModel>, t: Throwable) {   // Response Fail
                Log.d(tag, "실패 : $t")
            }
        })
    }

}

data class EstimationListResponseModel(val code: Int, val message: String?, val data: ArrayList<Estimation>)
data class PopupListResponseModel(val code: Int, val message: String?, val data: ArrayList<Popup>)

