package com.example.hospital.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Adapter
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.hospital.R
import com.example.hospital.data.Const
import com.example.hospital.data.DataAllCalls
import com.example.hospital.data.DataAllUsers
import com.example.hospital.databinding.ItemCallBinding
import com.example.hospital.databinding.ItemEmployeeBinding
import java.time.format.DateTimeFormatter

class AdapterCalls() :
    RecyclerView.Adapter<AdapterCalls.Holder>() {
    var list: List<DataAllCalls>? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding =
            ItemCallBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data = list?.get(position)
        holder.bind(data!!)
    }

    inner class Holder(private val binding: ItemCallBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(call: DataAllCalls) {
            binding.name.text = call.patient_name
            val date = call.created_at.format(DateTimeFormatter.ofPattern("dd . MM . yyyy"))
            binding.date.text = date
            if (call.status == Const.PENDING)
                binding.status.setImageResource(R.drawable.ic_pending)
            else
                binding.status.setImageResource(R.drawable.ic_done)
        }
    }


}