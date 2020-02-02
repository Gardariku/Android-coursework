package com.etu.client.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.etu.client.R
import com.etu.client.data.model.Dish


class DishAdapter(private val itemList:ArrayList<Dish>): RecyclerView.Adapter<DishAdapter.ViewHolder>() {

    var onItemClick: ((Dish) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_dish, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.textViewName.text = itemList[position].name
        holder.textViewType.text = itemList[position].type
        holder.imageView.setImageResource(R.drawable.dessert)
    }


    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val textViewName: TextView = mView.findViewById<View>(R.id.textViewDishRecyclerName) as TextView
        val textViewType: TextView = mView.findViewById<View>(R.id.textViewDishRecyclerType) as TextView
        val imageView:ImageView = mView.findViewById<View>(R.id.recyclerDishImage) as ImageView

        override fun toString(): String {
            return super.toString() + " '" + textViewName.text + "'"+textViewType.text+";"
        }

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(itemList[adapterPosition])
            }
        }
    }
}