package com.example.databaseapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HostelAdapter : RecyclerView.Adapter<HostelAdapter.HostelViewHolder>() {

    private var hlList: ArrayList<HostelModel> = ArrayList()
    private var onClickDeleteItem: ((HostelModel) -> Unit)? =null
    private var onClickChangeItem: ((HostelModel) -> Unit)? =null

    fun addItems(items: ArrayList<HostelModel>) {
        this.hlList = items
        notifyDataSetChanged()
    }

    fun setOnClickDeleteItem(callback: (HostelModel) -> Unit){
        this.onClickDeleteItem = callback
    }

    fun setOnClickChangeItem(callback: (HostelModel) -> Unit){
        this.onClickChangeItem = callback
    }

    class HostelViewHolder(var view: View): RecyclerView.ViewHolder(view){
        private var name = view.findViewById<TextView>(R.id.tvName)
        private var stars = view.findViewById<TextView>(R.id.tvStars)
        private var estimation = view.findViewById<TextView>(R.id.tvEstimation)
        private var number = view.findViewById<TextView>(R.id.tvNumber)

        fun bindView(hl:HostelModel){
            name.text = "Наименование: " + hl.name
            stars.text = "Количество звезд: " + (hl.stars).toString()
            estimation.text = "Оценка по отзывам: " + (hl.estimation).toString()
            number.text = "Число номеров: " + (hl.number).toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HostelViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.card_item_hostel, parent, false)
    )

    override fun onBindViewHolder(holder: HostelViewHolder, position: Int) {
        val hl = hlList[position]
        holder.bindView(hl)
        var btn_del: Button = holder.itemView.findViewById<Button>(R.id.btnDelete)
        var btn_change: Button = holder.itemView.findViewById<Button>(R.id.btnChange)

        btn_del.setOnClickListener{
            onClickDeleteItem?.invoke(hl)
        }

        btn_change.setOnClickListener{
            onClickChangeItem?.invoke(hl)
        }
    }

    override fun getItemCount(): Int {
        return hlList.size
    }
}