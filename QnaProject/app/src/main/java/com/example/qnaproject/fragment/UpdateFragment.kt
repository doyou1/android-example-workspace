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
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
=======
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import android.widget.EditText
>>>>>>> a08212a752b7cca2ae4275252684a64966ae1fff
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
<<<<<<< HEAD
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.qnaproject.R
import com.example.qnaproject.activity.ProductActivity
import com.example.qnaproject.databinding.FragmentUpdateBinding
=======
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.example.qnaproject.ProductAdapter
import com.example.qnaproject.R
import com.example.qnaproject.activity.ProductActivity
import com.example.qnaproject.databinding.FragmentHomeBinding
import com.example.qnaproject.databinding.FragmentUpdateBinding
import com.example.qnaproject.domain.Product
>>>>>>> a08212a752b7cca2ae4275252684a64966ae1fff
import com.example.qnaproject.responseModel.ProductResponseModel
import com.example.qnaproject.service.ProductService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
<<<<<<< HEAD
import android.provider.MediaStore
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.core.view.allViews
import com.bumptech.glide.load.engine.DiskCacheStrategy
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import okhttp3.MultipartBody

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

/**
 *  ProductActivity 세번째 화면
 *  상품 수정 화면
=======

/**
 *  ProductActivity 세번째 화면
>>>>>>> a08212a752b7cca2ae4275252684a64966ae1fff
 */
class UpdateFragment : Fragment() {

    private val baseUrl = "https://api.jamjami.co.kr/"
    private lateinit var binding: FragmentUpdateBinding
    private lateinit var mContext: Context
    private lateinit var backButtonCallback: OnBackPressedCallback  // 추가할 backbutton callback 함수
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>

<<<<<<< HEAD
    private var transactionCode = "N"               // 이미지 처리를 위한 트랜잭션 코드 (파일명명 구성 규칙)
    private var hasInitImage = false                // 최초 이미지 유무 플래그
    private var initItmImg1: String? = null         // 최초 이미지 있을시 url
    private var uploadUri: Uri? = null

    private var ITM_ID: Int = -1    // Product Item ID

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_update, container, false)

=======
    private var ITM_ID: Int = -1    // Product Item ID

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_update, container, false)
>>>>>>> a08212a752b7cca2ae4275252684a64966ae1fff
        if (arguments?.getInt("ITM_ID") == null) {  // ITM_ID가 없다면, 잘못된 접근
            val parentActivity = activity as ProductActivity
            parentActivity.changeFragment("home", null) // HomeFragment로 이동
        } else {
<<<<<<< HEAD
            ITM_ID = arguments?.getInt("ITM_ID") ?: -1  // 다른 형태 고민 해볼 것
=======
            ITM_ID = arguments?.getInt("ITM_ID")!!  // 다른 형태 고민 해볼 것
>>>>>>> a08212a752b7cca2ae4275252684a64966ae1fff
            Log.e(tag, "itm_id: ${ITM_ID}")

            galleryLauncher = getGalleryActivityResultLauncher()
            setProductData()
            setClickEvent()
        }
<<<<<<< HEAD

