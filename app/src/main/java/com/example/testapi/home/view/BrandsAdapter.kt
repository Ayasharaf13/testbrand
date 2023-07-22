package com.example.testapi.home.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testapi.R
import com.example.testapi.databinding.BrandCardBinding
import com.example.testapi.models.brands.SmartCollection

/*
class BrandsAdapter : ListAdapter<SmartCollection, BrandsAdapter.ViewHolder>(SmartCollectionDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = BrandCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val smartCollection = getItem(position)
        holder.binding.brandImg.setImageResource(R.drawable.ic_launcher_background)
        holder.binding.brandName.text = smartCollection.title
        Glide.with(holder.binding.root).load(smartCollection.image.src).into(holder.binding.brandImg)

        holder.binding.root.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToProductFragment(smartCollection.id)
            it.findNavController().navigate(action)
        }

    }

    inner class ViewHolder(var binding: BrandCardBinding) : RecyclerView.ViewHolder(binding.root)
}

class SmartCollectionDiffCallback : DiffUtil.ItemCallback<SmartCollection>() {
    override fun areItemsTheSame(oldItem: SmartCollection, newItem: SmartCollection): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SmartCollection, newItem: SmartCollection): Boolean {
        return oldItem == newItem
    }
}

 */


class BrandsAdapter(var list: List<SmartCollection>) :
    RecyclerView.Adapter<BrandsAdapter.ViewHolder>() {
    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = BrandCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
        // return 10
    }

    fun setBrandsList(list: List<SmartCollection>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.brandImg.setImageResource(R.drawable.ic_launcher_background)
        holder.binding.brandName.text = list[position].title
        Glide.with(holder.binding.root).load(list[position].image.src).into(holder.binding.brandImg)

        holder.binding.root.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToProductFragment(list[position].id)

            Navigation.findNavController(it).navigate(action)
        }



    }

    inner class ViewHolder(var binding: BrandCardBinding) : RecyclerView.ViewHolder(binding.root)
}

