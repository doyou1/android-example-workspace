package com.example.fileaccesssampling

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.appcompat.app.AppCompatActivity
import com.example.fileaccesssampling.databinding.ActivityMainBinding
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGet.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "*/*"
            startActivityForResult(intent, READ_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == READ_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                val uri: Uri? = data.data
                if (uri != null) {
                    val fileExtension = MimeTypeMap.getSingleton()
                        .getExtensionFromMimeType(contentResolver.getType(uri))
                    Log.e("TAG", "fileExtension: $fileExtension")
                    if (fileExtension != null) {
                        val fileName = "temp_file.${fileExtension}"
                        val file = File(cacheDir, fileName)
                        file.createNewFile()
                        val fout = FileOutputStream(file)
                        val fin = contentResolver.openInputStream(uri)
                        if (fin != null) {
                            copy(fin, fout)
                            val workbook = WorkbookFactory.create(file)
                            // if error, replace
//                            val workbook = XSSFWorkbook(file)
                            // EmptyFileException
//                            val workbook = WorkbookFactory.create(fin)

                            val sheet = workbook.getSheet("import")
                            val keyTitle = sheet.getRow(0).getCell(0).toString()
                            val valueTitle = sheet.getRow(0).getCell(1).toString()

                            val strBuilder = StringBuilder()

                            if (keyTitle == "key" && valueTitle == "value") {
                                for (i in sheet.firstRowNum..sheet.lastRowNum) {
                                    val row = sheet.getRow(i)
                                    val key =
                                        if (row.getCell(0) == null || row.getCell(0).toString()
                                                .trim()
                                                .isEmpty()
                                        ) null else row.getCell(0)
                                    val value =
                                        if (row.getCell(1) == null || row.getCell(1).toString()
                                                .trim()
                                                .isEmpty()
                                        ) null else row.getCell(1)


                                    if (key != null && value != null) {
                                        strBuilder.append("key: $key, value: $value")
                                    }
                                }
                                binding.tvResult.text = strBuilder.toString()
                            }

                            fout.flush()
                        }
                    }
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun copy(source: InputStream, target: OutputStream) {
        val buf = ByteArray(8192)
        var length: Int
        while (source.read(buf).also { length = it } > 0) {
            target.write(buf, 0, length)
        }
    }


    companion object {
        const val READ_REQUEST_CODE = 1
    }
}