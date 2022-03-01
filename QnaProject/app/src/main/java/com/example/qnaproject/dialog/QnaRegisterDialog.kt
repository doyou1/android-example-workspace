package com.example.qnaproject.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.qnaproject.databinding.DialogQnaRegisterBinding
import com.example.qnaproject.fragment.FragmentQna

class QnaRegisterDialog(context: Context) : Dialog(context) {

    private lateinit var binding: DialogQnaRegisterBinding
    private val mContext = context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogQnaRegisterBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)
        setDialogSize()
        setClickEvent()
    }

    private fun setDialogSize() {
        val params = window?.attributes
        params?.width = WindowManager.LayoutParams.MATCH_PARENT
        params?.height = WindowManager.LayoutParams.WRAP_CONTENT

        window?.attributes = params
    }

    private fun setClickEvent() {
        binding.btnRegister.setOnClickListener {
            Toast.makeText(context, "등록버튼 클릭", Toast.LENGTH_SHORT).show()
            // 입력값 초기화
            binding.etQnaTitle.setText("")
            binding.etQnaContent.setText("")
            dismiss()

        }
        binding.btnCancle.setOnClickListener {
            Toast.makeText(context, "취소버튼 클릭", Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }


    companion object {
        @Volatile private var instance: QnaRegisterDialog? = null

        @JvmStatic fun getInstance(context: Context): QnaRegisterDialog =
            instance ?: synchronized(this) {
                instance ?: QnaRegisterDialog(context).also {
                    instance = it
                }
            }
    }


}