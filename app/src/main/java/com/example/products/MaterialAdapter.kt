package com.example.products

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception
import java.util.ArrayList

class MaterialAdapter (
    private val values: ArrayList<Material>,
    private val activity: Activity
): RecyclerView.Adapter<MaterialAdapter.ViewHolder>(){
    private var itemClickListener: ((Material)-> Unit)? = null
    fun setItemClickListener(itemClickListener:(Material)-> Unit){
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        var itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.material_item,
                parent,
                false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = values.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.title.text = values[position].Title
        holder.kolvo.text = values[position].CountInPack
        holder.unit.text = values[position].Unit
        holder.stock.text = values[position].CountInStock
        holder.price.text = values[position].Cost
        itemClickListener?.invoke(values[position])

    }
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var title: TextView = itemView.findViewById(R.id.title)
        var kolvo: TextView = itemView.findViewById(R.id.kolvo)
        var unit:TextView = itemView.findViewById(R.id.Unit)
        var stock:TextView = itemView.findViewById(R.id.stock)
        var price:TextView = itemView.findViewById(R.id.price)
    }




}
