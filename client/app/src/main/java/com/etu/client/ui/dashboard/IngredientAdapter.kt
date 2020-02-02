package com.etu.client.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.etu.client.R
import com.etu.client.data.model.Ingredient

class IngredientAdapter (private val itemList:ArrayList<Ingredient>): RecyclerView.Adapter<IngredientAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_view_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.textViewName.text = itemList[position].name
        holder.imageView.setImageResource(R.drawable.drink)
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val textViewName: TextView = mView.findViewById<View>(R.id.textViewIngredientRecyclerName) as TextView
        val imageView: ImageView = mView.findViewById<View>(R.id.recyclerIngredientImage) as ImageView

        override fun toString(): String {
            return super.toString() + " '" + textViewName.text + ";"
        }

    }
}