package com.example.hotelreservationnitin.guestdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.hotelreservationnitin.databinding.GuestDetailItemBinding
import com.example.hotelreservationnitin.datamodels.GuestDetail

class GuestDetailRecyclerViewAdapter(
    private val values: List<GuestDetail>
) : RecyclerView.Adapter<GuestDetailRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            GuestDetailItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.nameView.setText(item.name)
        holder.ageView.setText(item.age.toString())

        holder.nameView.doAfterTextChanged { item.name = it.toString() }
        holder.ageView.doAfterTextChanged {
            if (it.toString().isNotEmpty())
                item.age = Integer.parseInt(it.toString())
        }

        holder.genderRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radio = group.findViewById<RadioButton>(checkedId)
            item.gender = radio.text.toString()
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: GuestDetailItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val nameView: EditText = binding.name
        val ageView: EditText = binding.age
        val genderRadioGroup: RadioGroup = binding.genderRadioGroup
    }

}