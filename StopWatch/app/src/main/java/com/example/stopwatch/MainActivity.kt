package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import com.example.stopwatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var stopTime = 0L
    var startBool = true
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.startStop.setOnClickListener { onClickStartStop() }
        binding.reset.setOnClickListener{onClickReset()}
    }
    private fun onClickStartStop() {
        if(startBool) {
            binding.timer.base = SystemClock.elapsedRealtime() + stopTime
            binding.timer.start()
            binding.startStop.setText(getString(R.string.stop))
        }
        else {
            stopTime = binding.timer.base - SystemClock.elapsedRealtime()
            binding.timer.stop()
            binding.startStop.setText(getString(R.string.start))
        }
        startBool = !startBool
    }
    private fun onClickReset() {
        if(startBool) {
            binding.timer.stop()
        }
        binding.timer.base = SystemClock.elapsedRealtime()
        stopTime = 0
    }

}