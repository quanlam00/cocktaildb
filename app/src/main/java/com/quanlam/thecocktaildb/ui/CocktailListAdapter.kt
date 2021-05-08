package com.quanlam.thecocktaildb.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.quanlam.thecocktaildb.R
import com.squareup.picasso.Picasso

class CocktailListAdapter(
    private val mValues: List<CocktailListItem>,
    private val mListener: OnCocktailItemClickListener?
) : RecyclerView.Adapter<CocktailListAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener = View.OnClickListener { v ->
        val item = v.tag as CocktailListItem
        mListener?.onClick(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cocktail_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mNameText.text = item.name
        if (item.imageUrl.isNotEmpty()) {
            holder.mImage.visibility = View.VISIBLE
            Picasso.get().load(item.imageUrl).into(holder.mImage)
        } else {
            holder.mImage.visibility = View.INVISIBLE
        }

        //Tag item info into list item
        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size
    //Representation of an Item
    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mNameText: TextView = mView.findViewById(R.id.drink_name)
        val mImage: ImageView = mView.findViewById(R.id.drink_icon)
    }
}