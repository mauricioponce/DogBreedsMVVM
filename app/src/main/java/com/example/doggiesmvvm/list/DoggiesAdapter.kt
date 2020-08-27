package com.example.doggiesmvvm.list

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.doggiesmvvm.R

import com.example.doggiesmvvm.dummy.DummyContent.DummyItem
import timber.log.Timber

class DoggiesAdapter(
    private var values: MutableList<DoggyUI>
) : RecyclerView.Adapter<DoggiesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.doggy_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        //holder.idView.text = item
        holder.contentView.text = item.breedName
    }

    override fun getItemCount(): Int = values.size

    fun update(breedsList: List<DoggyUI>) {
        Timber.d("update ${breedsList.size}" )
        values.clear()
        values.addAll(breedsList)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: TextView = view.findViewById(R.id.item_number)
        val contentView: TextView = view.findViewById(R.id.content)

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }
}