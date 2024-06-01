package com.example.binary_tree_visualization

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.binary_tree_visualization.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val maxHeap = MaxHeap()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonPush.setOnClickListener {
            val number = binding.editTextNumber.text.toString().toIntOrNull()
            if (number != null) {
                maxHeap.push(number)
                binding.editTextNumber.text.clear()
            }
        }

        binding.buttonPop.setOnClickListener {
            val poppedValue = maxHeap.pop()
            if (poppedValue == null) {
                Toast.makeText(this, "Pop할 수 없습니다!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonView.setOnClickListener {
            Log.d("MainActivity", "Heap: ${maxHeap.getHeap()}")
        }

        binding.buttonVisualize.setOnClickListener {
            binding.binaryTreeView.setHeap(maxHeap.getHeap())
        }
    }
}