=======
>>>>>>> a08212a752b7cca2ae4275252684a64966ae1fff
        return binding.root
    }

    /**
     * 레이아웃 아이템 클릭 이벤트
     */
    private fun setClickEvent() {
        binding.toolbarProductUpdate.tbBack.setOnClickListener {  // 툴바 '<'(BackButton) 클릭 이벤트
            moveToHomeFrag()
        }
        binding.ibAccessGallery.setOnClickListener {    // ImageView 클릭시 갤러리 앱으로 이동
<<<<<<< HEAD
            openGallery()
        }
        binding.ibRemoveImage.setOnClickListener {  // 이미지 삭제 버튼 클릭시
            removeImage()
        }
        binding.btnItmUpdate.setOnClickListener { // 수정 버튼 클릭시
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
=======
            openGalleryActivityResultLauncher()
>>>>>>> a08212a752b7cca2ae4275252684a64966ae1fff
        }
    }

    /**
<<<<<<< HEAD
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
     * EditText가 아닌 부분을 터치했을 경우
     * 키보드를 숨기기 위한 TouchEvent 설정
     */
    private fun setTouchEvent(view: View) {
        if (view !is EditText) {    // 현재 focus View가 EditText가 아니라면, if문 통과하는 구조 공부 필요
            view.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                    hideSoftKeyboard(mContext); // Keyboard를 숨김
                    return false;
                }
            })
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
=======
>>>>>>> a08212a752b7cca2ae4275252684a64966ae1fff
     * 상품 상세정보 인터페이스 URL 접근
     * 정보 데이터를 UI에 바인딩
     */
    private fun setProductData() {
        // Retrofit 객체 생성
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create()) // JSON 변환 객체 등록
            .build()

        // Retrofit 객체로 service 인터페이스 구현
        val productService = retrofit.create(ProductService::class.java)

        // List 데이터에 접근하는 call 객체 생성
        val call: Call<ProductResponseModel> = productService.getProduct(ITM_ID)
        // 선언한 call 객체에 queue 추가
        call.enqueue(object : Callback<ProductResponseModel> {
            override fun onResponse(
                call: Call<ProductResponseModel>,
                response: Response<ProductResponseModel>
            ) { // Response Success
                // ResponseBody의 형태에 따라 Custom ResponseModel로 변환
                val resBody = response.body() as ProductResponseModel
<<<<<<< HEAD
                val product = resBody.data[0]
                binding.product = product

                Log.e(tag, product.ITM_IMG1.toString())
                if (product.ITM_IMG1?.isNotEmpty() == true) {   // 기존 데이터에 이미지가 있을 때
                    hasInitImage = true // 최초 이미지 유무 플래그
                    initItmImg1 = product.ITM_IMG1
                    showRemoveImageButton()
                } else {                                        // 기존 데이터에 이미지가 없을 때
                    hasInitImage = false
                    hideRemoveImageButton()
                }

                /**
                 *
                 * 전달받은 ITM_VIEW_YN 값에 따라
                 * 라디오 버튼 checked 설정
                 *
                 * xml상 데이터 바인딩 문법 이해 부족
                 */
                if (product.ITM_VIEW_YN == "Y") binding.rgViewYn.check(binding.rbViewY.id)
                else if (product.ITM_VIEW_YN == "N") binding.rgViewYn.check(binding.rbViewN.id)

                Glide.with(mContext)
                    .load(product.ITM_IMG1)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .listener(loadImageListener(null))
                    .placeholder(R.drawable.ic_baseline_add_36)     // 기본이미지
                    .error(R.drawable.ic_baseline_add_36)           // 에러시 대체 이미지
                    .into(binding.ibAccessGallery)

                Log.e(tag, "product: $product, hasInitImage: $hasInitImage, initItmImg1: $initItmImg1")

            }

            override fun onFailure(
                call: Call<ProductResponseModel>,
                t: Throwable
            ) {   // Response Fail
=======
//                Log.e(tag, resBody.toString())
                val product = resBody.data[0]
                binding.product = product

                Glide.with(mContext)
                    .load(product.ITM_IMG1)
                    .placeholder(R.drawable.ic_baseline_add_36)     // 기본이미지
                    .error(R.drawable.ic_baseline_add_36)           // 에러시 대체 이미지
                    .into(binding.ibAccessGallery)
            }

            override fun onFailure(call: Call<ProductResponseModel>, t: Throwable) {   // Response Fail
>>>>>>> a08212a752b7cca2ae4275252684a64966ae1fff
                Log.d(tag, "실패 : $t")
            }
        })
    }

    /**
<<<<<<< HEAD
     * 상품정보 수정 인터페이스 URL 접근
     * 성공시 수정 성공 메시지와 함께 HomeFragment 이동
     */
    private fun updateProduct() {
        // 유효성 검사 완료한 텍스트 데이터
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
        // Create(새로운 입력) Update(이미지 변경)일 경우
        if (transactionCode == "C" || transactionCode == "U") {
            // 현재 미리보기되는 이미지의 절대경로
            val imageAbsolutePath = getRealPathFromURIForGallery()
            Log.e(tag, "imageAbsolutePath: ${imageAbsolutePath}")

            imageAbsolutePath?.let {
                try {
                    bytes = File(imageAbsolutePath).readBytes()
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }
            }
        } else if (transactionCode == "D") {
            bytes = byteArrayOf(0x01)   // 1byte array
        }

        Log.e(tag, "transactionCode : ${transactionCode}")
        Log.e(tag, "bytes : ${bytes}")
        // Text to RequestBody
        val itmIdBody = RequestBody.create(MediaType.parse("text/plain"), ITM_ID.toString())
        val itmNmBody = RequestBody.create(MediaType.parse("text/plain"), itmNm)
        val itmPriceBody = RequestBody.create(MediaType.parse("text/plain"), itmPrice)
        val itmDisBody = RequestBody.create(MediaType.parse("text/plain"), itmDis)
        val itmIntroBody = RequestBody.create(MediaType.parse("text/plain"), itmIntro)
        val itmViewYNBody = RequestBody.create(MediaType.parse("text/plain"), itmViewYN)

        // 업로드할 이미지의 ByteArray MultipartBody.Part 객체로 변환
        val requestFile: RequestBody? = bytes?.let {
            val result = RequestBody.create(MediaType.parse("image/png"), it)
            result
        }
        val itmImg1Body: MultipartBody.Part? = requestFile?.let {
            val fileName = getImageFileName()
            val result = MultipartBody.Part.createFormData("ITM_IMG1", fileName, it)
            result
        }

        Log.e("imgImg1Body", "result : ${itmImg1Body}")


        // RequestBodys to RequestMap
        val requestMap = HashMap<String, RequestBody>().also {
            it.put("ITM_ID", itmIdBody)
            it.put("ITM_NM", itmNmBody)
            it.put("ITM_PRICE", itmPriceBody)
            it.put("ITM_DIS", itmDisBody)
            it.put("ITM_INTRO", itmIntroBody)
            it.put("ITM_VIEW_YN", itmViewYNBody)
        }

        // Retrofit 객체 생성
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create()) // JSON 변환 객체 등록
            .build()

        // Retrofit 객체로 service 인터페이스 구현
        val productService = retrofit.create(ProductService::class.java)

        // List 데이터에 접근하는 call 객체 생성
        val call: Call<ProductResponseModel> = productService.updateProduct(requestMap, itmImg1Body)
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
    private fun getImageFileName(): String {
        if (hasInitImage) {  // 기존 이미지가 있다면? [U.D]-01-기존이미지이름.png
            // ?-??-YYMMDDHHMMSS.???
            var oldFileName = initItmImg1?.removePrefix("${baseUrl}images/good/")
            // YYMMDDHHMMSS
            var date = oldFileName?.substring(5, 17)

            Log.e(tag, "hasInitImage  $transactionCode-01-$date.png")
            return "$transactionCode-01-$date.png"
        } else {    // 없다면 현재시간
            val currentDate = getCurrentDate()

            Log.e(tag, "else  $transactionCode-01-$currentDate.png")
            return "$transactionCode-01-$currentDate.png"
        }

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
     * Uri -> filePath -> File(filePath)
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
     * 인터페이스 Url 요청 후 응답코드에 따른 처리 메서드
     */
    private fun resultProcess(code: Int) {
        when (code) {
            400 -> Toast.makeText(
                mContext,
                "서버 오류로 상품 수정이 실패했습니다.",
                Toast.LENGTH_SHORT
            ).show()
            200 -> {
                Toast.makeText(
                    mContext,
                    "상품 수정 성공!",
                    Toast.LENGTH_SHORT
                ).show()

                // move to HomeFragment & RecyclerView Refresh
                moveToHomeFragToCheckUpdateSuccess()
            }
            else -> Toast.makeText(
                mContext,
                "서버 오류로 상품 수정이 실패했습니다.",
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
                updateProduct()
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

    }

    /**
     * Gallery에 접근하기 위한 ActivityResultLauncher 선언 및 리턴 메서드
     * Gallery에 접근한 이후 작업은 if (result.resultCode == Activity.RESULT_OK) 내부에 선언
     */
    private fun getGalleryActivityResultLauncher(): ActivityResultLauncher<Intent> {
        return registerForActivityResult(
            ActivityResultContracts
                .StartActivityForResult()
        ) { result ->
=======
     * Gallery에 접근하기 위한 ActivityResultLauncher 선언 및 리턴 메서드
     * Gallery에 접근한 이후 작업은 if (result.resultCode == Activity.RESULT_OK) 내부에 선언
     */
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
                        .listener(loadImageListener("Y"))
                        .placeholder(R.drawable.ic_baseline_add_36)     // 기본 이미지
                        .error(R.drawable.ic_baseline_add_36)           // 기본 이미지
=======
                    val uri = data.data
                    Glide.with(mContext)
                        .load(uri)
                        .placeholder(R.drawable.ic_baseline_add_36)
                        .error(R.drawable.ic_baseline_add_36)        //
>>>>>>> a08212a752b7cca2ae4275252684a64966ae1fff
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
<<<<<<< HEAD

        // 권한 다이얼로그와 갤러리 앱이 함께 쇼잉되는 문제가 있어 
        // 등록 버튼에서 진행
//        checkPermission()   
=======
>>>>>>> a08212a752b7cca2ae4275252684a64966ae1fff
        galleryLauncher.launch(intent)
    }

    /**
<<<<<<< HEAD
     * Glide 이미지 로드 성공, 실패 여부 확인 리스너
     * 트랜잭션 코드 및 이미지 삭제 버튼 쇼잉 여부 설정
     */
    private fun loadImageListener(isUpdating: String?): RequestListener<Drawable> {
        return object : RequestListener<Drawable> {

            // Image Load 실패 시 CallBack
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                /**
                 * 1. 갤러리 접근 데이터 Load fail
                 *  1. 최초 이미지 있을 때 - N
                 *  2. 최초 이미지 없을 때 - N
                 */
                transactionCode = "N"
                uploadUri = null
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
                 * 2. 갤러리 접근 데이터 로드 success
                 *  1. 최초 이미지 있을 때 - U
                 *  2. 최초 이미지 없을 때 - C
                 */
                isUpdating?.let {
                    Log.e("isUpdating start", "hasInitImage: $hasInitImage, transactionCode: $transactionCode")
                    if (hasInitImage) transactionCode = "U"
                    else transactionCode = "C"

                    Log.e("isUpdating end", "hasInitImage: $hasInitImage, transactionCode: $transactionCode")
                }
//                if(isUpdating?.equals("Y") == true) {
//                    if (hasInitImage) transactionCode = "U"
//                    else transactionCode = "C"
//                }
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
         * 3. 삭제 버튼 클릭
         *  1. 최초 이미지 있을 때 - D
         *  2. 최초 이미지 없을 때 - N
         */
        if (hasInitImage) transactionCode = "D"
        else transactionCode = "N"
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
     *  현재 액티비티의 FragmentManager를 이용해
=======
     *  상위 액티비티의 FragmentManager를 이용해
>>>>>>> a08212a752b7cca2ae4275252684a64966ae1fff
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
     * 상품 수정이 성공했다면, HomeFragment의 RecyclerView를 초기화(HomeFragment 객체 재생성)
     */
    private fun moveToHomeFragToCheckUpdateSuccess() {
        val parentActivity = activity as ProductActivity
        parentActivity.changeFragment("home-update-success", null)
    }

    /**
=======
>>>>>>> a08212a752b7cca2ae4275252684a64966ae1fff
     * Fragment를 Activity에 attach할 떄 호출
     * Fragment 최초 생성시 호출 (생명주기 최초)
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context

        // backButton 클릭 이벤트
        backButtonCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                moveToHomeFrag()
            }
        }
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
        private var instance: UpdateFragment? = null

        @JvmStatic
        fun getInstance(): UpdateFragment =
=======
        backButtonCallback.remove()
    }

    companion object {
        @Volatile private var instance: UpdateFragment? = null

        @JvmStatic fun getInstance(): UpdateFragment =
>>>>>>> a08212a752b7cca2ae4275252684a64966ae1fff
            instance ?: synchronized(this) {
                instance ?: UpdateFragment().also {
                    instance = it
                }
            }
    }
<<<<<<< HEAD

=======
>>>>>>> a08212a752b7cca2ae4275252684a64966ae1fff
}