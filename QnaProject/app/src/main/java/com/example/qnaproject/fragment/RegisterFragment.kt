package com.example.qnaproject.fragment

<<<<<<< HEAD
import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.allViews
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.qnaproject.R
import com.example.qnaproject.activity.ProductActivity
import com.example.qnaproject.databinding.FragmentRegisterBinding
import com.example.qnaproject.responseModel.ProductResponseModel
import com.example.qnaproject.service.ProductService
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Url
import java.io.File
import java.io.FileNotFoundException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// 유효성 검사 결과 FLAG
private val SUCCESS = 0
private val OVERFLOW_NM = 1
private val NULL_NM = 2

private val OVERFLOW_PRICE = 3
private val NOT_NUMBER_PRICE = 4

private val OVERFLOW_DIS = 5
private val NOT_NUMBER_DIS = 6

private val OVERFLOW_INTRO = 7
private val NULL_INTRO = 8

// 텍스트 제한
private val nmTextLimit = 30
private val introTextLimit = 30

// 가격, 할인율 제한
private val priceLimit = 100000
private val disLimit = 100

=======
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.qnaproject.R
import com.example.qnaproject.activity.ProductActivity
import com.example.qnaproject.databinding.FragmentRegisterBinding
import retrofit2.http.Url
>>>>>>> a08212a752b7cca2ae4275252684a64966ae1fff

/**
 * ProductActivity 두번째 화면
 * 상품 등록 화면
 */
class RegisterFragment : Fragment() {

    private val baseUrl = "https://api.jamjami.co.kr/"
    private lateinit var binding: FragmentRegisterBinding
<<<<<<< HEAD
    private lateinit var mContext: Context
    private lateinit var backButtonCallback: OnBackPressedCallback
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>

    private var transactionCode = "N"
    private var uploadUri: Uri? = null
//    private val MEM_ID = 94           // 로그인 유저 id
//    private var PAGE = 1              // 페이지 변수

    private val MEM_ID = 94             // 로그인 유저 id
    private val PTR_ID = 43             // 파트너사 id

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
=======
    private lateinit var backButtonCallback: OnBackPressedCallback
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private lateinit var mContext: Context

//    private val MEM_ID = 94             // 로그인 유저 id
//    private val ITM_ONLY_VIEW = "N"
//    private var PAGE = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        // ImageView에 설정돼있는 background 설정 플래그
        // (default 값에는 border가 추가됨, 이미지가 추가되면 border를 지워야함)
>>>>>>> a08212a752b7cca2ae4275252684a64966ae1fff
        galleryLauncher = getGalleryActivityResultLauncher()
        setClickEvent()

