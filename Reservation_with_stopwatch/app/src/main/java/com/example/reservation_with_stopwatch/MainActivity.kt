package com.example.reservation_with_stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.reservation_with_stopwatch.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var selectedDate: Long = 0
    private var selectedTime: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calenderView.isVisible=false
        binding.timePicker.isVisible=false

        binding.chrono.setOnClickListener { stopWatchStart() }
        binding.res.setOnLongClickListener{ stopWatchStop() }

        binding.rdoCal.setOnClickListener {
            binding.calenderView.isVisible=true
            binding.timePicker.isVisible=false
        }
        binding.rdoTime.setOnClickListener {
            binding.calenderView.isVisible=false
            binding.timePicker.isVisible=true
        }

        binding.calenderView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            selectedDate = calendar.timeInMillis
        }

        binding.timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
            selectedTime = "${hourOfDay}시 ${minute}분"
        }
    }

    private fun displayReservationInfo() {
        val selectedDateTime = if (selectedDate != 0L && selectedTime.isNotEmpty()) {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = selectedDate
            val formattedDate = SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault()).format(calendar.time)
            "$formattedDate $selectedTime"
        } else {
            "날짜 및 시간을 선택해주세요."
        }
        binding.res.text = selectedDateTime
    }
    private fun stopWatchStart() {
        binding.chrono.base = SystemClock.elapsedRealtime()
        binding.chrono.start()
        binding.chrono.setTextColor(ContextCompat.getColor(this, R.color.red))
        binding.rdoCal.isVisible = true
        binding.rdoTime.isVisible = true
    }

    private fun stopWatchStop(): Boolean {
        if(selectedDate != 0L && selectedTime.isNotEmpty()) {
            binding.chrono.stop()
            displayReservationInfo()
            binding.chrono.setTextColor(ContextCompat.getColor(this, R.color.blue))
            binding.rdoCal.isVisible=false
            binding.rdoTime.isVisible=false
            binding.calenderView.isVisible=false
            binding.timePicker.isVisible=false
            return true
        }
        else{
            Toast.makeText(this, "날짜 및 시간을 선택해주세요", Toast.LENGTH_SHORT).show()
            return false
        }
    }

}
