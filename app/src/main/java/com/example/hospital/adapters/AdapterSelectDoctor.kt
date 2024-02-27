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
    var rowIndex = -1

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
        holder.apply {

        }
        holder.bind(data!!,position)
    }

    inner class Holder(private val binding: ItemDoctorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: DataAllUsers,position: Int) {
            binding.empName.text = user.first_name
            binding.spcialist.text = "Specialist - " + user.type

            if(rowIndex == position)
                binding.radioBtn.setImageResource(R.drawable.ic_selected)
            else
                binding.radioBtn.setImageResource(R.drawable.radio_btn)
        }

        init {
            itemView.setOnClickListener{
                rowIndex = layoutPosition
                listener?.onItemClick(list?.get(layoutPosition)?.id!!,list?.get(layoutPosition)?.first_name!!)
                notifyDataSetChanged()
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(id : Int,name : String)
    }

}