        return binding.root
    }


    /**
     * 레이아웃 아이템 클릭 이벤트
     */
    private fun setClickEvent() {
        binding.toolbarProductRegister.tbBack.setOnClickListener {  // 툴바 '<'(BackButton) 클릭 이벤트
            moveToHomeFrag()
        }
<<<<<<< HEAD
        binding.ibAccessGallery.setOnClickListener {    // ImageView 클릭시 갤러리 앱으로 이동
            openGallery()
        }
        binding.ibRemoveImage.setOnClickListener {
            removeImage()
        }
        binding.btnItmRegister.setOnClickListener {
            checkTextValidation()
        }

        /**
         * ViewGroup A > View A
         * Whenever ACTION_DOWN on ViewGroup A occurs,
         * the ontouch listener will log a message.
         * So if I return false,
         * both ViewGroup A and View A would return a log when ACTION_DOWN occurs?
         * and if I return true, only ViewGroup A would log the message?
         *
         * ViewGroup A에서 ACTION_DOWN이 발생할 때마다 온터치 수신기가 메시지를 기록합니다.
         * 그러면 false를 반환하면 ACTION_DOWN이 발생할 때
         * ViewGroup A와 View A 모두 로그를 반환하고
         * true를 반환하면 ViewGroup A만 메시지를 기록합니다.
         */
        binding.clRootView.allViews.iterator().forEach {
            it.setOnTouchListener { view, motionEvent ->
                if (view !is EditText) {
                    hideSoftKeyboard(mContext)
                }
                false
            }
        }

    }

    /**
     * 갤러리 앱을 열기 위한 권한 체크 및 이후 결과에 따른 처리 메서드
     */
    private fun openGallery() {
        /**
         * 갤러리 이미지의 bytearray를 얻기 위한 권한 확인 및 설정
         */
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                1
            )
        } else {
            openGalleryActivityResultLauncher() // 권한이 있다면 갤러리 오픈
        }
    }

    /**
     * requestPermissions 메서드 호출에 따른 콜백
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permission: Array<String>,
        grantResults: IntArray
    ) {
        when(requestCode) {
            1 -> {  // READ_EXTERNAL_STORAGE 권한 requestCode
                // 권한 허용(grant)
                if (grantResults.size > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery()   // 권한 허용 됐으므로, 갤러리 오픈을 위한 재호출
                } else {    // 권한 거부(denied)
                    Toast.makeText(mContext, "갤러리 접근 권한이 거부됐습니다", Toast.LENGTH_SHORT).show()
                }
                return;
            }
        }
    }
    /**
     * 키보드를 숨기는(닫는) 메서드
     */
    private fun hideSoftKeyboard(mContext: Context) {
        // InputMethodManager 객체 생성
        val inputMethodManager = mContext.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.root.windowToken, 0);
    }

    /**
     * 상품 등록 인터페이스 URL 접근
     */
    private fun registerProduct() {
        // 유효성 검사 완료한 텍스트
        val itmNm = binding.etItmNm.text.toString()
        val itmPrice = binding.etItmPrice.text.toString()
        val itmDis = binding.etItmDis.text.toString()
        val itmIntro = binding.etItmIntro.text.toString()
        var itmViewYN = binding.rgViewYn.checkedRadioButtonId.let { id ->
            if (id == binding.rbViewY.id) "Y"
            else if (id == binding.rbViewN.id) "N"
            else "N"
        }
        
        var bytes: ByteArray? = null
        if (transactionCode == "C") {   // 이미지를 업로드할 경우
            val imageAbsolutePath = getRealPathFromURIForGallery()
            imageAbsolutePath?.let {
                try {
                    // 절대경로로 접근해 이미지의 바이트화
                    bytes = File(imageAbsolutePath).readBytes()
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                    checkPermission()
                }
            }
        }

        // Text to RequestBody
        val memIdBody = RequestBody.create(MediaType.parse("text/plain"), MEM_ID.toString())
        val ptrIdBody = RequestBody.create(MediaType.parse("text/plain"), PTR_ID.toString())

        val itmNmBody = RequestBody.create(MediaType.parse("text/plain"), itmNm)
        val itmPriceBody = RequestBody.create(MediaType.parse("text/plain"), itmPrice)
        val itmDisBody = RequestBody.create(MediaType.parse("text/plain"), itmDis)
        val itmIntroBody = RequestBody.create(MediaType.parse("text/plain"), itmIntro)
        val itmViewYNBody = RequestBody.create(MediaType.parse("text/plain"), itmViewYN)

        // RequestBodys to RequestMap
        val requestMap = HashMap<String, RequestBody>().also {
            it.put("MEM_ID", memIdBody)
            it.put("PTR_ID", ptrIdBody)
            it.put("ITM_NM", itmNmBody)
            it.put("ITM_PRICE", itmPriceBody)
            it.put("ITM_DIS", itmDisBody)
            it.put("ITM_INTRO", itmIntroBody)
            it.put("ITM_VIEW_YN", itmViewYNBody)
        }

        // 업로드할 이미지의 ByteArray MultipartBody.Part 객체로 변환
        val requestFile: RequestBody? = bytes?.let {
            val result = RequestBody.create(MediaType.parse("image/png"), it)
            result
        }

        val itmImg1Body: MultipartBody.Part? = requestFile?.let {
            val result = MultipartBody.Part.createFormData("ITM_IMG1", getImageFileName(), it)
            result
        }

        // Retrofit 객체 생성
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create()) // JSON 변환 객체 등록
            .build()

        // Retrofit 객체로 service 인터페이스 구현
        val productService = retrofit.create(ProductService::class.java)

        // List 데이터에 접근하는 call 객체 생성
        val call: Call<ProductResponseModel> = productService.registerProduct(requestMap, itmImg1Body)
        // 선언한 call 객체에 queue 추가
        call.enqueue(object : Callback<ProductResponseModel> {
            override fun onResponse(
                call: Call<ProductResponseModel>,
                response: Response<ProductResponseModel>
            ) { // Response Success
                // ResponseBody의 형태에 따라 Custom ResponseModel로 변환
//                val resBody = response.body() as ProductResponseModel

                resultProcess(response.body()?.code ?: 400)

            }

            override fun onFailure(
                call: Call<ProductResponseModel>,
                t: Throwable
            ) {   // Response Fail
                Log.d(tag, "실패 : $t")
            }
        })
    }

    /**
     * 서버에 저장될 이미지 이름 설정 메서드
     */
    private fun getImageFileName(): String? {
        val currentDate = getCurrentDate()
        
        // Maybe only 'C'
        // 상품 수정 구조와 맞추기 위한 코드
        // C-01-YYMMDDHHmmSS.png
        return "$transactionCode-01-$currentDate.png"
    }

    /**
     * YYMMDDHHmmSS로 포매팅된 현재시간 문자열 생성 메서드
     */
    private fun getCurrentDate(): String {
        val current = LocalDateTime.now()
        var formatter = DateTimeFormatter.ofPattern("YYMMDDHHmmSS")
        return current.format(formatter)
    }

    /**
     * uploadUri(갤러리에서 선택된 이미지의 uri)를 통해
     * 이미지의 절대경로 출력
     */
    private fun getRealPathFromURIForGallery(): String? {
        uploadUri?.let {
            val projection = arrayOf(MediaStore.Images.Media.DATA)
            val cursor: Cursor = mContext.contentResolver.query(
                it, projection, null,
                null, null
            )!!
            if (cursor != null) {
                val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                cursor.moveToFirst()
                return cursor.getString(column_index)
            }
            cursor.close()
            return it.path
        }
        return null
    }

    /**
     * 갤러리 이미지의 bytearray를 얻기 위한 권한 설정
     */
    private fun checkPermission() {
        if (mContext.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                mContext as Activity,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                1
            )
        }
    }

    /**
     * 인터페이스 Url 요청 후 응답코드에 따른 처리 메서드
     */
    private fun resultProcess(code: Int) {
        when (code) {
            400 -> Toast.makeText(
                mContext,
                "서버 오류로 상품 등록이 실패했습니다.",
                Toast.LENGTH_SHORT
            ).show()
            200 -> {
                Toast.makeText(
                    mContext,
                    "상품 등록 성공!",
                    Toast.LENGTH_SHORT
                ).show()

                // move to HomeFragment & RecyclerView Refresh
                moveToHomeFragToCheckRegisterSuccess()
            }
            else -> Toast.makeText(
                mContext,
                "서버 오류로 상품 등록이 실패했습니다.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    /**
     * 유효성 검사 결과 처리 메서드
     * when을 통해 이후 처리 수행
     */
    private fun checkTextValidation() {
        // 유효성 검사, 결과에 따라 Toast 메시지 출력 및 상품 리스트화면 이동
        when (validateText()) {
            SUCCESS -> {
                registerProduct()
            }
            OVERFLOW_NM -> Toast.makeText(
                mContext,
                "상품 이름의 글자수를 확인해주세요(최대: ${nmTextLimit})",
                Toast.LENGTH_SHORT
            ).show()
            NULL_NM -> Toast.makeText(
                mContext,
                "상품 이름의 글자수를 확인해주세요(최대: ${nmTextLimit})",
                Toast.LENGTH_SHORT
            ).show()
            OVERFLOW_PRICE -> Toast.makeText(
                mContext,
                "상품 가격을 확인해주세요(최대: ${priceLimit})",
                Toast.LENGTH_SHORT
            ).show()
            NOT_NUMBER_PRICE -> Toast.makeText(
                mContext,
                "상품 가격에 숫자가 아닌 값이 들어있습니다. 확인해주세요(최대: ${priceLimit})",
                Toast.LENGTH_SHORT
            ).show()
            OVERFLOW_DIS -> Toast.makeText(
                mContext,
                "상품 할인율을 확인해주세요(최대: ${disLimit})",
                Toast.LENGTH_SHORT
            ).show()
            NOT_NUMBER_DIS -> {
                Toast.makeText(
                    mContext,
                    "상품 할인율에 숫자가 아닌 값이 들어있습니다. 확인해주세요(최대: ${disLimit})",
                    Toast.LENGTH_SHORT
                ).show()
            }
            OVERFLOW_INTRO -> Toast.makeText(
                mContext,
                "상품 설명의 글자수를 확인해주세요(최대: ${introTextLimit})",
                Toast.LENGTH_SHORT
            ).show()
            NULL_INTRO -> Toast.makeText(
                mContext,
                "상품 설명의 글자수를 확인해주세요(최대: ${introTextLimit})",
                Toast.LENGTH_SHORT
            ).show()
            else -> Toast.makeText(mContext, "작성된 텍스트 다시 한번 확인해주세요", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 유효성 검사 메서드
     * ITM_NM - 글자수 제한
     * ITM_PRICE - 숫자인지, 특정 가격 이상인지
     * ITM_DIS - 숫자인지, 100이상인지
     */
    private fun validateText(): Int {
        val itmNm = binding.etItmNm.text.toString()
        val itmPrice = binding.etItmPrice.text.toString()
        val itmDis = binding.etItmDis.text.toString()
        val itmIntro = binding.etItmIntro.text.toString()

        Log.e(tag, "${itmNm}, ${itmPrice}, ${itmDis}, ${itmIntro}")

        // ITM_NM 유효성검사
        if (itmNm.length > nmTextLimit) return OVERFLOW_NM
        else if (itmNm.length == 0 || itmNm.equals("")) return NULL_NM

        // ITM_PRICE 유효성검사
        if (itmPrice.toIntOrNull() == null) return NOT_NUMBER_PRICE
        else {
            if (itmPrice.toInt() > priceLimit) return OVERFLOW_PRICE
        }

        // ITM_DIS 유효성검사
        if (itmDis.toIntOrNull() == null) return NOT_NUMBER_DIS
        else {
            if (itmDis.toInt() > disLimit) return OVERFLOW_DIS
        }

        // ITM_INTRO 유효성검사
        if (itmIntro.length > introTextLimit) return OVERFLOW_INTRO
        else if (itmIntro.isEmpty() || itmIntro.equals("")) return NULL_INTRO

        return SUCCESS
=======
        binding.ibAccessGallery.setOnClickListener {
            openGalleryActivityResultLauncher()
        }
>>>>>>> a08212a752b7cca2ae4275252684a64966ae1fff
    }

    /**
     * Gallery에 접근하기 위한 ActivityResultLauncher 선언 및 리턴 메서드
     * Gallery에 접근한 이후 작업은 if (result.resultCode == Activity.RESULT_OK) 내부에 선언
     */
<<<<<<< HEAD
    private fun getGalleryActivityResultLauncher(): ActivityResultLauncher<Intent> {
        return registerForActivityResult(
            ActivityResultContracts
                .StartActivityForResult()
        ) { result ->
=======
    private fun getGalleryActivityResultLauncher() : ActivityResultLauncher<Intent> {
        return registerForActivityResult(
            ActivityResultContracts
                .StartActivityForResult()) { result ->
>>>>>>> a08212a752b7cca2ae4275252684a64966ae1fff

            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data

                data?.let { data ->
<<<<<<< HEAD
                    uploadUri = data.data
                    Glide.with(mContext)
                        .load(uploadUri)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .listener(loadImageListener())
=======
                    val uri = data.data
                    Glide.with(mContext)
                        .load(uri)
>>>>>>> a08212a752b7cca2ae4275252684a64966ae1fff
                        .placeholder(R.drawable.ic_baseline_add_36)
                        .error(R.drawable.ic_baseline_add_36)        //
                        .into(binding.ibAccessGallery)
                }
            }
        }
    }

    /**
     * 선언된 gallery 접근 런처를 실행시키는 메서드
     */
    private fun openGalleryActivityResultLauncher() {
        val intent = Intent(Intent.ACTION_PICK).also {
            it.setType("image/*")
            val mimeTypes = arrayOf("image/jpg")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        }
        galleryLauncher.launch(intent)
    }

    /**
<<<<<<< HEAD
     * Glide 이미지 로드 성공, 실패 여부 확인 리스너
     * 트랜잭션 코드 및 이미지 삭제 버튼 쇼잉 여부 설정
     */
    private fun loadImageListener(): RequestListener<Drawable> {
        return object : RequestListener<Drawable> {

            // Image Load 실패 시 CallBack
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                /**
                 * 1. 갤러리 접근 데이터 fail - C
                 */
                transactionCode = "N"
                uploadUri = null    // Glide Load 이전에 담아둔 uploadUri 비우기
                hideRemoveImageButton() // 이미지 로드 실패시 remove button 쇼잉
                return false
            }

            // Image Load 후 CallBack
            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                /**
                 * 2. 갤러리 접근 데이터 success - C
                 */
                transactionCode = "C"
                showRemoveImageButton() // 이미지 로드 성공시 remove button 숨김
                return false
            }
        }
    }

    /**
     * 이미지 삭제 버튼 클릭시
     * 이미지 삭제(비우기) 처리
     */
    private fun removeImage() {
        Glide.with(mContext)
            .clear(binding.ibAccessGallery)

        /**
         * 3. 삭제 버튼 클릭 - N
         */
        transactionCode = "N"
        uploadUri = null
        hideRemoveImageButton()
    }


    /**
     * RemoveImageButton을 보여주는 메서드
     */
    private fun showRemoveImageButton() {
        binding.ibRemoveImage.visibility = View.VISIBLE
    }

    /**
     * RemoveImageButton을 숨기는 메서드
     */
    private fun hideRemoveImageButton() {
        binding.ibRemoveImage.visibility = View.GONE
    }

    /**
=======
>>>>>>> a08212a752b7cca2ae4275252684a64966ae1fff
     *  상위 액티비티의 FragmentManager를 이용해
     *  fragment를 replace하기 위한 메서드
     */
    private fun moveToHomeFrag() {
        val parentActivity = activity as ProductActivity
        parentActivity.changeFragment("home", null)
    }

    /**
<<<<<<< HEAD
     * 현재 액티비티의 FragmentManager를 이용해
     * frament를 replace하기 위한 메서드
     *
     * 상품 등록이 성공했다면, HomeFragment의 RecyclerView를 초기화(HomeFragment 객체 재생성)
     */
    private fun moveToHomeFragToCheckRegisterSuccess() {
        val parentActivity = activity as ProductActivity
        parentActivity.changeFragment("home-register-success", null)
    }

    /**
=======
>>>>>>> a08212a752b7cca2ae4275252684a64966ae1fff
     * Fragment를 Activity에 attach할 떄 호출
     * Fragment 최초 생성시 호출 (생명주기 최초)
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // backButton 클릭 이벤트
        backButtonCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                moveToHomeFrag()
            }
        }
        mContext = context
        // Activity에 custom backButtonCallback 추가
        requireActivity().onBackPressedDispatcher.addCallback(this, backButtonCallback)
    }

    /**
     * Fragment와 Activity의 연결고리가 끊길 떄 호출
     * Fragment 소멸시 호출 (생명주기 최후)
     */
    override fun onDetach() {
        super.onDetach()
<<<<<<< HEAD
        backButtonCallback.remove() // remove Custom Backbutton callback
    }

    companion object {
        @Volatile
        private var instance: RegisterFragment? = null

        @JvmStatic
        fun getInstance(): RegisterFragment =
=======
        backButtonCallback.remove()
    }

    companion object {
        @Volatile private var instance: RegisterFragment? = null

        @JvmStatic fun getInstance(): RegisterFragment =
>>>>>>> a08212a752b7cca2ae4275252684a64966ae1fff
            instance ?: synchronized(this) {
                instance ?: RegisterFragment().also {
                    instance = it
                }
            }
    }
}