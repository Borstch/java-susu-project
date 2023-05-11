package com.example

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class HistoryAdapter(
    private val historyItems: MutableList<Pair<String, String>>
) :
    RecyclerView.Adapter<HistoryAdapter.HistoryHolder>() {


    inner class HistoryHolder(view: View) : RecyclerView.ViewHolder(view) {
        val expression: TextView = view.findViewById(R.id.expression)
        val result: TextView = view.findViewById(R.id.result)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.history_items, parent, false)
        return HistoryHolder(itemView)
    }

    override fun onBindViewHolder(holder: HistoryHolder, position: Int) {
        val item = historyItems[position]
        holder.expression.text = item.first
        holder.result.text = item.second
    }

    override fun getItemCount(): Int {
        return historyItems.size
    }
}