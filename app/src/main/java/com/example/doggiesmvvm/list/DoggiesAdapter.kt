package com.example.doggiesmvvm.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.doggiesmvvm.R
import kotlinx.android.synthetic.main.doggy_item.view.*

class DoggiesAdapter(
    private var values: MutableList<DoggyUI>
) : RecyclerView.Adapter<DoggiesAdapter.ViewHolder>() {

    val selectedItem = MutableLiveData<DoggyUI>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.doggy_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.contentView.text = item.breedName
        holder.view.setOnClickListener(createOnClickListener(item))
    }

    override fun getItemCount(): Int = values.size

    private fun createOnClickListener(selected: DoggyUI): View.OnClickListener {
        return View.OnClickListener {
            selectedItem.postValue(selected)
        }
    }

    fun update(breedsList: List<DoggyUI>) {
        values = breedsList.toMutableList()
        notifyDataSetChanged()
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val contentView: TextView = view.content


        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }
}