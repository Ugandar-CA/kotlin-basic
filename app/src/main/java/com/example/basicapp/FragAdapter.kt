package com.example.basicapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FragAdapter(private val list: List<pojoConvert>) :
    RecyclerView.Adapter<FragAdapter.FragViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FragViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FragViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: FragViewHolder, position: Int) {
        val model: pojoConvert = list[position]
        holder.bind(model)
    }

    class FragViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.retrofitviewholder, parent, false)) {

        private var txtName: TextView = itemView.findViewById(R.id.title)
        private var txtContent: TextView = itemView.findViewById(R.id.content)

        fun bind(model: pojoConvert) {
            txtName.text = model.title
            txtContent.text = model.completed.toString()
        }

    }
}


