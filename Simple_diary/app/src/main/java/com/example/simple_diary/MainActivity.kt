package com.example.simple_diary

import android.content.Context
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.simple_diary.databinding.ActivityMainBinding
import java.io.IOException

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val context = applicationContext
        var cal = Calendar.getInstance()
        var cYear = cal.get(Calendar.YEAR)
        var cMonth = cal.get(Calendar.MONTH)
        var cDay = cal.get(Calendar.DAY_OF_MONTH)
        var fName = "${cYear}_${cMonth + 1}_${cDay}.txt"
        val initialDiary = readDiary(fName)
        binding.datePicker.init(cYear, cMonth, cDay) {view, year, month, day ->
            fName = "${year}_${month+1}_${day}.txt"
            var str = readDiary(fName)
            binding.editText.setText(str)
        }
        binding.btnSmt.setOnClickListener {
            openFileOutput(fName, Context.MODE_PRIVATE).use {
                it.write(binding.editText.text.toString().toByteArray())
                Toast.makeText(applicationContext, "저장완료", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun readDiary(fName: String) : String?{
        var diaryStr: String?=null
        try{
            openFileInput(fName).bufferedReader().forEachLine {
                if(diaryStr === null) {diaryStr=it}
                else{diaryStr+="\n"+it}
                binding.btnSmt.text="수정 하기"
            }
        } catch (e: IOException) {
            binding.editText.hint="일기 없음"
            binding.btnSmt.text = "새로 저장"
        }
        return diaryStr
    }
}