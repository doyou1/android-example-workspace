package com.example.externalstorageinandroid13sampling

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.documentfile.provider.DocumentFile
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.externalstorageinandroid13sampling.databinding.ActivityMainBinding
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.*
import java.text.SimpleDateFormat
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private val TAG = this::class.java.simpleName
    private lateinit var binding: ActivityMainBinding
    private val openDocumentTreeLauncher = getDocumentTreeResultLauncher()
    private val openDocumentLauncher = getDocumentResultLauncher()
    private val MIME_TYPE_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setClickEvent()
    }

    private fun setClickEvent() {
        binding.btnOpenDocumentTree.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
            openDocumentTreeLauncher.launch(intent)
        }

        binding.btnOpenDocument.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "*/*"
            openDocumentLauncher.launch(intent)
        }

    }

    private fun getDocumentTreeResultLauncher(): ActivityResultLauncher<Intent> {
        return registerForActivityResult(
            ActivityResultContracts
                .StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                result.data?.let {
                    it.data?.let { uri ->
                        Log.e(TAG, "data: $uri")
                        val takeFlags = Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                        contentResolver.takePersistableUriPermission(uri, takeFlags)
                        val fileName = "test-${getCurrentTime()}.xlsx"
                        writeExcel(uri, fileName, getWorkbook())
                    }
                }
            }
        }
    }


    private fun getDocumentResultLauncher(): ActivityResultLauncher<Intent> {
        return registerForActivityResult(
            ActivityResultContracts
                .StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                result.data?.let {
                    it.data?.let { uri ->
                        Log.e(TAG, "data: $uri")
                        if (isXlsxType(uri)) {
                            val takeFlags = Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                            contentResolver.takePersistableUriPermission(uri, takeFlags)
                            readExcel(uri)?.let { workbook ->
                                convertExcelToItems(workbook)?.let { list ->
                                    setRecyclerView(list)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun writeExcel(baseDocumentTreeUri: Uri, fileName: String, workbook: XSSFWorkbook) {
        DocumentFile.fromTreeUri(this, baseDocumentTreeUri)?.let { directory ->
            directory.createFile(MIME_TYPE_XLSX, fileName)?.let { file ->
                val pfd = contentResolver.openFileDescriptor(file.uri, "w")
                if (pfd != null) {
                    val fos = FileOutputStream(pfd.fileDescriptor)
                    try {
                        workbook.write(fos)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    } finally {
                        pfd.close()
                        fos.flush()
                        fos.close()
                    }
                }
            }
        }
    }

    private fun readExcel(fileUri: Uri): Workbook? {
        val fin = contentResolver.openInputStream(fileUri)
        if (fin != null) {
            val fileName = "temp.xlsx"
            val file = File(cacheDir, fileName)
            file.createNewFile()
            val fout = FileOutputStream(file)
            return try {
                copy(fin, fout)
                WorkbookFactory.create(file)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            } finally {
                fin.close()
                fout.flush()
                fout.close()
            }
        } else return null
    }

    @Throws(IOException::class)
    private fun copy(source: InputStream, target: OutputStream) {
        val buf = ByteArray(8192)
        var length: Int
        while (source.read(buf).also { length = it } > 0) {
            target.write(buf, 0, length)
        }
    }


    private fun getWorkbook(): XSSFWorkbook {
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("result")
        var rowIdx = 0
        val row = sheet.createRow(rowIdx++)
        val keyCellIdx = 0
        val valueCellIdx = 1
        val keyCell = row.createCell(keyCellIdx)
        val valueCell = row.createCell(valueCellIdx)
        keyCell.setCellValue("key")
        valueCell.setCellValue("value")
        for (i in 0..100) {
            val itemRow = sheet.createRow(rowIdx++)
            val itemKeyCell = itemRow.createCell(keyCellIdx)
            val itemValueCell = itemRow.createCell(valueCellIdx)
            itemKeyCell.setCellValue("$i")
            itemValueCell.setCellValue("$i ${getCurrentTime()}")
        }
        return workbook
    }

    private fun getCurrentTime(): String {
        val c = Calendar.getInstance()
        val sdf = SimpleDateFormat("YYYYMMDDHHmmssSSS")
        return sdf.format(c.time)
    }

    private fun isXlsxType(uri: Uri): Boolean {
        val str = uri.toString()
        return str.substring(str.lastIndexOf(".") + 1) == "xlsx"
    }

    private fun convertExcelToItems(workbook: Workbook): List<Item>? {
        return try {
            val result = arrayListOf<Item>()
            val sheet = workbook.getSheet("result")
            val titleRow = sheet.getRow(0)
            val titleKeyCellValue = titleRow.getCell(0).toString()
            val titleValueCellValue = titleRow.getCell(1).toString()
            if (titleKeyCellValue == "key" && titleValueCellValue == "value") {
                val keyCellIdx = 0
                val valueCellIdx = 1
                for (i in 1..sheet.lastRowNum) {
                    val row = sheet.getRow(i)
                    val key = row.getCell(keyCellIdx).toString()
                    val value = row.getCell(valueCellIdx).toString()
                    if (key.isNotEmpty() && value.isNotEmpty()) {
                        result.add(Item(key, value))
                    }
                }
            }
            result
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun setRecyclerView(list: List<Item>) {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = RvAdapter(list)
    }
}