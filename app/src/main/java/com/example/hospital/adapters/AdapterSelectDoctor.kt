package com.example.hospital.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.hospital.R
import com.example.hospital.data.DataAllUsers
import com.example.hospital.databinding.ItemCallBinding
import com.example.hospital.databinding.ItemDoctorBinding
import com.example.hospital.databinding.ItemEmployeeBinding

open class AdapterSelectDoctor : Adapter<AdapterSelectDoctor.Holder>() {

    var list: List<DataAllUsers>? = null
    var listener : OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding =
            ItemDoctorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data = list?.get(position)
        holder.bind(data!!)
    }

    inner class Holder(private val binding: ItemDoctorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: DataAllUsers) {
            binding.empName.text = user.first_name
            binding.spcialist.text = "Specialist - " + user.type

        }

        init {
            binding.root.setOnClickListener{
                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION){
                    list?.get(position).let {
                        listener?.onItemClick(it!!)
                        binding.radioBtn.setImageResource(R.drawable.ic_selected)
                    }
                }
            }
        }


    }

    interface OnItemClickListener {
        fun onItemClick(user: DataAllUsers)
    }

}