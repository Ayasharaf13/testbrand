package com.example.testapi.home.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testapi.R
import com.example.testapi.databinding.BrandCardBinding
import com.example.testapi.models.brands.Image
import com.example.testapi.models.brands.SmartCollection


/*
class BrandsAdapter {
}

 */




class BrandsAdapter(private var list: List<SmartCollection>) :
    RecyclerView.Adapter<BrandsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = BrandCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setBrandsList(list: List<SmartCollection>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentBrand = list[position]
        val image = currentBrand.image as? Image
        if (image != null) {
            with(holder.binding) {
                // Update the image loading logic if necessary
                Glide.with(root).load(image.src).into(brandImg)
                brandName.text = currentBrand.title
            }
        }
    }

    inner class ViewHolder(val binding: BrandCardBinding) : RecyclerView.ViewHolder(binding.root)
}

