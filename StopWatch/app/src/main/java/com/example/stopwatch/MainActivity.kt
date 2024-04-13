package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import com.example.stopwatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var stopTime = 0L
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.start.setOnClickListener{onClickStart()}
        binding.stop.setOnClickListener{onClickStop()}
        binding.reset.setOnClickListener{onClickReset()}
    }

     private fun onClickStart() {
        binding.timer.base = SystemClock.elapsedRealtime() + stopTime
        binding.timer.start()
    }

    private fun onClickStop() {
        stopTime = binding.timer.base - SystemClock.elapsedRealtime()
        binding.timer.stop()
    }
    private fun onClickReset() {
        binding.timer.base = SystemClock.elapsedRealtime()
        stopTime = 0
    }
}