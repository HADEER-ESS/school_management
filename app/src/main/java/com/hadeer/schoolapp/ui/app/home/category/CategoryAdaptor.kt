package com.hadeer.schoolapp.ui.app.home.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hadeer.schoolapp.databinding.CategoryCardItemBinding

class CategoryAdaptor(val data : List<Category>) : RecyclerView.Adapter<CategoryAdaptor.CategoryViewHolder>() {

    class CategoryViewHolder(val binding: CategoryCardItemBinding) : RecyclerView.ViewHolder(binding.root){
        val category_image = binding.categoryImg
        val category_name = binding.categoryTv
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = CategoryCardItemBinding.inflate(inflater, parent, false)
        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val data = data[position]
        holder.category_image.setImageResource(data.icon)
        holder.category_name.setText(data.name)
    }
}