package com.example.calculator

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
import com.example.calculator.databinding.ActivityMainBinding
import java.lang.NumberFormatException
import java.math.BigDecimal
import java.math.MathContext

class MainActivity : AppCompatActivity(), View.OnClickListener{
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.B0.setOnClickListener(this)
        binding.B1.setOnClickListener(this)
        binding.B2.setOnClickListener(this)
        binding.B3.setOnClickListener(this)
        binding.B4.setOnClickListener(this)
        binding.B5.setOnClickListener(this)
        binding.B6.setOnClickListener(this)
        binding.B7.setOnClickListener(this)
        binding.B8.setOnClickListener(this)
        binding.B9.setOnClickListener(this)
        binding.dot.setOnClickListener(this)

       binding.apply {
           add.setOnClickListener { calculate('+') }
           sub.setOnClickListener { calculate('-') }
           mul.setOnClickListener { calculate('*') }
           div.setOnClickListener { calculate('/') }
           clear.setOnClickListener { clearInput() }
       }
    }

    private fun calculate(operator: Char) {
        val num1String = binding.num1.text.toString()
        val num2String = binding.num2.text.toString()

        if (num1String.isEmpty() || num2String.isEmpty()) {
            Toast.makeText(this, "숫자를 전부 입력하고 연산을 진행해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        try{
            val num1 = BigDecimal(num1String)
            val num2 = BigDecimal(num2String)

            val result = when (operator) {
                '+' -> num1 + num2
                '-' -> num1 - num2
                '*' -> num1 * num2
                '/' -> {
                    if (num2.compareTo(BigDecimal.ZERO) == 0) {
                        Toast.makeText(this, "0으로 나눌 수 없습니다.", Toast.LENGTH_SHORT).show()
                        return
                    }
                    num1.divide(num2, MathContext.DECIMAL128)
                }
                else -> {
                    return
                }
            }
            binding.result.text = getString(R.string.results, result.toString())
        }
        catch (e: NumberFormatException) {
            Toast.makeText(this, "올바른 숫자 형태를 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }
    }

    private fun clearInput() {
        binding.num1.text.clear()
        binding.num2.text.clear()
        binding.result.text = getString(R.string.results)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if(currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onClick(v: View?) {
        var input:String = (v as Button).text.toString()
        if(binding.num1.isFocused) {
            input = binding.num1.text.toString() + input
            binding.num1.setText(input)
        } else if(binding.num2.isFocused) {
            input = binding.num2.text.toString() + input
            binding.num2.setText(input)
        } else {
            Toast.makeText(applicationContext, "Please click num1 or num2", Toast.LENGTH_SHORT).show()
        }
    }


}