package com.example.database_practice

import android.content.ContentValues
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.database_practice.databinding.ActivityMainBinding
import com.example.database_practice.databinding.ListItemBinding

class MainActivity : AppCompatActivity() {
    private val dbHelper = MyDatabase.MyDbHelper(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding:ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var db = dbHelper.writableDatabase
        val entryArr = mutableListOf(
            MyElement(1, "Spicy","aespa","MY WORLD", 68136),
            MyElement(2, "I AM","IVE (아이브)","I've IVE", 153643),
            MyElement(3, "UNFORGIVEN","LE SSERAFIM (르세라핌)","UNFORGIVEN", 85725),
        )
        for (entry in entryArr) {
            val myentry = MyDatabase.MyDBContract.MyEntry
            val values = ContentValues().apply {
                put(myentry.rank, entry.rank)
                put(myentry.title, entry.title)
                put(myentry.artist, entry.artist)
                put(myentry.album,entry.album)
                put(myentry.num_like, entry.num_like)
            }
            val newRowId = db?.insert(myentry.TABLE_NAME, null, values)
        }
        db.close()

        val getList = dbHelper.selectAll()
        val adapter = MyAdapter(getList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        binding.submit.setOnClickListener {
            db = dbHelper.writableDatabase
            val binding2: ListItemBinding = ListItemBinding.inflate(layoutInflater)
            val elem = MyElement(
                binding2.rank.text.toString().toInt(),
                binding2.title.text.toString(),
                binding2.artist.text.toString(),
                binding2.album.text.toString(),
                binding2.numLike.text.toString().toInt()
            )
            val myentry = MyDatabase.MyDBContract.MyEntry
            val values = ContentValues().apply {
                put(myentry.rank, elem.rank)
                put(myentry.title, elem.title)
                put(myentry.artist, elem.artist)
                put(myentry.album,elem.album)
                put(myentry.num_like, elem.num_like)
            }
            val newRowId=db?.insert(myentry.TABLE_NAME, null, values)
            val newList = dbHelper.selectAll()
            adapter.setList(newList)
            adapter.notifyDataSetChanged()
            db.close()
        }
    }
}