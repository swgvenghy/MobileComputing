package com.example.database_practice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.database_practice.databinding.ListItemBinding

class MyAdapter(private var dataset: MutableList<MyElement>): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount() = dataset.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
    fun setList(newList: MutableList<MyElement>)
    {
        this.dataset = newList
    }
    fun getElement(pos:Int): MyElement {
        return dataset[pos]
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val binding = (holder as MyViewHolder).binding
        binding.rank.text = dataset[position].rank.toString()
        binding.albumCover.setImageDrawable(
            ContextCompat.getDrawable(binding.root.context, R.drawable.ic_launcher_background)
        )
        binding.title.text = dataset[position].title
        binding.artist.text = dataset[position].artist
        binding.album.text = dataset[position].album
        binding.numLike.text = dataset[position].num_like.toString()
    }
}