package com.example.hotelreservationnitin.hotels

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

import com.example.hotelreservationnitin.databinding.ItemHotelBinding
import com.example.hotelreservationnitin.datamodels.Hotel

class HotelRecyclerViewAdapter(
    private val values: List<Hotel>,
    private val onHotelListItemClickListener: OnHotelListItemClickListener
) : RecyclerView.Adapter<HotelRecyclerViewAdapter.ViewHolder>() {

    private var selectedRowIndex = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ItemHotelBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hotel = values[position]
        holder.name.text = hotel.hotelname
        var price = "NA"
        if (hotel.rooms.isNotEmpty()) {
            price = hotel.rooms[0].price.toString()
        }
        holder.price.text = price
        holder.rating.text = hotel.starrating.toString()
        holder.city.text = hotel.city
        holder.itemView.setOnClickListener{
            if (selectedRowIndex != -1)
                notifyItemChanged(selectedRowIndex)
            selectedRowIndex = holder.bindingAdapterPosition
            onHotelListItemClickListener.onItemClick(hotel)
            notifyItemChanged(selectedRowIndex)
        }
        if (holder.bindingAdapterPosition == selectedRowIndex) {
            holder.root.setBackgroundColor(Color.parseColor("#567845"))
        } else {
            holder.root.setBackgroundColor(Color.parseColor("#ffffff"))
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: ItemHotelBinding) : RecyclerView.ViewHolder(binding.root) {
        val root: LinearLayout = binding.root
        val name: TextView = binding.name
        val price: TextView = binding.price
        val rating: TextView = binding.rating
        val city: TextView = binding.city
    }

}