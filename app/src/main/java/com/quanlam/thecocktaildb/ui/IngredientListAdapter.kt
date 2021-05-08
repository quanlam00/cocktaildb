package com.quanlam.thecocktaildb.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.quanlam.thecocktaildb.R
import java.lang.ref.WeakReference

class IngredientListAdapter(
    private val mValues: List<IngredientListItem>,
    context: Context,
    val drinkId: Int,
) : RecyclerView.Adapter<IngredientListAdapter.ViewHolder>() {
    private val context = WeakReference(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.ingredient_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        context.get()?.let {
            val item = mValues[position]
            if (item.amount.isNotEmpty()) {
                holder.mNameText.text = "${item.name} (${item.amount})"
            } else {
                holder.mNameText.text = item.name
            }

            //add the drinkId to the getColor utils to spice up the color combination
            holder.mImage.setImageDrawable(ContextCompat.getDrawable(
                context.get()!!,
                IngredientColorUtil.getColorForNumber(
                    (position + drinkId)%15
                )))
        }
    }

    override fun getItemCount(): Int = mValues.size
    //Representation of an Item
    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val mNameText: TextView = mView.findViewById(R.id.ingredient_name)
        val mImage: ImageView = mView.findViewById(R.id.ingredient_icon)
    }
}