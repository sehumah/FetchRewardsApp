package com.exercise.fetchrewardsapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DataAdapter(private val context: Context, private val dataList: List<Data>) : RecyclerView.Adapter<DataAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // get access to xml views for the Data object
        private val tvName   = itemView.findViewById<TextView>(R.id.tv_name)
        private val tvListId = itemView.findViewById<TextView>(R.id.tv_listId)
        private val tvId     = itemView.findViewById<TextView>(R.id.tv_Id)

        // bind remote data to their respective views
        fun bind(data: Data) {
            tvName.text   = data.name
            tvListId.text = data.listId.toString()
            tvId.text     = data.id.toString()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_data, parent, false)  // inflate the layout resource file to use
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataItem = dataList[position]  // grab an individual data item
        holder.bind(dataItem)  // bind data values to views
    }

}
